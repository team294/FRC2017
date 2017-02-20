package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.DriveMode;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;
import org.usfirst.frc.team294.robot.commands.GyroTurnToAngle.TurnMode;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *skeleton command for scoring in the boiler in autonomous
 *parameters in the command not accurate(well, I mean, they are pretty accurate, because they are written by me, and I am very smart)
 */
public class AutoScoreBoilerFromMiddleRed extends CommandGroup {

    public AutoScoreBoilerFromMiddleRed() {
    	
    	//addSequential(new ShooterSetToSpeed(12000)); // Need a new command for rpm. ShooterSetToSpeed uses Vbus
    	
    	
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
    	addSequential(new ShooterSetRPM(11000));
    	addSequential(new DriveStraightDistance(1.0, 26.0, DriveMode.RELATIVE, Units.inches));
    	addSequential(new WaitSeconds(.2));
      	addSequential(new GyroTurnToAngle(1, -75, 0, TurnMode.RELATIVE));
      	addSequential(new WaitSeconds(.2));
      	addSequential(new GyroTurnToAngle(1, .75, 1, TurnMode.BOILER_VISION));
      	addSequential(new WaitSeconds(.2));
       	addSequential(new DriveToBoiler());
       	addSequential(new WaitSeconds(.2));
       	addSequential(new ConveyorSetToVoltage());
    }
}
