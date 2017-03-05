package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.subsystems.Intake.Status;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the hopper if it is safe
 */
public class MoveHopperIfSafe extends Command {

	private boolean deploy;
	private boolean waitForMovement;
	
	/**
	 * Set the position of the hopper if it is safe to do so (e.g. intake is not stowed)
	 * @param deploy true to deploy hopper, false to stow hopper
	 */
    public MoveHopperIfSafe(boolean deploy) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    	
    	// To ensure hopper movement tracking, don't allow this command to be interrupted
    	this.setInterruptible(false);

    	this.deploy = deploy;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if ((deploy && Robot.intake.getHopperTracker() == Status.deployed) || 
    			(!deploy && Robot.intake.getHopperTracker() == Status.stowed)) {
    		// Hopper is already in desired position, so do nothing
    		waitForMovement = false;
    	} else if (Robot.intake.getIntakeTracker() != Status.deployed) {
    		// Can't move hopper if intake is not deployed (ie., if stowed or unknown or moving)
    		waitForMovement = false;
    	} else {
    		// OK, we can move the hopper
    		waitForMovement = true;
    		Robot.intake.setHopperTracker(Status.unknown);	// put in unknown state while hopper is moving
        	if (deploy) Robot.intake.deployHopper();
        	else { Robot.intake.stowHopper(); }
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (waitForMovement) return (timeSinceInitialized() > Robot.intake.HOPPER_DELAY);
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (waitForMovement) Robot.intake.setHopperTracker(deploy ? Status.deployed : Status.stowed);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
