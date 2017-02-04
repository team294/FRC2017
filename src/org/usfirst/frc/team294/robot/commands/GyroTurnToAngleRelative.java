package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ToleranceChecker;

import edu.wpi.first.wpilibj.command.Command;

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
    
    public GyroTurnToAngleRelative(double angle, double speed) {
    	requires(Robot.driveTrain);

        angle = (angle > 180) ? angle - 360 : angle;
        angle = (angle < -180) ? angle + 360 : angle;
    	this.angle = angle;
    	speed = (speed > 1) ? 1 : speed;
    	this.speed = speed;
    	this.tolerance = new ToleranceChecker(4.0, 5);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tolerance.reset();
    	Robot.driveTrain.resetDegrees();
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}
