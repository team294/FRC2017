package org.usfirst.frc.team294.robot.commands;




import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

<<<<<<< HEAD:src/org/usfirst/frc/team294/robot/commands/StopAllMotors.java
/**
<<<<<<< HEAD:src/org/usfirst/frc/team294/robot/commands/StopAllMotors.java
 * Stops all motors except the drive train
=======
 *              NOT USED.  SEE ConveyorSetToVoltage()
>>>>>>> refs/remotes/origin/master:src/org/usfirst/frc/team294/robot/commands/ConveyorSetToSpeed.java
 */
public class StopAllMotors extends Command {

	/**
	 * Stops all motors except the drive train
	 */
    public StopAllMotors() {
=======

 
public class MoveHopper extends Command {

	private boolean position;
	

	 /* Set the position of the hopper
	 * @param position true for deployed, false for stowed
	 */
	
    public MoveHopper(boolean position) {
>>>>>>> refs/remotes/origin/master:src/org/usfirst/frc/team294/robot/commands/MoveHopper.java
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.shooter);
    	requires(Robot.ballFeed);
    	requires(Robot.intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.shooter.stop();
    	Robot.ballFeed.stop();
    	Robot.intake.stopIntake();
    	Robot.intake.stopClimber();
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
       	Robot.shooter.stop();
    	Robot.ballFeed.stop();
    	Robot.intake.stopIntake();
    	Robot.intake.stopClimber();
    }
}


