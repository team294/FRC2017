package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
<<<<<<< HEAD:src/org/usfirst/frc/team294/robot/commands/ShooterSetRPM.java
 * Sets the shooter according to rpm
=======
 * Why does this exist? Why not use ShooterSetToSpeed(0.0) ?
 * 
 * This exists because if you use ShooterSetToSpeed(0,0), the system will effectively brake to zero possibly damaging
 *  belts and motors.  This routine sets the motors to  zero volts in voltage mode which will let the motors coast to a stop.
>>>>>>> origin/Add-Shooter:src/org/usfirst/frc/team294/robot/commands/ShooterStop.java
 */
public class ShooterSetRPM extends Command {

	private double rpm;
	
    public ShooterSetRPM(double rpm) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.rpm = rpm;
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.setRPM(rpm);
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
