package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveToDistance extends Command {

    public DriveToDistance() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }
    
    public double calculateSpeed() {
    	double speed = 0;
    	double t = 0;
    	final double maxSpeed = 5;
    	
    	if (t <= 1) {
    		speed = 5*t;
    	} else if (t >= 3) {
    		speed = -5*t;
    	}
    	if (1 <= t && t <= 3) {
    		speed = maxSpeed;
    	}
    	return speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
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
