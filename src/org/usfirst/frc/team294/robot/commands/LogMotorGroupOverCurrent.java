package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.triggers.MotorGroupCurrentTrigger;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogMotorGroupOverCurrent extends Command {
	MotorGroupCurrentTrigger trigger;
	
    public LogMotorGroupOverCurrent(MotorGroupCurrentTrigger trigger) {
    	this.trigger = trigger;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.log.writeLogEcho(trigger.getMotorErrorString());
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
