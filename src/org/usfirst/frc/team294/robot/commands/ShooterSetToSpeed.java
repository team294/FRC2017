package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShooterSetToSpeed extends Command {
	
	private double speed;

	/**
	 * Set the shooter to speed according to Vbus
	 * @param speed between -1 and 1
	 */
    public ShooterSetToSpeed(double speed) {
        requires(Robot.shooter);
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Validate inputs first
    	if (speed > 1.0) speed = 1.0;
    	if (speed < -1.0) speed = -1.0;
    	Robot.shooter.setSpeed(speed);
    	
    	// Write log of shooting
    	Robot.log.writeLogEcho("Shooter: Setting Shooting Speed " + speed);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
