package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearActuatShield extends Command {
	
	private boolean state;
	
	/**
	 * Set the gear shield piston
	 * @param state true for out, false for in
	 */
    public GearActuatShield(boolean state) {
        requires(Robot.gearGate);
        this.state = state;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (state) {
    		Robot.gearGate.gearShieldOpen();
    	} else {
    		Robot.gearGate.gearShieldClose();
    	}
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
