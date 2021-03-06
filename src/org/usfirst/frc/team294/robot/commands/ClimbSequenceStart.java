package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ClimbSequenceStart extends CommandGroup {

    public ClimbSequenceStart() {
    	addSequential(new StopAllMotors());
    	addSequential(new MoveIntakeIfSafe(true));
    	addSequential(new MoveHopperIfSafe(false));
    	addSequential(new ClimbSetToSpeed(0.25));
    	addSequential(new WaitSeconds(0.5));
    	addSequential(new CheckClimberCurrent(2.5));
    	addSequential(new ClimbSetToSpeed(0.4));
    	addSequential(new WaitSeconds(0.5));
    	addSequential(new CheckClimberCurrent(10));
    	addSequential(new ClimbSetToSpeed(0.7));
    	addSequential(new WaitSeconds(0.5));
    	addSequential(new CheckClimberCurrent(16));
    	addSequential(new ClimbSetToSpeed(.9));			
    	addSequential(new WaitSeconds(0.5));				
//    	addSequential(new CheckClimberCurrent(70));		
//    	addSequential(new ClimbSetToSpeed(0));
    }
}
