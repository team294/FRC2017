package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 *
 */
public class TurnToAngle extends Command {

	private double angle;
	final private double MIN_POWER = 0.4;
	final private double MAX_POWER = 0.6;
	private double targetAngle;
	private double currentAngle;
	
    public TurnToAngle(double target) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	requires(Robot.vision);
    	targetAngle=target;
    	SmartDashboard.putString("Init TurnToAngle function", "yes");
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("Init TurnToAngle", "yes");
    	System.out.println("Started TurnToAngle");
    	System.out.println("Hangs before vision calculations");
    	currentAngle= Robot.vision.getGearAngleOffset();
    	angle=targetAngle-currentAngle;
    	double speed=calculateSpeed(angle);
    	System.out.println("Hangs at driveAtAngle");
    	Robot.driveTrain.driveAtAngle(speed, 1);
    	System.out.println("Got through init for TurnToAngle");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle= Robot.vision.getGearAngleOffset();
    	angle=targetAngle-currentAngle;
    	double speed=calculateSpeed(angle);
    	Robot.driveTrain.driveAtAngle(speed, angle/Math.abs(angle));
    	SmartDashboard.putNumber("angle", currentAngle);
    	SmartDashboard.putString("Init TurnToAngle finished", "yes");
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
