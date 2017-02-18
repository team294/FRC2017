package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * I'm not completely sure why a command needs to exist to display to the Smart Dashboard... just call it when you need it
 * Testing purposes only (useless and can be done cleaner in teleopPeriodic -John)
 */
public class DisplayBoilerDistance extends Command {

    public DisplayBoilerDistance() {
    	requires(Robot.boilerVision);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double distance = Robot.boilerVision.getBoilerDistance();
    	SmartDashboard.putNumber("Boiler Distance", distance);
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
