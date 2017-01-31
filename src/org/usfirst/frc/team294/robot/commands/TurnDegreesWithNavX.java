package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegreesWithNavX extends Command {
	
	private double currentAngle;
	private double targetAngle;
	private double addDegrees;
	private boolean turnClockwise;

	/**
	 * Turns degrees degrees from the current angle
	 * @param degrees: Degrees to turn
	 */
	public TurnDegreesWithNavX(double degrees) {
		requires(Robot.driveTrain);
		this.addDegrees = degrees;
		
		this.currentAngle = (Robot.driveTrain.ahrs.getPitch() + 360) % 360;//Current angle is translated into a positive angle from 0-360
		this.targetAngle = (currentAngle + addDegrees + 360)%360;//Compute target angle
		
		
		//Determine turn direction
    	if(addDegrees > 0) turnClockwise = true;
    	else turnClockwise = false;
	}
	
    // Called just before this Command runs the first time
    protected void initialize() {

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(turnClockwise) Robot.driveTrain.tankTurn(1,1);
    	else Robot.driveTrain.tankTurn(1,-1);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		if(((Robot.driveTrain.ahrs.getPitch() +360) %360) != targetAngle) return false;
		else {
			Robot.driveTrain.stop();
			return true;
		}    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
