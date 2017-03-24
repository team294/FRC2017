package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.RobotMap.AutoAngles;
import org.usfirst.frc.team294.robot.RobotMap.AutoDistances;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;
import org.usfirst.frc.team294.robot.RobotMap.Teams;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

/**
 * Scores a gear according to the starting position of the robot
 */
public class AutoDriveAndGear extends CommandGroup {

	/**
	 * Drives backwards and delivers a gear based on starting position
	 * @param position StartPositions.left,middle,right
	 */
    public AutoDriveAndGear(Teams team, StartPositions position) {
    	
    	// Shift down and drive to the baseline
    	addSequential(new ShiftDown());
    	addSequential(new MoveGearGate(false));
    	
    	double extraDistance = (team == Teams.red) ? 4.0 : 1.0;
    	
//    	addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toBaseLine), Units.inches, false, true));
        

        // Add turn and additional drive commands for left and right
	    if (Robot.smartDashboardDebug) {
		    SmartDashboard.putNumber("Autonomous Position", position.ordinal());
        }    	
        switch(position) {
        case left: //close edge of the frame perimeter (not bumper) is 77" from center
        	addSequential(new DriveStraightDistance(0.4, -82, Units.inches, true, true));
        	//addSequential(new WaitSeconds(0.2));
        	addSequential(new GyroTurnToAngle(0.7, -58));
            //addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.leftGear)));
            addSequential(new WaitSeconds(0.2));
            addSequential(new MoveGearGate(true));
            // Turn using gear vision and then advance the final segment
            addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            //addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, -30, Units.inches, true, true));
            addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            //addSequential(new WaitSeconds(0.2));
            addSequential(new DriveNotStraightDistance(0.4, -extraDistance -22, Units.inches, true, true));
            //addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearSide), Units.inches, false, true));
            addSequential(new MoveGearGate(true));
            break;
        case right:
            /*addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.rightGear)));        	
        	addSequential(new WaitSeconds(0.2));

            // Turn using gear vision and then advance the final segment
        	addSequential(new GyroTurnToAngle(0.4, 0.0, 4.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearSide), Units.inches, false, true));
            addSequential(new MoveGearGate(true));
            break;*/
        	addSequential(new DriveStraightDistance(0.4, -68, Units.inches, true, true));
        	//addSequential(new WaitSeconds(0.2));
        	addSequential(new GyroTurnToAngle(0.7, 65));//over turn on right side for camera fov
            //addSequential(new GyroTurnToAngle(0.7, RobotMap.getAngle(AutoAngles.leftGear)));
            addSequential(new WaitSeconds(0.2));
            
            // Turn using gear vision and then advance the final segment
            addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            //addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, -30, Units.inches, true, true));
            addSequential(new WaitSeconds(0.2));
            addSequential(new MoveGearGate(true));
            // Turn using gear vision and then advance the final segment
            addSequential(new GyroTurnToAngle(0.4, 0.0, 2.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
            //addSequential(new WaitSeconds(0.2));
            addSequential(new DriveStraightDistance(0.4, -20 -extraDistance, Units.inches, true, true));
            addSequential(new DriveNotStraightDistance(0.25, -13.0, Units.inches, true, true));
            //addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearSide), Units.inches, false, true));
            addSequential(new MoveGearGate(true));
            break;
        case middle:
        	// Turn using gear vision and then advance the final segment
        	/*double angleOffset = Robot.gearVision.getGearAngleOffset();
        	addSequential(new GyroTurnToAngle(0.4, Math.signum(-angleOffset)*90, 2.0, GyroTurnToAngle.TurnMode.RELATIVE));
        	addSequential(new WaitSeconds(0.2));
        	addSequential(new DriveStraightDistance(0.4, -38/Math.tan((90-Math.abs(angleOffset))*Math.PI/180), Units.inches, true, true));
        	addSequential(new WaitSeconds(0.2));
        	addSequential(new GyroTurnToAngle(0.4, Math.signum(angleOffset)*90, 2.0, GyroTurnToAngle.TurnMode.RELATIVE));
        	addSequential(new WaitSeconds(0.2));
        	angleOffset = Robot.gearVision.getGearAngleOffset();
        	addSequential(new GyroTurnToAngle(0.4, -angleOffset, 2.0, GyroTurnToAngle.TurnMode.RELATIVE));
        	addSequential(new WaitSeconds(0.2));
        	addSequential(new MoveGearGate(true));
        	addSequential(new WaitSeconds(0.8));
        	addSequential(new DriveStraightDistance(0.4, -38/Math.cos(angleOffset*Math.PI/180), Units.inches, true, true));
        	*/
        	addSequential(new DriveStraightDistance(0.4, -55, Units.inches, true, true));
        	addSequential(new WaitSeconds(0.2));
        	double angleOffset = Robot.gearVision.getGearAngleOffset();
        	addSequential(new GyroTurnToAngle(0.4, -angleOffset, 2.0, GyroTurnToAngle.TurnMode.RELATIVE));
        	//addSequential(new WaitSeconds(0.2));
        	addSequential(new MoveGearGate(true));
        	//addSequential(new WaitSeconds(0.2));
        	//addSequential(new DriveStraightDistance(0.4, -20/Math.cos(angleOffset*Math.PI/180), Units.inches, true, true));
        	addSequential(new DriveStraightDistance(0.4, -extraDistance -38/Math.cos(angleOffset*Math.PI/180), Units.inches, true, true));
        	

//            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearMiddle)*0.65, Units.inches, false, true));
//            addSequential(new WaitSeconds(0.2));
//            addSequential(new GyroTurnToAngle(0.4, 0.0, 0.2, GyroTurnToAngle.TurnMode.GEAR_VISION));
//            addSequential(new WaitSeconds(0.2));
//            addSequential(new DriveStraightDistance(0.4, RobotMap.getDistance(AutoDistances.toGearMiddle)*0.35, Units.inches, false, true));

        	addSequential(new MoveGearGate(true));
            break;
        case baselineOnly:
        	addSequential(new DriveStraightDistance(0.4, -90, Units.inches, true, true));
        	break;
        }
        
        // Wait for human player to raise the gear/peg
        addSequential(new MoveGearGate(true));
        addSequential(new DeployIntakeAndHopper());
        //addSequential(new ShooterSetRPM(Robot.shootHighSpeed));
    }
    
    // This should write to the file log when the command is called instead of when the robot powers up	
    protected void initialize() {
    	Robot.log.writeLog("Autonomous: Starting Gear Command");
    }
}
