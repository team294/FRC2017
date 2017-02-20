package org.usfirst.frc.team294.utilities;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class MotorCurrentTrigger extends Trigger {
    CANTalon talon;
    double limit;
    double duration;
    Timer timer = new Timer();
    
    /**
     * checks if <b>talon</b> is outputting <b>limit</b> amps for <b>duration</b> seconds.
     * @param talon
     * @param limit
     * @param duration
     */
    public MotorCurrentTrigger(CANTalon talon, double limit, double duration) {
        this.talon = talon;
        this.limit = limit;
        this.duration = duration;
        timer.start();
    }

    public boolean get() {
        if (talon.getOutputCurrent() < limit)
            timer.reset();
        return timer.get() >= duration;
    }
}
