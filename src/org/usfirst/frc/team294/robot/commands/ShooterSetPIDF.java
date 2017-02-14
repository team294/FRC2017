package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Set the PIDF of the shooter from the Smart Dashboard
 */
public class ShooterSetPIDF extends Command {

	private double speed;
	
	/**
	 * Set the PIDF of the shooter from the Smart Dashboard and the speed here
	 * @param speed rpm, from -1000 to 18000
	 */
    public ShooterSetPIDF(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.shooter);
		this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		Robot.shooter.setPIDFromSmartDashboard();
		Robot.shooter.setRPM(speed);
    	
    	// Write log of setting PIDF
    	Robot.log.writeLog(" Shooter-- Setting PIDF ");
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
