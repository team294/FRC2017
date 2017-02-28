package org.usfirst.frc.team294.robot.triggers;

import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class TeleopTimer extends Trigger {
	double timeLimit;
	
	/**
	 * Timer in seconds
	 * @param limit (time)
	 */
	public TeleopTimer (double limit) {
		timeLimit = limit;
	}

    public boolean get() {
    	
        return false;//Robot.teleopTime.get() >= timeLimit;
    }
}
