package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LogMessage extends Command {
	private String msg;
	private boolean echo;
	
	/**
	 * Writes a message to the log file.  This is mostly helpful in a command sequence, so that the message
	 * is written when the command sequence is run, instead of when the command sequence is created.
	 * @param msg Message to write to log file
	 * @param echo true to also echo the message to the console, false to only write the message to the log file.
	 */
    public LogMessage(String msg, boolean echo) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.msg = msg;
    	this.echo = echo;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if (echo)
    		Robot.log.writeLogEcho(msg);
    	else
    		Robot.log.writeLog(msg);
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
