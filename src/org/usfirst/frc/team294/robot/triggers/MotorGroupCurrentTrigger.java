package org.usfirst.frc.team294.robot.triggers;

import com.ctre.CANTalon;

import java.util.Arrays;
import java.util.List;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class MotorGroupCurrentTrigger extends Trigger {
	CANTalon[] motorList; //List of motors that we will be checking the currents for
	double duration; //Time that motors must be running at bad current level
	String name;  //Name of motor group for this trigger
	Timer timer = new Timer(); //Timer to measure duration
	Boolean[] badMotor; //List of bad motors. If a value is true, that means that motor is bad

	/**
	 * Test to see if any motor in the group is dead
	 * @param inputMotors -  CANTalon list containing all the motors
	 * @param duration - Time needed to be running properly
	 * @param name - Name for this trigger or motor group, for debugging and dashboard
	 */
	public MotorGroupCurrentTrigger(CANTalon[] inputMotors, double duration, String name) {
		this.motorList = inputMotors;
		this.duration = duration;
		this.name = name;
		badMotor = new Boolean[motorList.length];

		for(int i=0; i<motorList.length; i++){
			badMotor[i] = false; //Assume that no  motors are bad, thus badMotor = false
		}
		timer.start();
	}

	//TODO: Test MotorGroupCurrentTrigger. 
	public boolean get() {
		for(int i = 0; i < motorList.length; i++) {
			//Loop through every motor
			badMotor[i] = false;//Reset flag, assume motor is good
			for(int j = 0; j < motorList.length; j++) {
				// Compare current for motor i to to every other motor
				if (i==j) continue;//Don't compare motor to itself, moves on to next iteraton of j loop
				else if ((motorList[j].getOutputCurrent() > 0.5) && (motorList[i].getOutputCurrent()/motorList[j].getOutputCurrent() <= 0.67)) {//If a motor is bad
					badMotor[i] = true;//Set the index corresponding to the bad motor to true
				}
			} //LINES 44-46 CAN CAUSE A TIMEOUT ERROR, DON't KNOW WHY
		}

		for(int k = 0; k < badMotor.length; k++){
			if (badMotor[k] == true) break;//If we have a bad motor, the timer keeps running
			else if((k == badMotor.length) && (badMotor[k] == false)) timer.reset();//If we have no bad motors, reset the timer
		}
		/*if (!badMotor.contains(true)) {
			// All motors are good, so reset timer
			timer.reset(); 
		}*/

		printBadMotors();

		return timer.get() >= duration;//Tells us whether we have a bad motor or not
	}

	public String getMotorErrorString(){
		String s = "";
		for(int i = 0; i < badMotor.length; i++){
			if(badMotor[i]) {
				s = s + " " + motorList[i];
			}
		}
		return s;  
	}

	public void printBadMotors(){
		String s = "";

		for(int i = 0; i < badMotor.length; i++){
			if(badMotor[i]) 
				s = s + (" " + motorList[i]);
		}

		SmartDashboard.putString(("Bad" + name + "motors: "), s);  //Prints list of bad motor(s) to Smartdashboard
	}
}
