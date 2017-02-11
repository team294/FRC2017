package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ToleranceChecker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroTurnToAngleRelative extends Command {

	private double angle;
	private double speed;
	
	private double angleErr;
	private double priorAngleErr;
	
	private double speedControl;
	private double minSpeed = 0.25;
	
	private double kDangle = 0.05;
	private double kPangle = 0.025;

	private ToleranceChecker tolerance;
	
	private boolean vision = false;

	/**
	 * Turn to an angle relative to the robot's current position
	 * @param angle from -180 to +180
	 * @param speed from 0.25 to +1
	 * @param tolerance angle tolerance to use (default is 4.0)
	 */
    public GyroTurnToAngleRelative(double angle, double speed, double tolerance) {
        requires(Robot.driveTrain);
        
        angle = (angle > 180) ? angle - 360 : angle;
        angle = (angle < -180) ? angle + 360 : angle;
        this.angle = angle;
        speed = (speed > 1) ? 1 : speed;
        this.speed = speed;
        this.tolerance = new ToleranceChecker(tolerance, 5);
    }
    
    /**
     * Turn the robot to a specified angle
     * @param angle from -180 to +180 (- = left, + = right)
     * @param speed from 0 to 1.0
     */
    public GyroTurnToAngleRelative(double angle, double speed) {
    	requires(Robot.driveTrain);

        angle = (angle > 180) ? angle - 360 : angle;
        angle = (angle < -180) ? angle + 360 : angle;
    	this.angle = angle;
    	speed = (speed > 1) ? 1 : speed;
    	this.speed = speed;
    	this.tolerance = new ToleranceChecker(4.0, 5);
    	this.vision = false;
    }
    
    /**
     * Turns the Robot using an angle from the Gear Vision
     * @param angle null value, is overwritten by gear vision subsystem
     * @param speed from 0 to 1.0
     * @param useGearVision true to use gear vision, false to not use
     */
    public GyroTurnToAngleRelative(double angle, double speed, boolean useGearVision) {
    	requires(Robot.driveTrain);
    	requires(Robot.vision);
    	
    	this.angle = angle;
    	speed = (speed > 1) ? 1 : speed;
    	this.speed = speed;
    	this.tolerance = new ToleranceChecker(4.0, 5);
    	this.vision = useGearVision;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tolerance.reset();
    	Robot.driveTrain.resetDegrees();
    	
    	angle = (vision) ? Robot.vision.getGearAngleOffset() : angle;
    	if (angle == -500) SmartDashboard.putBoolean("Contours Found", false);
    	else { SmartDashboard.putBoolean("Contours Found", true); }
    	// Normalize angle to -180 to +180
        angle = (angle > 180) ? angle - 360 : angle;
        angle = (angle < -180) ? angle + 360 : angle;
    }

    private double getAngleErr() {
    	double angleErr;
    	
    	// Find angle error.  - = left, + = right
    	angleErr = angle - Robot.driveTrain.getGyroAngle();
    	angleErr = (angleErr>180) ? angleErr-360 : angleErr;
    	angleErr = (angleErr<-180) ? angleErr+360 : angleErr;  
    	
    	return angleErr;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	angleErr = getAngleErr();
    	
    	tolerance.check(Math.abs(angleErr));
    	
    	if (tolerance.success()) {
    		Robot.driveTrain.stop();
    		Robot.log.writeLog("Gyro: Turned to angle relative: " + angle);
    	} else {
    		speedControl = angleErr*kPangle;
        	
        	if (speedControl>0) {
        		speedControl = (speedControl<minSpeed) ? minSpeed : speedControl;
        	} else {
        		speedControl = (speedControl>-minSpeed) ? -minSpeed : speedControl;
        	}

        	speedControl += kDangle*(angleErr-priorAngleErr);
        	speedControl = (speedControl>speed) ? speed : speedControl;
        	speedControl = (speedControl<-speed) ? -speed : speedControl;

        	Robot.driveTrain.driveAtAngle(speedControl, 1);
        	
        	priorAngleErr = angleErr;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tolerance.success();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLog("Autonomous Turn Completed: Expected Angle " + angle + " Actual Angle " + Robot.driveTrain.getGyroAngle() + " Error " + angleErr);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLog("Autonomous Turn Command Interrupted");
    }
}
