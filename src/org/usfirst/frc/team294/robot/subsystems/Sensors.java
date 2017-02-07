package org.usfirst.frc.team294.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Sensors extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Ultrasonic ultra = new Ultrasonic(1,1);
	 public void robotInit() {
	    	ultra.setAutomaticMode(true); // turns on automatic mode
	    }
	 public void ultrasonicDistqance() {
	    	double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor
	    }
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

