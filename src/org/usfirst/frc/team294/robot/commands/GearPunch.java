package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GearPunch extends Command {
	
	private boolean punchOut;
	
	/**
	 * Set the gear puncher piston
	 * @param true for out, false for in
	 */
    public GearPunch(boolean punchOut) {
        requires(Robot.gearGate);
        this.punchOut = punchOut;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (punchOut) {
    		Robot.gearGate.gearPuncherDeploy();
    	} else {
    		Robot.gearGate.gearPuncherRetract();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
