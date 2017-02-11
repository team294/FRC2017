package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetShooterHood extends Command {

	private boolean position;
	
	/**
	 * Set the position of the shooter hood
	 * @param position true for out/up, false for in/down
	 */
    public SetShooterHood(boolean position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooterHood);
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (position) Robot.shooterHood.deployShooterHood();
    	else { Robot.shooterHood.stowShooterHood(); }
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
