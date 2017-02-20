package org.usfirst.frc.team294.robot.commands;




import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DeployHopper extends Command {

    public DeployHopper() {

 
public class MoveHopper extends Command { //Why?? -John

	private boolean position;
	

	 /* Set the position of the hopper
	 * @param position true for deployed, false for stowed
	 */
	
    public MoveHopper(boolean position) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(!Robot.intake.getIntakePosition()) Robot.intake.deployIntake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.deployHopper();
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


