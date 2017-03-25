package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.RobotMap.Teams;
import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;

import edu.wpi.first.wpilibj.command.CommandGroup; 

/**
 * Deliver a gear and shoot based on the start position and team colour given
 */
public class AutoMasterCommand extends CommandGroup {
	
	/**
	 * Autonomous Command to deliver gear and shoot
	 * @param team Teams.red,blue
	 * @param position Positions.left,middle,right
	 */
    public AutoMasterCommand(Teams team, StartPositions position) {

    	addSequential(new LogMessage("Autonomous: Starting Master Command for team " + team + " and position " + position, true));
    	
    	if ((team == Teams.noBoilerShooting) || (team == Teams.blue) || (team == Teams.red)) {
    		
        	// Do a gear sequence according to the position given
        	addSequential(new AutoDriveAndGear(team, position));
        	
        	// Turn on boiler camera
        	addSequential(new CameraActivate(true, false));
        	
        	// Score on the boiler according to team and position
        	// addSequential(new AutoScoreBoiler(team, position));
    	} else {
    		addSequential(new AutoGrabHopper(team));
    	}
    }
}
