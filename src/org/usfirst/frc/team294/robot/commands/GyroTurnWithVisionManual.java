package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GyroTurnWithVisionManual extends CommandGroup {

	/**
	 * Activates the given camera/light direction (if needed) and turns toward target using vision
	 * @param boilerDirection true = boiler direction, false = gear direction
	 */
    public GyroTurnWithVisionManual(boolean boilerDirection) {

    	addSequential(new CameraActivate(boilerDirection, true));
    	if (boilerDirection)
    		addSequential(new GyroTurnToAngle(0.6, 0.0, 2.0, GyroTurnToAngle.TurnMode.BOILER_VISION));
    	else
    		addSequential(new GyroTurnToAngle(0.8, 0.0, 2.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
    	
    }
}
