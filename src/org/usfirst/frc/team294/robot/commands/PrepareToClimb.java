package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class PrepareToClimb extends CommandGroup {

    public PrepareToClimb() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	addSequential(new MoveIntakeIfSafe(true));
    	addSequential(new MoveHopperIfSafe(false));
    	addSequential(new ClimbSetToSpeed(0.4));
    	addSequential(new CheckClimberCurrent(40, .4));
    	addSequential(new WaitSeconds(1));
    	addSequential(new ClimbSetToSpeed(0.5));
    	addSequential(new CheckClimberCurrent(40, .8));
    	addSequential(new ClimbSetToSpeed(0.8));
    	addSequential(new CheckClimberCurrent(40, 1.5));
    	addSequential(new ClimbSetToSpeed(0));
    }
}
