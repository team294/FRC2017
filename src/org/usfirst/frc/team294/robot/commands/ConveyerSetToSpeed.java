package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the speed of both conveyers
 */
public class ConveyerSetToSpeed extends Command {

	private double speed;
	
	/**
	 * Set the speed of the vertical and horizontal conveyers
	 * @param speed from -1 (out) to +1 (in)
	 */
    public ConveyerSetToSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ballFeed);
    	this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballFeed.setHorSpeed(speed);
    	Robot.ballFeed.setVertSpeed(speed);
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
