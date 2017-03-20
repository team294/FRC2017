package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.commands.DriveStraightDistance.Units;
import org.usfirst.frc.team294.robot.commands.ConveyorSetFromRobot.States;
import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoGrabHopper extends CommandGroup {

    public AutoGrabHopper() {
    	addSequential(new DriveStraightDistance(0.9, -75, Units.inches, true, true));
    	addParallel(new ShooterSetRPM(Robot.shootSpeedLowRPM));
    	addParallel(new DeployIntakeAndHopper());
    	addSequential(new GyroTurnToAngle(0.7, -90));
    	addSequential(new DriveStraightDistance(0.4, 16, Units.inches, true, true));
    	addSequential(new WaitSeconds(1.5));
    	addSequential(new DriveStraightDistance(0.4, -16, Units.inches, true, true));
    	addSequential(new GyroTurnToAngle(0.7, 90));
    	addSequential(new DriveStraightDistance(0.4, 40, Units.inches, true, true));
    	addSequential(new GyroTurnToAngle(.7, 20));
    	addSequential(new ConveyorSetFromRobot(States.in));
    	
    	
    	
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
    }
}
