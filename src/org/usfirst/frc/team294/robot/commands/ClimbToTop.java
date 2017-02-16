package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ClimbToTop extends Command {

	public ClimbToTop() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.intake);
	}

	double initTime = 0;
	boolean initialized = false;

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.intake.setClimbSpeed(1.0);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.intake.getClimberCurrent() > 3 && !initialized){
			initTime = timeSinceInitialized();
			initialized  = true;
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if ((Robot.intake.getClimberCurrent() > 3) && (timeSinceInitialized() - initTime) > 1){
			return true;
		}
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intake.stopClimber();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
