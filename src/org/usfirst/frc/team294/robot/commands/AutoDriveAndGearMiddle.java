package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot to the gear peg straight ahead (or behind) in the middle
 */
public class AutoDriveAndGearMiddle extends CommandGroup {

    public AutoDriveAndGearMiddle() {
    	// Speeds 0.4 for testing purposes
        addParallel(new DriveStraightDistance(0.7, -93.25, Units.inches, false, true));
        // will probably need to shorten distance to allow vision to bring in robot
    }
}
