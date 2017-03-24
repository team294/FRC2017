package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearActuateShield extends Command {
	
	private boolean openShield;
	private boolean waitIfMoved;
	
	/**
	 * Set the gear shield piston.  Command returns immediately without waiting for the shield to move.
	 * @param openShield true to open shield, false to close shield
	 */
    public GearActuateShield(boolean openShield) {
        requires(Robot.gearGate);
        this.openShield = openShield;
        waitIfMoved = false;
    }

	/**
	 * Set the gear shield piston, with option to wait for the shield to finish moving.
	 * @param openShield true to open shield, false to close shield
	 * @param waitIfMoved true to wait for the shield to finish moving (if position actually changed), false to end command immediately
	 */
    public GearActuateShield(boolean openShield, boolean waitIfMoved) {
        requires(Robot.gearGate);
        this.openShield = openShield;
        this.waitIfMoved = (waitIfMoved && (Robot.gearGate.isGearShieldOpen() != openShield));
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (openShield) {
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
        return (!waitIfMoved || this.timeSinceInitialized() >= 0.8);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
