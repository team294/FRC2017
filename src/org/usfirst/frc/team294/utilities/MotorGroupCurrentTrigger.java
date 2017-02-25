package org.usfirst.frc.team294.utilities;

import com.ctre.CANTalon;

import java.util.List;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	int badMotor = -1;

	/*double the1stValue;
	double the2ndValue;
	double the3rdValue;*/

	/**
	 * test to see if a motor is dead
	 * @param inputMotors -  CANTalon list containing all the motors
	 * @param limit - Current limit
	 * @param duration - Time needed to be running properly
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


	public boolean get() {		for(int i = 0; i < motorList.size(); i++){
		for(int j = 0; j < motorList.size(); j++){
			if((motorList.get(j).getOutputCurrent() > 0.1) && (motorList.get(i).getOutputCurrent()/motorList.get(j).getOutputCurrent() < 0.67)){
				badMotor = i;
			}
			else{
				timer.reset();
			}
		}
	}
	//SmartDashboard.putNumber("Bad motor", motorList.get(badMotor).getDeviceID());
	return timer.get() >= duration;
	}
	public void printBadMotor(){
		SmartDashboard.putNumber("Bad motor" , badMotor);
	}
}
