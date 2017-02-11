package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class TurnToAngle extends Command {

	private double angle;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
	final private double MIN_POWER = 0.4;
	final private double MAX_POWER = 0.6;
	private double targetAngle;
	private double currentAngle;
	private double defaultDirection = -1; 
	
    public TurnToAngle(double target) {
    	requires(Robot.driveTrain);
    	requires(Robot.gearVision);
    	targetAngle=target;
    }
  // Called just before this Command runs the first time
    protected void initialize() {currentAngle = Robot.gearVision.getGearAngleOffset();
    	angle = targetAngle - currentAngle;
    	double speed=calculateSpeed(angle);
    	Robot.driveTrain.driveAtAngle(speed, 1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = Robot.gearVision.getGearAngleOffset();
    	if (currentAngle == -500) {
    		Robot.driveTrain.driveAtAngle(MAX_POWER, defaultDirection);
    	}
    	else {
	    	angle = targetAngle - currentAngle;
	    	double speed = calculateSpeed(angle);
	    	Robot.driveTrain.driveAtAngle(speed, angle/Math.abs(angle));
    	}
    }
    
    private double calculateSpeed(double angle) {
    	double power = 0;
    	if (Math.abs(angle) >= 34.25) {
    		power = MAX_POWER;
    	} else {
    		power = MIN_POWER + angle*angle*(MAX_POWER-MIN_POWER)/34.25/34.25;
    	}
    	return power;
    	
    }
   
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	SmartDashboard.putNumber("Angle", angle);
    	System.out.println("Checking angle: " + Double.toString(angle));
        if (Math.abs(angle) < .75) {
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
