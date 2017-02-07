package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot to the gear peg that requires a turn to the left (different on left and right side of the field)
 */
public class AutoDriveAndGearLeft extends CommandGroup {

    public AutoDriveAndGearLeft() {
    	// Speeds 0.7 for testing purposes
        addParallel(new DriveStraightDistance(0.7, -93.25, Units.inches));
        addSequential(new GyroTurnToAngleRelative(-30, 0.7));
        addSequential(new DriveStraightDistance(0.7, -35.25, Units.inches));
        // will need to add vision tracking
        Robot.log.writeLog("Autonomous Gear Routine Left Completed Successfully");
    }
}
