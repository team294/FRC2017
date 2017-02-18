package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the voltage output of both conveyers
 */
public class ConveyorSetVoltage extends Command {

	private double voltage;
	
	/**
	 * Set the voltage output of the vertical and horizontal conveyers
	 * @param voltage from -12 (out) to 12 (in)
	 */
    public ConveyorSetVoltage(double voltage) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ballFeed);
    	this.voltage = voltage;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballFeed.setHorSpeed(voltage);
    	Robot.ballFeed.setVertSpeed(voltage);
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
    	Robot.ballFeed.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.ballFeed.stop();
    }
}
