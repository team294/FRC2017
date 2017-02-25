package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the voltage output of both conveyers
 */
public class ConveyorSetFromRobot extends Command {

	public enum States {
		in, out, stopped
	}
	
	private double horVoltage;
	private double vertVoltage;
	
	/**
	 * Set the vertical and horizontal voltage output of the vertical and horizontal conveyers
	 * @param vertical voltage from -12 (out) to 12 (in) is in Robot
	 * @param horizontal voltage from -12 (out) to 12 (in) is in Robot
	 */
    public ConveyorSetFromRobot(States state) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ballFeed);

    	if (state == States.in) {
    		horVoltage = Robot.horizontalConveyorIn;
    		vertVoltage = Robot.verticalConveyor;
    	} else if (state == States.out) {
    		horVoltage = Robot.horizontalConveyorOut;
    		vertVoltage = Robot.verticalConveyorOut;
    	} else {
    		horVoltage = 0.0;
    		vertVoltage = 0.0;
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.ballFeed.setHorSpeed(horVoltage);
    	Robot.ballFeed.setVertSpeed(vertVoltage);
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
