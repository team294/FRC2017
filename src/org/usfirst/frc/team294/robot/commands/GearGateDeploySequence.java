package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearGateDeploySequence extends CommandGroup {

    public GearGateDeploySequence() {
    	addSequential(new GearActuateShield(true, true));
    	addSequential(new GearPunch(true));
    	addSequential(new WaitSeconds(1.0));
    	addSequential(new GearPunch(false));
    }
}
