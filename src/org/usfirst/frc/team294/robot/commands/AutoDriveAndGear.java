package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.RobotMap.AutoAngles;
import org.usfirst.frc.team294.robot.RobotMap.AutoDistances;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

/**
 * Scores a gear according to the starting position of the robot
 */
public class AutoDriveAndGear extends CommandGroup {

	/**
	 * Drives backwards and delivers a gear based on starting position
	 * @param position StartPositions.left,middle,right
	 */
    public AutoDriveAndGear(StartPositions position) {

    	Robot.log.writeLog("Autonomous: Starting Gear Command for " + position);
    	
    	// Shift down and drive to the baseline
    	addSequential(new ShiftDown());
        addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toBaseLine), Units.inches, false, true));
        addSequential(new WaitSeconds(0.2));

        // Add turn and additional drive commands for left and right
        switch(position) {
        case left:
            addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.leftGear)));
            addSequential(new WaitSeconds(0.2));
            break;
        case right:
            addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.rightGear)));        	addSequential(new WaitSeconds(0.2));
        	addSequential(new WaitSeconds(0.2));
        default:
        	// Nothing should be done for a middle sequence
        	break;
        }
        
        // Turn using gear vision and then advance the final segment
        addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
        addSequential(new WaitSeconds(0.2));
        addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGear), Units.inches, true, true));
    }
}
