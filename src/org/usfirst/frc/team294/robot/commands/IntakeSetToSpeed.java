package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the intake to speed according to Vbus
 */
public class IntakeSetToSpeed extends Command {

	private double speed;

	/**
	 * Set the intake motor to speed according to Vbus
	 * @param speed -1 (outtake) to +1 (intake). 0 = stopped
	 */
	public IntakeSetToSpeed(double speed) {
		requires(Robot.intake);
		this.speed = speed;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		// Validate inputs first
		if (speed < -1.0) speed = -1.0;
		if (speed > 1.0) speed = 1.0;
		Robot.intake.setSpeed(speed);
		
		Robot.log.writeLog("Command IntakeSetToSpeed(" + speed + ")");
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.intake.setSpeed(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.intake.setSpeed(0);

	}
}
