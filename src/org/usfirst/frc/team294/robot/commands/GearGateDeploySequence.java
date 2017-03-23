package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearGateDeploySequence extends CommandGroup {

    public GearGateDeploySequence() {
    	addSequential(new GearShieldMove(true));
    	addSequential(new GearPunch(true));
    }
}
