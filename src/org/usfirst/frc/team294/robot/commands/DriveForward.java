package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForward extends Command {

	double speed;
	
    public DriveForward(double setSpeed) {
        requires(Robot.driveTrain);
        speed = setSpeed;
		System.out.println("Drive forward create");	
		SmartDashboard.putString("Debug msg","Create drive forward");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
		System.out.println("Drive forward init");		
    	Robot.driveTrain.driveForward(speed);
    	SmartDashboard.putNumber("Drive Forward Speed", speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		System.out.println("Drive forward exec");		
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
		System.out.println("Drive forward isFinished");		
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
