package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.RobotMap.AutoDistances;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drives the robot to the gear peg straight ahead (or behind) in the middle
 */
public class AutoDriveAndGearMiddle extends CommandGroup {

    public AutoDriveAndGearMiddle() {
    	// Speeds 0.7 for testing purposes
        addSequential(new DriveStraightDistance(0.7, RobotMap.getDistance(AutoDistances.toBaseLine), Units.inches, false, true));       
        // will probably need to shorten distance to allow vision to bring in robot
    }
}
