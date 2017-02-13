package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Why does this exist? Why not use ShooterSetToSpeed(0.0) ?
 * 
 * This exists because if you use ShooterSetToSpeed(0,0), the system will effectively brake to zero possibly damaging
 *  belts and motors.  This routine sets the motors to  zero volts in voltage mode which will let the motors coast to a stop.
 */
public class ShooterStop extends Command {

    public ShooterStop() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.setVoltage(0.0);
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
