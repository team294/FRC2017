package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGearAndScore extends CommandGroup {

	public enum Positions {
		left, middle, right
	}
	
	public enum Teams {
		blue, red
	}
	
	/**
	 * Autonomous Command to deliver gear and shoot
	 * @param team Teams.red,blue
	 * @param position Positions.left,middle,right
	 */
    public AutoGearAndScore(Teams team, Positions position) {
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
    	addSequential(new ShiftDown());
    	if (team == Teams.red) {
    		switch (position) {
    		case left:
    	    	addSequential(new AutoDriveAndGearLeft());
    	    	addSequential(new WaitSeconds(.2));
    	    	addSequential(new AutoScoreBoilerFromLeftRed());
    	    	break;
    		case right:
    	    	addSequential(new AutoDriveAndGearRight());
    	    	addSequential(new WaitSeconds(.2));
    	    	addSequential(new AutoScoreBoilerFromRightRed());
    	    	break;
    		case middle:
    	    	addSequential(new AutoDriveAndGearMiddle());
    	    	addSequential(new WaitSeconds(.2));
    	    	addSequential(new AutoScoreBoilerFromMiddleRed());
    		}
    	} else {
    		switch (position) {
    		case left:
    	    	addSequential(new AutoDriveAndGearLeft());
    	    	addSequential(new WaitSeconds(.2));
    	    	addSequential(new AutoScoreBoilerFromLeftBlue());
    	    	break;
    		case right:
    	    	addSequential(new AutoDriveAndGearRight());
    	    	addSequential(new WaitSeconds(.2));
    	    	addSequential(new AutoScoreBoilerFromRightBlue());
    	    	break;
    		case middle:
    	    	addSequential(new AutoDriveAndGearMiddle());
    	    	addSequential(new WaitSeconds(.2));
    	    	addSequential(new AutoScoreBoilerFromMiddleBlue());
    		}
    	}
    }
}
