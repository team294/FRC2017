package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CheckClimberCurrent extends Command {

//	double percentage;
//	double maxCurrent;
	double current;
	
	/**
	 * checks average climber current
	 * @param current
	 */
    public CheckClimberCurrent(double current) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
//    	this.maxCurrent = maxCurrent;
//    	this.percentage = currentPercentage;
    	this.current = current;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
//    	current = maxCurrent * percentage;
		Robot.log.writeLogEcho("Start climber current check, current trigger, " + current);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//		Robot.log.writeLogEcho("Climber current ," + Robot.intake.getAverageClimberCurrent() + ", amps." + "climber current set current ," + current + ",");
        return Robot.intake.getAverageClimberCurrent() >= current;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
