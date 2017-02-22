package org.usfirst.frc.team294.utilities;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;



/**
 * checks if <b>talon</b> is at or above <b>limit</b> degrees F for <b>duration</b> seconds.
 * @param talon
 * @param limit
 * @param duration
 */
public class MotorTemperatureTrigger extends Trigger {
	
    CANTalon talon;
    double limit;
    double duration;
    Timer timer = new Timer();

	public MotorTemperatureTrigger (CANTalon talon, double limit, double duration) {
        this.talon = talon;
        this.limit = limit;
        this.duration = duration;
        timer.start();
    }

	
    public boolean get() {
        if (talon.getTemperature() < limit)
            timer.reset();
        return timer.get() >= duration;
    }
}
