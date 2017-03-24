package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CameraActivate extends Command {
	private boolean boilerDirection;
	private boolean wait;
	
	/**
	 * Activates the given camera direction and associated light.
	 * @param boilerDirection true = boiler direction, false = gear direction
	 * @param waitIfSwitched true = wait to clear camera pipeline (only if direction needed to be switched), false = never wait
	 */
    public CameraActivate(boolean boilerDirection, boolean waitIfSwitched) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.boilerDirection = boilerDirection;
    	wait = waitIfSwitched;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (Robot.cameraControl.isCamerasOn() && (Robot.cameraControl.isBoilerCurrentDirection() == boilerDirection)) {
    		// We are already on and in the right direction
    		wait = false;
    	} else {
    		// Set direction
    		if (boilerDirection)
    			Robot.cameraControl.activateBoilerCamera();
    		else
    			Robot.cameraControl.activateGearCamera();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (!wait || (timeSinceInitialized()>=0.5));
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
