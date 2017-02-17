package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sets the shooter according to voltage
 */
public class ShooterSetVoltage extends Command {

	private double voltage;
	
	/**
	 * Sets the shooter according to voltage
	 * @param voltage from -12.0 to 12.0
	 */
    public ShooterSetVoltage(double voltage) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.voltage = voltage;
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Validate inputs
    	if (voltage > 12.0) voltage = 12.0;
    	if (voltage < -12.0) voltage = -12.0;

    	Robot.shooter.setVoltage(voltage);
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
