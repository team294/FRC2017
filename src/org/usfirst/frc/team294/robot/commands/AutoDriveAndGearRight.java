package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot on to the gear peg that requires a right turn (different depending on which side of the field)
 */
public class AutoDriveAndGearRight extends CommandGroup {

    public AutoDriveAndGearRight() {
    	// Speeds 0.7 for testing purposes
        addSequential(new DriveStraightDistance(0.4, RobotMap.distanceToRightGearBeforeTurn, Units.inches, false, true));
        // Extra segment for testing
        // addSequential(new DriveStraightDistance(0.4, -20, Units.inches, true, true));
        addSequential(new GyroTurnToAngle(0.7, RobotMap.turnToRightGear));
        // This should turn the robot to the gear (no offset on camera)
        addSequential(new WaitSeconds(0.5));
        addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
        addSequential(new DriveStraightDistance(0.4, RobotMap.distanceToRightGearAfterTurn, Units.inches, true, true));
    }
}
