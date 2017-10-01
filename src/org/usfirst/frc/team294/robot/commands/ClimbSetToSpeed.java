package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ClimbSetToSpeed extends Command {

	private double speed;
	private boolean speedFromDashboard = false;
	
	/**
	 * Set the speed of the climber motors
	 * @param speed from 0 to +1
	 */
    public ClimbSetToSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    	this.speed = speed;
    	speedFromDashboard = false;
    }

	/**
	 * Set the speed of the climber motors
	 * @param speed from 0 to +1
	 * @param speedFromDashboard true to activate
	 */
    public ClimbSetToSpeed(double speed, boolean speedFromDashboard) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    	this.speed = speed;
    	this.speedFromDashboard = speedFromDashboard;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Turn on climber data logging
    	Robot.logClimberData = true;
    	
    	if (speedFromDashboard)
    		speed = SmartDashboard.getNumber("Climb Speed", 0.0);
    	
		Robot.log.writeLogEcho("Set climber speed, " + speed);

    	Robot.intake.setClimbSpeed(speed);
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
