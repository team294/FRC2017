package org.usfirst.frc.team294.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class GearGateRetractSequence extends CommandGroup {

    public GearGateRetractSequence() {
    	addSequential(new GearShieldMove(false));
    	addSequential(new GearPunch(false));
    }
}
