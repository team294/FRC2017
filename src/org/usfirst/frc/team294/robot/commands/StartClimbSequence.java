package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartClimbSequence extends CommandGroup {

    public StartClimbSequence() {

    	addSequential(new MoveIntakeIfSafe(true));
    	addSequential(new MoveHopperIfSafe(false));
    	addSequential(new ClimbSetToSpeed(0.4));
    	addSequential(new CheckClimberCurrent(30, .4));
    	addSequential(new WaitSeconds(1));
    	addSequential(new ClimbSetToSpeed(0.5));
    	addSequential(new CheckClimberCurrent(30, .8));
    	addSequential(new ClimbSetToSpeed(0.8));
    	addSequential(new CheckClimberCurrent(30, 1.3));
    	addSequential(new ClimbSetToSpeed(0));
    }
}
