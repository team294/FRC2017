package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Sets the voltage output of both conveyers
 */
public class ConveyorSetVoltage extends Command {

	private double horVoltage = Robot.horizontalConveyor;
	private double vertVoltage = Robot.verticalConveyor;
	
	/**
	 * Set the vertical and horizontal voltage output of the vertical and horizontal conveyers
	 * @param vertical voltage from -12 (out) to 12 (in)
	 * @param horizontal voltage from -12 (out) to 12 (in)
	 */
<<<<<<< HEAD:src/org/usfirst/frc/team294/robot/commands/ConveyorSetVoltage.java
    public ConveyorSetVoltage(double voltage) {
=======
    public ConveyorSetToVoltage() {
>>>>>>> refs/remotes/origin/master:src/org/usfirst/frc/team294/robot/commands/ConveyorSetToVoltage.java
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.ballFeed);
//    	this.vertVoltage = vertVoltage;
//    	this.horVoltage = horVoltage;

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
