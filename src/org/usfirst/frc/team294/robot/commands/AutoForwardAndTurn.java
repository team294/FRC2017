package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Drive forward, turn, and drive forward again.
 */
public class AutoForwardAndTurn extends CommandGroup {

    public AutoForwardAndTurn() {
    
    	addParallel(new ShiftDown());
    	addParallel(new IntakeSetToSpeed(0.7));
    	addParallel(new DriveStraightDistance(0.4, 10.0, Units.inches));
    	addSequential(new GyroTurnToAngleRelative(45.0, 0.4));
    	addSequential(new DriveStraightDistance(0.4, 5.0, Units.inches));
    	addSequential(new WaitSeconds(0.5));
    	addSequential(new ShooterSetToSpeed(0.7));
    }
}
