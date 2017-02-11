package org.usfirst.frc.team294.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.Ultrasonic;

/**
 *
 */
public class UltrasonicSensors extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Ultrasonic vexUltrasonicSensor = new Ultrasonic(8,9);

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	vexUltrasonicSensor.setAutomaticMode(true);
    }

	public void updateSmartDashboard() {
		// TODO Auto-generated method stub
		SmartDashboard.putNumber("2",2.0);
	} 
	
	public double getDistance() {
		return vexUltrasonicSensor.getRangeInches(); // reads the range on the ultrasonic sensor
    }
	
	public void printDistance() {
		SmartDashboard.putNumber("Distance from target in inches", vexUltrasonicSensor.getRangeInches());
	}
	
	public boolean collisionPrevention(){
		if (vexUltrasonicSensor.getRangeInches() <= 15) {
			return true;
		} else {
			return false;
		}
	}
	
}

