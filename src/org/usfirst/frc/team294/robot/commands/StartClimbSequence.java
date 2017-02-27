package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class StartClimbSequence extends CommandGroup {

	private double highCurrent = 30;

    public StartClimbSequence() {

    	addSequential(new MoveIntakeIfSafe(true));
    	addSequential(new MoveHopperIfSafe(false));
    	addSequential(new StopAllMotors());
    	addSequential(new ClimbSetToSpeed(0.4));
    	addSequential(new WaitSeconds(1));
    	addSequential(new CheckClimberCurrent(10));
    	addSequential(new WaitSeconds(1));
    	addSequential(new ClimbSetToSpeed(0.7));
    	addSequential(new WaitSeconds(2));
    	addSequential(new CheckClimberCurrent(16));
    	addSequential(new ClimbSetToSpeed(.8));
    	addSequential(new CheckClimberCurrent(38));
    	addSequential(new ClimbSetToSpeed(0));
    }
}
