package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot to the gear peg that requires a turn to the left (different on left and right side of the field)
 */
public class AutoDriveAndGearLeft extends CommandGroup {

    public AutoDriveAndGearLeft() {
    	// Speeds 0.7 for testing purposes
        addParallel(new DriveStraightDistance(0.7, RobotMap.distanceToLeftGearBeforeTurn, Units.inches, false, true));
        addSequential(new GyroTurnToAngle(0.7, RobotMap.turnToLeftGear));
        addSequential(new DriveStraightDistance(0.7, RobotMap.distanceToLeftGearAfterTurn, Units.inches, false, true));
        // will need to add vision tracking
    }
}
