package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.*;

/**
 *
 */
public class DriveWithJoysticks extends Command {

    public DriveWithJoysticks() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.setDriveControlByPower();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double leftVal = Robot.oi.leftJoystick.getY();
    	double rightVal = Robot.oi.rightJoystick.getY();
    	double finalLeftVal = 0;
    	double finalRightVal = 0;
    	
    	//if (Robot.shifter.isLowMode(lowGear) = true){
    		
    		if(leftVal > 0.075){
        		finalLeftVal = (.7/.925)*leftVal + (1 - (.7/.925));
        	}
        	else if (leftVal < -0.05){
        		finalLeftVal = (.7/.95)*leftVal + -(1 - (.7/.95));
        	}
        	else {
        		finalLeftVal = 0;
        	}
        	
        	if(rightVal > 0.075){
        		finalRightVal = (.7/.925)*rightVal + (1 - (.7/.925));
        	}
        	else if (rightVal < -0.05){
        		finalRightVal = (.7/.95)*rightVal + -(1 - (.7/.95));
        	}
        	else {
        		finalRightVal = 0;
        	}
    	//}
    	
    /*	if (Robot.shifter.isHighGear = true){
    		
    		if(leftVal > 0.075){
        		finalLeftVal = (.7/.925)*leftVal + (1 - (.7/.925));
        	}
        	else if (leftVal < -0.05){
        		finalLeftVal = (.7/.95)*leftVal + -(1 - (.7/.95));
        	}
        	else {
        		finalLeftVal = 0;
        	}
        	
        	if(rightVal > 0.075){
        		finalRightVal = (.7/.925)*rightVal + (1 - (.7/.925));
        	}
        	else if (rightVal < -0.05){
        		finalRightVal = (.7/.95)*rightVal + -(1 - (.7/.95));
        	}
        	else {
        		finalRightVal = 0;
        	}
    	}*/
    			
    	if (Robot.oi.getDriveDirection() == true){
    		Robot.driveTrain.driveWithJoystick(finalLeftVal, finalRightVal);
    		SmartDashboard.putBoolean("Forward", true);
    	}
    	else {
    		Robot.driveTrain.driveWithJoystick(-finalRightVal, -finalLeftVal);
    		SmartDashboard.putBoolean("Forward", false);
    	}
    	
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
    	Robot.driveTrain.stop();
    }
}
