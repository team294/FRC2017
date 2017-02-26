package org.usfirst.frc.team294.robot.triggers;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 *
 */
public class POVTrigger extends Trigger {
    Joystick joystick;
    int pov;
    int val;
    
    /**
     * Creates a trigger that is active when the given joystick
     * POV control is pressed in the "val" direction.  
     * @param joystick Joystick to monitor
     * @param pov Joystick POV ID number to montior
     * @param val Direction value to trigger on, in degrees (Up = 0, Right = 90, Down = 180, Left = 270)
     */
    public POVTrigger(Joystick joystick, int pov, int val) {
        this.joystick = joystick;
        this.pov = pov;
        this.val = val;
    }

    /**
     * Creates a trigger that is active when the given joystick
     * POV control (ID = 0) is pressed in the "val" direction.  
     * @param joystick Joystick to monitor
     * @param val Direction value to trigger on, in degrees (Up = 0, Right = 90, Down = 180, Left = 270)
     */
    public POVTrigger(Joystick joystick, int val) {
        this.joystick = joystick;
        this.pov = 0;
        this.val = val;
    }

    public boolean get() {
        return joystick.getPOV(pov) == val;
    }
}
