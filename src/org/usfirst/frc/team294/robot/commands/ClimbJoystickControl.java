package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Testing purposes only. Remove before Competition.
 */
public class ClimbJoystickControl extends Command {
	

    public ClimbJoystickControl() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	// Turn on climber data logging
    	Robot.logClimberData = true;
    	
		Robot.log.writeLogEcho("Climber joystic control");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.setClimbSpeed(Robot.oi.xboxController.getRawAxis(5));
		}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;//!Robot.oi.right[3].get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.intake.setClimbSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.intake.setClimbSpeed(0);
    }
}
