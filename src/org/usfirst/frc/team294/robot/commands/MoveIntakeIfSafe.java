package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.subsystems.Intake.Status;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the intake if it is safe
 */
public class MoveIntakeIfSafe extends Command {

	private boolean deploy;
	private boolean waitForMovement;
	
	/**
	 * Set the position of the intake only if safe to do so
	 * @param deploy true to deploy intake, false to stow intake
	 */
	public MoveIntakeIfSafe(boolean deploy) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.intake);
		
    	// To ensure intake movement tracking, don't allow this command to be interrupted
    	this.setInterruptible(false);

		this.deploy = deploy;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
    	if ((deploy && Robot.intake.getIntakeTracker() == Status.deployed) || 
    			(!deploy && Robot.intake.getIntakeTracker() == Status.stowed)) {
    		// Intake is already in desired position, so do nothing
    		waitForMovement = false;
    	} else if (!deploy && Robot.intake.getHopperTracker() != Status.stowed) {
    		// Can't stow intake if hopper is not stowed (ie., if deployed or unknown or moving)
    		// Note that we can *always* deploy the intake, even if all states are unknown 
    		waitForMovement = false;
    	} else {
    		// OK, we can move the intake
    		waitForMovement = true;
    		Robot.intake.setIntakeTracker(Status.unknown);	// put in unknown state while intake is moving
        	if (deploy) Robot.intake.deployIntake();
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
    	if (waitForMovement) Robot.intake.setIntakeTracker(deploy ? Status.deployed : Status.stowed);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
