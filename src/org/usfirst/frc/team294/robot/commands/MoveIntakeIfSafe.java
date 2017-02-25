package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.subsystems.Intake.Status;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the intake if it is safe
 */
public class MoveIntakeIfSafe extends Command {

	private boolean status;
	private boolean waitForMovement;
	
	/**
	 * Set the position of the intake only if safe to do so
	 * @param status true for deployed, false for stowed
	 */
	public MoveIntakeIfSafe(boolean status) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.intake);
		
		this.status = status;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	if ((status && Robot.intake.getIntakeTracker() == Status.deployed) || 
    			(!status && Robot.intake.getIntakeTracker() == Status.stowed)) {
    		waitForMovement = false;
    	} else if (!status && Robot.intake.getHopperTracker() != Status.stowed) {
    		waitForMovement = false;
    	} else {
    		waitForMovement = true;
    		Robot.intake.setIntakeTracker(Status.unknown);
        	if (status) Robot.intake.deployIntake();
        	else { Robot.intake.stowIntake(); }
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (waitForMovement) return (timeSinceInitialized() > Robot.intake.INTAKE_DELAY);
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (waitForMovement) Robot.intake.setIntakeTracker(status ? Status.deployed : Status.stowed);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
