package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

<<<<<<< HEAD:src/org/usfirst/frc/team294/robot/commands/ShooterSetToSpeed.java

public class ShooterSetToSpeed extends Command {
	
	private double speed;

	/**
	 * Set the shooter to speed according to Vbus
	 * @param speed between -1 and 1
	 */
    public ShooterSetToSpeed(double speed) {
        requires(Robot.shooter);
        this.speed = speed;
=======
/**
 *
 */
public class SetShooterSpeed extends Command {
	double speed = 0;

    public SetShooterSpeed(double speed) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    	this.speed = speed;
>>>>>>> origin/Add-Shooter:src/org/usfirst/frc/team294/robot/commands/SetShooterSpeed.java
    }

    // Called just before this Command runs the first time
    protected void initialize() {
<<<<<<< HEAD:src/org/usfirst/frc/team294/robot/commands/ShooterSetToSpeed.java
    	// Validate inputs first
    	if (speed > 1.0) speed = 1.0;
    	if (speed < -1.0) speed = -1.0;
    	Robot.shooter.setSpeed(speed);
    	
    	// Write log of shooting
    	Robot.log.writeLogEcho("Shooter: Setting Shooting Speed " + speed);
=======
    	Robot.shooter.setShooterMotorToSpeed(speed);
>>>>>>> origin/Add-Shooter:src/org/usfirst/frc/team294/robot/commands/SetShooterSpeed.java
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
