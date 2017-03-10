package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;
import org.usfirst.frc.team294.robot.RobotMap.Teams;
import org.usfirst.frc.team294.robot.commands.ConveyorSetFromRobot.States;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.DriveMode;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;
import org.usfirst.frc.team294.robot.commands.GyroTurnToAngle.TurnMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Score on the boiler based on where the robot ends up from a gear sequence
 */
public class AutoScoreBoiler extends CommandGroup {

	/**
	 * Scores based on where the robot ends up <b>after scoring a gear</b>
	 * @param team Teams.red,blue
	 * @param position StarPositions.left,middle,right
	 */
    public AutoScoreBoiler(Teams team, StartPositions position) {

    	Robot.log.writeLog("Auonomous: Starting Score Command for " + team + "and" + position);
    	
    	if (team == Teams.noBoilerShooting) return;
    	
    	// Rev up the shooter and back off of the gear peg
    	addSequential(new ShooterSetRPM(11000));
    	addSequential(new DriveStraightDistance(1.0, 26.0, DriveMode.RELATIVE, Units.inches));
    	addSequential(new WaitSeconds(.2));
    	
    	// Turn according to the team colour and position
    	if (team == Teams.red) {
    		switch(position) {
    		case right:
    	    	addSequential(new DriveStraightDistance(1.0, 26.0, DriveMode.RELATIVE, Units.inches));
    	    	addSequential(new WaitSeconds(.2));
    	      	addSequential(new GyroTurnToAngle(1, -130, 0, TurnMode.RELATIVE));
    	      	addSequential(new WaitSeconds(.2));
    	       	break;
    		case middle:
    			addSequential(new GyroTurnToAngle(1, -75, 0, TurnMode.RELATIVE));
    	      	addSequential(new WaitSeconds(.2));
    	      	break;
    		case left:
    			// Nothing is done on red right
    	      	break;
			default:
				break;
    		}
    	} else {
    		switch(position) {
    		case right:
    			// Nothing is done on blue left
    			break;
    		case middle:
    			addSequential(new GyroTurnToAngle(1, 75, 0, TurnMode.RELATIVE));
    	      	addSequential(new WaitSeconds(.2));
    	      	break;
    		case left:
    	    	addSequential(new DriveStraightDistance(1.0, 26.0, DriveMode.RELATIVE, Units.inches));
    	    	addSequential(new WaitSeconds(.2));
    	      	addSequential(new GyroTurnToAngle(1, 130, 0, TurnMode.RELATIVE));
    	      	addSequential(new WaitSeconds(.2));
    	      	break;
			default:
				break;
    		}
    	}
    	
    	// Turn towards the boiler and drive forward
      	addSequential(new GyroTurnToAngle(1, .75, 1, TurnMode.BOILER_VISION));
      	addSequential(new WaitSeconds(.2));
    	addSequential(new DriveStraightDistance(1.0, 0.0, DriveMode.BOILER_VISION, Units.inches));
       	addSequential(new WaitSeconds(.2));
       	
       	// Shoot
       	addSequential(new ConveyorSetFromRobot(States.in));
    }
}
