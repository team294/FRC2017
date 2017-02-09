package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForward extends Command {


	private double speed = 0.0;
	private double curve = 0.0;
	private double duration = 0.0;
	
    public DriveForward(double setSpeed) {
        requires(Robot.driveTrain);
        speed = setSpeed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
      System.out.println("Drive forward init");		
    	Robot.driveTrain.driveForward(speed);
    	SmartDashboard.putNumber("Drive Forward Speed", speed);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timeSinceInitialized() >= duration);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
