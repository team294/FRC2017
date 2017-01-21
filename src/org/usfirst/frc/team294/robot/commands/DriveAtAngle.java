package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team294.robot.*;
/**
 *
 */
public class DriveAtAngle extends Command {

    public DriveAtAngle() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	 requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.driveAtAngle(.1, -.5);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.getEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
