package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.RobotMap.AutoAngles;
import org.usfirst.frc.team294.robot.RobotMap.AutoDistances;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot to the gear peg that requires a turn to the left (different on left and right side of the field)
 */
public class AutoDriveAndGearLeft extends CommandGroup {

    public AutoDriveAndGearLeft() {
    	// Speeds 0.7 for testing purposes
        addSequential(new DriveStraightDistance(0.7, RobotMap.getDistance(AutoDistances.toBaseLine), Units.inches, false, true));
        addSequential(new WaitSeconds(.2));
        addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.leftGear)));
        addSequential(new WaitSeconds(.2));
        addSequential(new DriveStraightDistance(0.7, RobotMap.getDistance(AutoDistances.toGear), Units.inches, false, true));
        // will need to add vision tracking
    }
}
