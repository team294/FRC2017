package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetShooterHoodToAngle extends Command {

	protected double angle;
	
	/**
	 * Set the shooter hood to an angle
	 * @param angle from -180 to +180
	 */
    public SetShooterHoodToAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.shooterHood);
        this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// sterilize inputs here
    	Robot.shooterHood.setShooterHoodAngle(angle);
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
