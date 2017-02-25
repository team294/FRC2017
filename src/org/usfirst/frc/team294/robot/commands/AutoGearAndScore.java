package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.RobotMap.Teams;
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

    	// Shift down and do a gear sequence according to the position given
    	addSequential(new ShiftDown());
    	addSequential(new AutoDriveAndGear(position));
    	addSequential(new WaitSeconds(.2));
    	
    	// Move to the boiler according to the team colour and starting position
    	// This can be consolidated and made more modular in the near future
    	if (team == Teams.red) {
    		switch (position) {
    		case left:
    	    	addSequential(new AutoScoreBoilerFromLeftRed());
    	    	break;
    		case right:
    	    	addSequential(new AutoScoreBoilerFromRightRed());
    	    	break;
    		case middle:
    	    	addSequential(new AutoScoreBoilerFromMiddleRed());
    		}
    	} else {
    		switch (position) {
    		case left:
    	    	addSequential(new AutoScoreBoilerFromLeftBlue());
    	    	break;
    		case right:
    	    	addSequential(new AutoScoreBoilerFromRightBlue());
    	    	break;
    		case middle:
    	    	addSequential(new AutoScoreBoilerFromMiddleBlue());
    		}
    	}
    }
}
