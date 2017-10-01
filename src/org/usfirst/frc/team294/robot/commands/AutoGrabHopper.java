package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;
import org.usfirst.frc.team294.robot.RobotMap.Teams;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;
import org.usfirst.frc.team294.robot.commands.GyroTurnToAngle.TurnMode;
import org.usfirst.frc.team294.robot.commands.ConveyorSetFromRobot.States;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */

public class AutoGrabHopper extends CommandGroup {

	public AutoGrabHopper(Teams team) {
    	addSequential(new LogMessage("Autonomous: Starting Grab Hopper Command for " + team , true));
 
		switch(team){
		case hopperRed:
			addParallel(new AutoPrepareToShoot());
			addSequential(new DriveStraightDistance(0.9, -90, 2.0, Units.inches, true, true));
			addSequential(new GyroTurnToAngle(0.7, -90));
			addParallel(new IntakeSetToSpeed(-Robot.intakeSpeed));
			addSequential(new DriveStraightDistance(0.9, 42, 1.5, Units.inches, true, true));
			addSequential(new WaitSeconds(0.5));
			addSequential(new DriveStraightDistance(0.9, -16, 2.0, Units.inches, true, true));//-30
			addSequential(new GyroTurnToAngle(0.7, 82));//90
			addSequential(new DriveStraightDistance(0.9, 30, 2.0, Units.inches, true, true));
			addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.BOILER_VISION));
	    	addSequential(new WaitSeconds(0.2));
	    	addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.BOILER_VISION));
			//addSequential(new GyroTurnToAngle(.9, -50));
			//addSequential(new DriveStraightDistance(0.4, 12, Units.inches, true, true));
			addSequential(new ConveyorSetFromRobot(States.in));
			break;
		case hopperBlue:
			addParallel(new AutoPrepareToShoot());
			addSequential(new DriveStraightDistance(0.9, -90, 2.0, Units.inches, true, true));
			addSequential(new GyroTurnToAngle(0.7, 90));
			addParallel(new IntakeSetToSpeed(-Robot.intakeSpeed));
			addSequential(new DriveStraightDistance(0.9, 42, 1.5, Units.inches, true, true));
			addSequential(new WaitSeconds(0.5));
			addSequential(new DriveStraightDistance(0.9, -16, 2.0, Units.inches, true, true));//-30
			addSequential(new GyroTurnToAngle(0.7, -82));//90
			addSequential(new DriveStraightDistance(0.9, 30, 2.0, Units.inches, true, true));
			addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.BOILER_VISION));
	    	addSequential(new WaitSeconds(0.2));
	    	addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.BOILER_VISION));
			//addSequential(new GyroTurnToAngle(.9, -50));
			//addSequential(new DriveStraightDistance(0.4, 12, Units.inches, true, true));
			addSequential(new ConveyorSetFromRobot(States.in));
			break;
		}
	}
}