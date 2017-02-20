package org.usfirst.frc.team294.utilities;

import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * 
 * @author John Haggerty
 *
 */
public class MotorGroupCurrentTrigger extends Trigger {
	//CANTalon the1stMotor;
	//CANTalon the2nsMotor;
	//CANTalon the3rdMotor;
	double the1stValue;
	double the2ndValue;
	double the3rdValue;
	
	/**
	 * test to see if a motor is dead
	 * @param motor1
	 * @param value1
	 * @param motor2
	 * @param value2
	 * @param motor3
	 * @param value3
	 * I may only need the amp values, and not the motors -John
	 */
	public MotorGroupCurrentTrigger(double value1, double value2, double value3) {
	//public MotorGroupCurrentTrigger(CANTalon motor1, double value1, CANTalon motor2, double value2, CANTalon motor3, double value3) {
//		this.the1stMotor = motor1;
		this.the1stValue = value1;
//		this.the2nsMotor = motor2;
		this.the2ndValue = value2;
//		this.the3rdMotor = motor3;
		this.the3rdValue = value3;
	}

    public boolean get() {
    	if (the1stValue < Math.abs(the2ndValue + the3rdValue) || the2ndValue < Math.abs(the1stValue + the3rdValue) || the3rdValue < Math.abs(the1stValue + the2ndValue)) {
    		return true;
    	}
    	else {
            return false;
    	}
    }
}
