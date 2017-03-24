package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.RobotMap.Teams;
import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;

import edu.wpi.first.wpilibj.command.CommandGroup; 

/**
 * Deliver a gear and shoot based on the start position and team colour given
 */
public class AutoGearAndScore extends CommandGroup {
	
	/**
	 * Autonomous Command to deliver gear and shoot
	 * @param team Teams.red,blue
	 * @param position Positions.left,middle,right
	 */
    public AutoGearAndScore(Teams team, StartPositions position) {

    	addSequential(new LogMessage("Autonomous: Starting Gear and Score Command for " + team + " and " + position, true));
    	
    	// Do a gear sequence according to the position given
    	addSequential(new AutoDriveAndGear(team, position));
    	
    	// Turn on boiler camera
    	addSequential(new CameraActivate(true, false));
    	
    	// Score on the boiler according to team and position
    	// addSequential(new AutoScoreBoiler(team, position));
    }
}
