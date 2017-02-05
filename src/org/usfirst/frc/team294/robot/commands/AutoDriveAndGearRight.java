package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot on to the gear peg that requires a right turn (different depending on which side of the field)
 */
public class AutoDriveAndGearRight extends CommandGroup {

    public AutoDriveAndGearRight() {
    	// Speeds 0.7 for testing purposes
        addParallel(new DriveStraightDistance(0.7, -93.25, Units.inches));
        addSequential(new GyroTurnToAngleRelative(30, 0.7));
        addSequential(new DriveStraightDistance(0.7, -35.25, Units.inches));
        // will add vision
    }
}
