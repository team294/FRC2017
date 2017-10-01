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
    	
    	if (Robot.shifter.isShifterInHighGear()){
    		leftVal = leftVal*0.75;
    		rightVal = rightVal*0.75;
    	}
    	
    	// Arcade drive stuff
    	// Simple map
    	// left = straight + turn
    	// right = straight - turn
    	// straight = left - turn
    	// right = left - turn - turn
    	// *turn = (left-right)/2
    	// left = straight + (left - right)/2
    	// *straight = (left + right)/2
    	//double turnScaleFactor = 1.0;
    	//double straightVal = (leftVal+rightVal)/2;
    	//double turnVal = (leftVal-rightVal)/2*turnScaleFactor;
    	//leftVal = straightVal+turnVal;
    	//rightVal = straightVal-turnVal;
    	
    	
    	if (Robot.oi.getDriveDirection() == true){
    		Robot.driveTrain.driveWithJoystick(leftVal, rightVal);
//    		SmartDashboard.putBoolean("Forward", true);
    	}
    	else {
    		Robot.driveTrain.driveWithJoystick(-rightVal, -leftVal);
//    		SmartDashboard.putBoolean("Forward", false);
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
