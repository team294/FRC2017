package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.subsystems.Intake.Positions;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class MoveIntake extends Command {

	private boolean position;
	private boolean waitForMovement;
	
	/**
	 * Set the position of the intake only if safe to do so
	 * @param position true for deployed, false for stowed
	 */
    public MoveIntake(boolean position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    	
    	this.position = position;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if ((position && Robot.intake.getIntakeTracker() == Positions.deployed) || 
    			(!position && Robot.intake.getIntakeTracker() == Positions.stowed)) {
    		waitForMovement = false;
    	} else if (Robot.intake.getHopperTracker() != Positions.stowed) {
    		waitForMovement = false;
    	} else {
    		waitForMovement = true;
    		Robot.intake.setIntakeTracker(Positions.unknown);
        	if (position) Robot.intake.deployIntake();
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
    	if (waitForMovement) Robot.intake.setHopperTracker(position ? Positions.deployed : Positions.stowed);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
