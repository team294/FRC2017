package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class TurnToAngle extends Command {

	private double angle;
	final private double MIN_POWER = 0.2;
	final private double MAX_POWER = 0.7;
	private double targetAngle;
	private double currentAngle;
	
    public TurnToAngle(double target) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	requires(Robot.vision);
    	targetAngle=target;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	currentAngle= Robot.vision.getGearAngleOffset();
    	angle=targetAngle-currentAngle;
    	double speed=calculateSpeed(angle);
    	Robot.driveTrain.driveAtAngle(speed, 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle= Robot.vision.getGearAngleOffset();
    	angle=targetAngle-currentAngle;
    	double speed=calculateSpeed(angle);
    	Robot.driveTrain.driveAtAngle(speed, -angle/Math.abs(angle));
    	SmartDashboard.putNumber("angle", currentAngle);
    }
    
    private double calculateSpeed(double angle) {
    	double power = 0;
    	if (Math.abs(angle) >= 90) {
    		power = MAX_POWER;
    	} else {
    		power = MIN_POWER + Math.abs(angle)*(MAX_POWER-MIN_POWER)/90;
    	}
    	return power;
    	
    }
   
    	
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (angle <= 3 && angle >= -3) {
        	return true;
        } else {
        	return false;
        }
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
