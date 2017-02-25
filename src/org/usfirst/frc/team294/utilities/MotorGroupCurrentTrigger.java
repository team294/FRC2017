package org.usfirst.frc.team294.utilities;

import com.ctre.CANTalon;

import java.util.List;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * 
 */
public class MotorGroupCurrentTrigger extends Trigger {
	List<CANTalon> motorList; 
	double duration;
	double limit;
	Timer timer = new Timer();
	int badMotor = -1;

	/**
	 * test to see if a motor is dead
	 * @param inputMotors -  CANTalon list containing all the motors
	 * @param limit - Current limit
	 * @param duration - Time needed to be running properly
	 */
	public MotorGroupCurrentTrigger(List<CANTalon> inputMotors, double limit, double duration) {
		motorList = inputMotors;
		this.duration = duration;
		this.limit = limit;
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
