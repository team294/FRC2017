package org.usfirst.frc.team294.robot.triggers;

import com.ctre.CANTalon;

import java.util.Arrays;
import java.util.List;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotorGroupCurrentTrigger extends Trigger {
	List<CANTalon> motorList; //List of motors that we will be checking the currents for
	double duration; //Time that motors must be running at bad current level
	double limit; //Current limit
	Timer timer = new Timer(); //Timer to measure duration
	List<Integer> badMotor; //List of bad motors. If a value is -1, that means that there's no bad motor

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

	//TODO:  Fix and test MotorGroupCurrentTrigger.  Don:  I think the logic in this function may not achieve the intended purpose, so please check. 
	public boolean get() {
		for(int i = 0; i < motorList.size(); i++){
			for(int j = 0; j < motorList.size(); j++){//These 2 for loops will go through every motor value and compare it to ever other motor value
				if((motorList.get(j).getOutputCurrent() > 0.1) && (motorList.get(i).getOutputCurrent()/motorList.get(j).getOutputCurrent() <= 0.67)){//If a motor is bad
					if(!badMotor.contains(i)) badMotor.add(i);//We add the value of the bad motor to our list of bad motors if it's not already there
				}
				else if((motorList.get(j).getOutputCurrent() > 0.1) && (motorList.get(i).getOutputCurrent()/motorList.get(j).getOutputCurrent() > 0.67) && (badMotor.get(i) == i)){
					//Else if the motor is no longer bad
					int replaceMotor = badMotor.indexOf(motorList.get(i));//Computes the index of the motor we will be replacing the value of
					badMotor.set(replaceMotor, -1);//Set that index to be -1
					//badMotor.remove(i); //I don't know if we want to remove the motors, I don't think it makes a difference
					timer.reset();//Resets the timer because our motor is fine
				}
				else if (badMotor.get(i) != -1){
					timer.reset();//If no motors are bad, reset the timer
				}
			}
		}
		//SmartDashboard.putNumber("Bad motor", motorList.get(badMotor).getDeviceID());
		return timer.get() >= duration;//Tells us whether we have a bad motor or not
	}

	public void printBadMotors(){
		for(int i =0; i<badMotor.size();i++){
			if(badMotor.get(i) != -1) SmartDashboard.putNumber("Bad motor" , badMotor.get(i));//Prints bad motor(s) to Smartdashboard
		}
	}
}
