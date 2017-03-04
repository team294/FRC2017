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
    	
    	// Shift down and drive to the baseline
    	addSequential(new ShiftDown());
        addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toBaseLine), Units.inches, false, true));
        addSequential(new WaitSeconds(0.2));

        // Add turn and additional drive commands for left and right
        switch(position) {
        case left:
            addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.leftGear)));
            addSequential(new WaitSeconds(0.2));
            
            // Turn using gear vision and then advance the final segment
            //addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearSide), Units.inches, false, true));
            break;
        case right:
            addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.rightGear)));        	
        	addSequential(new WaitSeconds(0.2));

            // Turn using gear vision and then advance the final segment
        	addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearSide), Units.inches, false, true));
        default:
        	// Turn using gear vision and then advance the final segment
        	addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearMiddle)*0.65, Units.inches, false, true));
            addSequential(new WaitSeconds(0.2));
            addSequential(new GyroTurnToAngle(0.4, 0.0, 0.2, GyroTurnToAngle.TurnMode.GEAR_VISION));
            addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearMiddle)*0.35, Units.inches, false, true));
            break;
        }
        addSequential(new MoveGearGate(true));
    }
    
    // This should write to the file log when the command is called instead of when the robot powers up	
    protected void initialize() {
    	Robot.log.writeLog("Autonomous: Starting Gear Command");
    }
}
