package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ToleranceChecker;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class GyroTurnToAngle extends Command {
	
	private double speed;
	private double turnAngle;
	private double angleErr;
	
	protected ToleranceChecker angleTol = new ToleranceChecker(4.0, 5);
	

	/**
	 * Turn the robot to a desired angle using the Gyro, relative to the absolute orientation, not the current position
	 * @param speed speed to turn at, from 0 to 1
	 * @param turnAngle 0 to +180 for clockwise, 0 to -180 for counterclockwise
	 * @param tolerance the angle tolerance to accept (default is 4.0)
	 */
    public GyroTurnToAngle(double speed, double turnAngle, double tolerance) {
        requires(Robot.driveTrain);
        this.turnAngle = turnAngle;
        this.speed = Math.abs(speed);
        angleTol.setTolerance(tolerance);
    }
    
	/**
	 * Turn the robot to a desired angle using the Gyro, relative to the absolute orientation, not the current position (tolerance of 4.0)
	 * @param speed speed to turn at, from 0 to 1, minimum of 0.25
	 * @param turnAngle 0 to +180 for clockwise, 0 to -180 for counterclockwise
	 */
    public GyroTurnToAngle(double speed, double turnAngle) {
        requires(Robot.driveTrain);
        this.turnAngle = turnAngle;
        this.speed = Math.abs(speed);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Validate inputs for safety
    	if (turnAngle > 180.0) turnAngle = 180.0;
    	if (turnAngle < -180.0) turnAngle = -180.0;
    	if (speed > 1.0) speed = 1.0;
    	if (speed < 0.25) speed = 0.25;
    	angleTol.reset();
    }
    
    /**
     * Get the change in angle needed to turn
     * @return angle error from -180 to +180
     */
    private double getAngleError() {
    	double angleErr;
    	
    	// Find angle error.  - = left, + = right
    	angleErr = turnAngle - Robot.driveTrain.getGyroAngle();
    	if (angleErr > 180.0) angleErr -= 360;
    	if (angleErr < -180.0) angleErr += 360;  
    	
    	return angleErr;
    }    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	// Find angle error.  - = left, + = right
    	angleErr = getAngleError();
    	SmartDashboard.putNumber("Current Angle:", Robot.driveTrain.getGyroAngle());
    	SmartDashboard.putNumber("Angle Error:", angleErr);
    	
    	angleTol.check(Math.abs(angleErr));
    	
    	if (angleTol.success()) {
        	Robot.driveTrain.stop();
        	Robot.log.writeLog("Auto turn (finish):  current angle = " + Robot.driveTrain.getGyroAngle() + 
        			", target angle = " + turnAngle);
        	System.out.println("Auto turn (finish):  current angle = " + Robot.driveTrain.getGyroAngle() + 
        			", target angle = " + turnAngle);
    	} else {
    		if (angleErr < 0.0) speed = -speed;
    		SmartDashboard.putNumber("Speed:", speed);
        	Robot.driveTrain.driveAtAngle(speed, 1);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return angleTol.success();
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
