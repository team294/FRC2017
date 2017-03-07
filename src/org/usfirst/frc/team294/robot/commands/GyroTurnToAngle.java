package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ToleranceChecker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class GyroTurnToAngle extends Command {
	
	// Available turn modes
	public enum TurnMode {
	    RELATIVE, ABSOLUTE, GEAR_VISION, BOILER_VISION, SMARTDASHBOARD
	}
	
	// Settings from command initialization
	private double angle;
	private double maxSpeed;
	private TurnMode turnMode;
	
	// Tolerance checker
	private ToleranceChecker tolCheck;
	
	// Turning parameters
	private double kPangle = 0.04;
	private double kIangle = 0.002;
	private double kDangle = 0.25;
	private double minSpeed = 0.25;

	// Local variables
	private double angleErr;
	private double priorAngleErr = 0.0;
	private double intErr = 0.0;
	private double speedControl;

    /**
     * Turns the Robot using an angle from the Gear Vision
	 * @param speed from 0 to +1
 	 * @param angle from -180 to +180 degrees (ignored if turnMode = GEAR_VISION or BOILER_VISION)
	 * @param tolerance angle tolerance to use (default is 4.0)
     * @param turnMode :
     * <p> <b>RELATIVE</b> = Reset gyro, turn <b>angle</b> degrees from current orientation
     * <p> <b>ABSOLUTE</b> = Turn <b>angle</b> degrees from prior orientation zero (don't reset gyro)
     * <p> <b>GEAR_VISION</b> = Reset gyro, turn per gear camera (ignore <b>angle</b>)
     * <p> <b>BOILER_VISION</b> = Reset gyro, turn per gear camera (ignore <b>angle</b>)
     * <p> <b>SMARTDASHBOARD</b> = Reset gyro, turn <b>angle</b> degrees at <b>maxSpeed</b> speed within <b>angleErr</b> tolerance from current orientation based on input from Smartdashboard
     */
    public GyroTurnToAngle(double speed, double angle, double tolerance, TurnMode turnMode) {
    	requires(Robot.driveTrain);
    	if (turnMode == TurnMode.GEAR_VISION) {
    		requires(Robot.gearVision);
    	}
    	if (turnMode == TurnMode.BOILER_VISION) {
    		requires(Robot.boilerVision);
    	}

		// Normalize angle
		angle = angle - Math.floor(angle/360)*360;  // Normalize to 0 to 360 degrees
        angle = (angle > 180) ? angle - 360 : angle;  // Normalize to -180 to +180 degrees
    	this.angle = angle;
    	
    	// Limit speed range
    	speed = Math.abs(speed);
    	speed = (speed > 1) ? 1 : speed;
    	this.maxSpeed = speed;

    	// Save rest of parameters
    	tolCheck = new ToleranceChecker(tolerance, 5);
    	this.turnMode = turnMode; 
    }
	
	/**
	 * Turn to an angle relative to the robot's current position (resets the gyro before turning).
	 * @param speed from 0 to +1
	 * @param angle from -180 to +180 degrees
	 * @param tolerance angle tolerance to use (default is 4.0)
	 */
    public GyroTurnToAngle(double speed, double angle, double tolerance) {
    	this(speed, angle, tolerance, TurnMode.RELATIVE);
    }
    
    /**
	 * Turn to an angle relative to the robot's current position (resets the gyro before turning),
	 * with a tolerance of 4.0 degrees.
	 * @param speed from 0 to +1
	 * @param angle from -180 to +180 degrees
     */
    public GyroTurnToAngle(double speed, double angle) {
    	this(speed, angle, 4.0, TurnMode.RELATIVE);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	tolCheck.reset();

    	switch (turnMode) {
    	case ABSOLUTE:
    		Robot.log.writeLogEcho("Gyro: Start turn to angle absolute " + angle  + " degrees, current heading " +
    				Robot.driveTrain.getGyroAngle() + " degrees.");
    		break;
    	case RELATIVE:
    		Robot.driveTrain.resetDegrees();
    		Robot.log.writeLogEcho("Gyro: Start turn to angle relative " + angle + " degrees.");
    		break;
    	case GEAR_VISION:
    		Robot.driveTrain.resetDegrees();
    		angle = -Robot.gearVision.getGearAngleOffset();
        	if (!Robot.gearVision.isGearAngleValid())
        		SmartDashboard.putBoolean("Contours Found", false);
        	else
        		SmartDashboard.putBoolean("Contours Found", true);
    		Robot.log.writeLogEcho("Gyro: Start turn to angle GEAR" + angle + " degrees.");
    		break;
    	case BOILER_VISION:
    		Robot.driveTrain.resetDegrees();
    		angle = Robot.boilerVision.getBoilerAngleOffset();
    		Robot.log.writeLogEcho("Gyro: Start turn to angle BOILER" + angle + " degrees.");
    		break;
    	case SMARTDASHBOARD:
    		Robot.driveTrain.resetDegrees();
    		angle = SmartDashboard.getNumber("TurnAngle", 0); 
    		maxSpeed = SmartDashboard.getNumber("TurnSpeed", 0);
    		angleErr = SmartDashboard.getNumber("AngleTolerance", 0);
    		Robot.log.writeLogEcho("Gyro: Start turn to angle SMARTDASHBOARD" + angle + " degrees.");
    		break;
    	}

    	priorAngleErr = getAngleErr();
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
    	intErr = intErr + angleErr*0.02;
    	tolCheck.check(Math.abs(angleErr));
    	
    	if (tolCheck.success()) {
    		Robot.driveTrain.stop();
    		Robot.log.writeLogEcho("Gyro: Turned to angle goal " + angle + ", final angle " + Robot.driveTrain.getGyroAngle());
    	} else {
    		speedControl = angleErr*kPangle + intErr*kIangle;
        	
        	if (speedControl>0) {
        		speedControl = (speedControl<minSpeed) ? minSpeed : speedControl;
        	} else {
        		speedControl = (speedControl>-minSpeed) ? -minSpeed : speedControl;
        	}

        	speedControl += kDangle*(angleErr-priorAngleErr);
        	speedControl = (speedControl>maxSpeed) ? maxSpeed : speedControl;
        	speedControl = (speedControl<-maxSpeed) ? -maxSpeed : speedControl;
        	SmartDashboard.putNumber("Turn Command", speedControl);
        	SmartDashboard.putNumber("Gear error", angleErr);
        	Robot.driveTrain.driveAtAngle(speedControl, 1);
        	
        	priorAngleErr = angleErr;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return tolCheck.success();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLogEcho("Autonomous Turn Completed: Expected Angle " + angle + " Actual Angle " + Robot.driveTrain.getGyroAngle() + " Error " + angleErr);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLogEcho("Autonomous Turn Command Interrupted");
    }
}
