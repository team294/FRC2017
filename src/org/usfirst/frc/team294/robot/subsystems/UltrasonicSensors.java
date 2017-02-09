package org.usfirst.frc.team294.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class UltrasonicSensors extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Ultrasonic ultra = new Ultrasonic(1,1);
	 public void robotInit() {
	    	ultra.setAutomaticMode(true); // turns on automatic mode
	    }
	 public void ultrasonicDistance() {
	    	double range = ultra.getRangeInches(); // reads the range on the ultrasonic sensor
	 }
	 
	 public boolean collisionPrevention(){
		 double range = ultra.getRangeInches();
		 if (range < 2){ //this is a placeholder value, replace with whatever distance we need from the wall to be able to come to a full stop
			return true;
		 }
		 else return false;
	 }
	 
	 public void updateSmartDashboard(){
		 SmartDashboard.putDouble("Ultrasonic Distance", ultra.getRangeInches());
	 }
	 
	 
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    
    }
}

