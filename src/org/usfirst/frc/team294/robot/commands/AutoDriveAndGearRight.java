package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot on to the gear peg that requires a right turn (different depending on which side of the field)
 */
public class AutoDriveAndGearRight extends CommandGroup {

    public AutoDriveAndGearRight() {
    	// Speeds 0.7 for testing purposes
        addSequential(new DriveStraightDistance(0.4, -93.25, Units.inches, false, true));
        // Extra segment for testing
        addSequential(new DriveStraightDistance(0.4, -20, Units.inches, true, true));
        addSequential(new GyroTurnToAngleRelative(30, 0.7));
        // This should turn the robot to the gear (no offset on camera)
//      addSequential(new GyroTurnToAngleRelative(0.0, 0.4, true));
        addSequential(new DriveStraightDistance(0.4, -35.25, Units.inches, true, true));
    }
}
