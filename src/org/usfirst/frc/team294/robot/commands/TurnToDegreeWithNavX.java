package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnToDegreeWithNavX extends Command {

	private double currentAngle;
	private double targetAngle;
	private boolean turnClockwise;
	private double angleToTurnTo;

	/**
	 * Turns to angle specified using navX data
	 * @param target: Angle that needs to be turned to
	 */
	public TurnToDegreeWithNavX(double target) {
		requires(Robot.driveTrain);
		this.currentAngle = (Robot.driveTrain.ahrs.getPitch() + 360) % 360;//Current angle from 0-360
		this.targetAngle = (target + 360) % 360;//Calculates target angle to hit

		//Find shortest turn path
		if(Math.abs(targetAngle-currentAngle) < Math.abs(360 - (targetAngle-currentAngle)))
			angleToTurnTo = targetAngle - currentAngle;
		else angleToTurnTo = 360 - (targetAngle-currentAngle);

		//Determine turn direction
		if(Math.abs(targetAngle - currentAngle) > Math.abs(targetAngle+currentAngle)) turnClockwise = true; 
		else turnClockwise = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(turnClockwise)Robot.driveTrain.tankTurn(1,1);
		else Robot.driveTrain.tankTurn(1,-1);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		if((Robot.driveTrain.ahrs.getPitch() + 360) % 360 != angleToTurnTo) return false;
		else {
			Robot.driveTrain.stop();
			return true;
		}
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
