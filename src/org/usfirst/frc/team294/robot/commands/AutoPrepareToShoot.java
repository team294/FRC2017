package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoPrepareToShoot extends CommandGroup {

    public AutoPrepareToShoot() {
    	
    	addSequential(new ShiftDown());
    	addSequential(new GearGateTilt(false));
    	addSequential(new CameraActivate(false, false));  // Turn on gear camera

    	addParallel(new DeployIntakeAndHopper());
        addParallel(new ShooterSetRPM(Robot.shootSpeedLowRPM));
        addSequential(new WaitSeconds(0.4));

    }
}
