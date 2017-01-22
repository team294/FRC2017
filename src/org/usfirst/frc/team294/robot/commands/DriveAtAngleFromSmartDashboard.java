package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.*;
/**
 *
 */

public class DriveAtAngleFromSmartDashboard extends Command { 

	private double speed;
	private double curve;
	/**
	 * Sets robot to drive at an angle based on smart dashboard inputs
	 */
    public DriveAtAngleFromSmartDashboard() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	 requires(Robot.driveTrain);
    	 this.speed = SmartDashboard.getNumber("Drive Speed", 0);
    	 this.curve = SmartDashboard.getNumber("Drive Curve", 0);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.driveAtAngle(speed, curve);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("Running command\n");
    	this.speed = SmartDashboard.getNumber("Drive Speed", 0);
   	 	this.curve = SmartDashboard.getNumber("Drive Curve", 0);
    	Robot.driveTrain.driveAtAngle(speed, curve);
    	Robot.driveTrain.getEncoder();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if(this.speed == 0){
    		return true;
    	}
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
