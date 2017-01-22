package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;



/**
 *
 */
public class DriveToAngle extends Command {

	private double speed;
	private double curve;
	
    public DriveToAngle(double speed, double curve) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	this.speed = speed;
    	this.curve = curve;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (speed > 1.0) speed = 1.0;
    	if (speed < -1.0) speed = -1.0;
    	Robot.driveTrain.driveAtAngle(speed, curve);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
