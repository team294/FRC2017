package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartClimbSequence extends CommandGroup {

    public StartClimbSequence() {

    	addSequential(new MoveIntakeIfSafe(true));
    	addSequential(new MoveHopperIfSafe(false));
    	addSequential(new StopAllMotors());
    	addSequential(new ClimbSetToSpeed(0.4));
    	addSequential(new WaitSeconds(1));
    	addSequential(new CheckClimberCurrent(10));
//    	addSequential(new ClimbSetToSpeed(0.7));
//    	addSequential(new WaitSeconds(1));
//    	addSequential(new CheckClimberCurrent(16));
    	addSequential(new ClimbSetToSpeed(.9));			//  test this.  If it works increase to 1.0
    	addSequential(new WaitSeconds(1));				// Rob added after analyzing current graph from last night
    	addSequential(new CheckClimberCurrent(40));		// We had increased this to 38 because it stopped here, but we didn't have delay - try 30 and measure
    	addSequential(new ClimbSetToSpeed(0));
    }
}
