package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sets the shooter according to Voltage
 */
public class ShooterSetVoltageFromSmartDashboard  extends Command{
	
	public ShooterSetVoltageFromSmartDashboard() {	
		 	requires(Robot.shooter);
	}    	
		   
	// Called just before this Command runs the first time
	protected void initialize() {
		    Robot.shooter.setVoltage(SmartDashboard.getNumber("Shooter Motor Set Voltage", 0.0));  		
	}

		    // Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}

		    // Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		        return false;
	}

		    // Called once after isFinished returns true
	protected void end() {
		Robot.shooter.setVoltage(0);
	}

		    // Called when another command which requires one or more of the same
		    // subsystems is scheduled to run
	protected void interrupted() {
		Robot.shooter.setVoltage(0);
	}
		    
}


