package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.RobotMap.AutoAngles;
import org.usfirst.frc.team294.robot.RobotMap.AutoDistances;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot on to the gear peg that requires a right turn (different depending on which side of the field)
 */
public class AutoDriveAndGearRight extends CommandGroup {

    public AutoDriveAndGearRight() {
    	// Speeds 0.7 for testing purposes
        addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toBaseLine), Units.inches, false, true));
        // Extra segment for testing
        // addSequential(new DriveStraightDistance(0.4, -20, Units.inches, true, true));
        addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.rightGear)));
        // Wait seconds to give the cameras a clearer picture
        addSequential(new WaitSeconds(0.5));
        addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
        addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGear), Units.inches, true, true));
    }
}
