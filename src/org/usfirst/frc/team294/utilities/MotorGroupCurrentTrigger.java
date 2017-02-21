package org.usfirst.frc.team294.utilities;

import com.ctre.CANTalon;

import java.util.ArrayList;
import java.util.List;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;

/**
 * 
 * @author John Haggerty
 *
 */
public class MotorGroupCurrentTrigger extends Trigger {
	List<CANTalon> motorList; 
	double duration;
	double limit;
	Timer timer = new Timer();
	/*double the1stValue;
	double the2ndValue;
	double the3rdValue;*/

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
	public MotorGroupCurrentTrigger(List<CANTalon> inputMotors, double limit, double duration) {
		//public MotorGroupCurrentTrigger(CANTalon motor1, double value1, CANTalon motor2, double value2, CANTalon motor3, double value3) {
		motorList = inputMotors;
		this.duration = duration;
		this.limit = limit;
		//this.Motor1 = motor1;
		//		this.the1stValue = value1;
		//this.Motor2 = motor2;
		//		this.the2ndValue = value2;
		//this.Motor3 = motor3;
		//		this.the3rdValue = value3;
	}


	public boolean get() {
		List<Double> currents =  new ArrayList<Double>();
		for(int i =0; i < motorList.size(); i++){
			for(int j = i+1; j < motorList.size(); j++){
			/*if (the1stValue < Math.abs(the2ndValue + the3rdValue) || the2ndValue < Math.abs(the1stValue + the3rdValue) || the3rdValue < Math.abs(the1stValue + the2ndValue)) {
			return true;
		}
		else {
			return false;
		}*/
		//if(motorList.get(i).getOutputCurrent()<)
			}
		}
		return timer.get() >= duration;
	}
}
