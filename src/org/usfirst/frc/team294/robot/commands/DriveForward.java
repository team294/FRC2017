package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForward extends Command {

	private double speed = 0.0;
	private double curve = 0.0;
	private double duration = 0.0;
	
	/**
	 * Drives forward for at a set speed, curve, and time.
	 * @param speed : +1.0 = full forward, -1.0 = full reverse
	 * @param curve : -1.0 = turn-in-place left, 0.0 = go straight, +1.0 = turn-in-place right 
	 * @param duration :  Time to drive forward, in seconds
	 */
    public DriveForward(double speed, double curve, double duration) {
        requires(Robot.driveTrain);
        this.speed = speed;
        this.curve = curve;
        this.duration = duration;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.driveTrain.driveAtAngle(speed, curve);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (timeSinceInitialized() >= duration);
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
