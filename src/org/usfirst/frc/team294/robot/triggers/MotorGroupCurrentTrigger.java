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
	String name;  // Name of this trigger
	Timer timer = new Timer(); //Timer to measure duration
	List<Boolean> badMotor; //List of bad motors. If a value is true, that means that motor is bad
	
	/**
	 * Test to see if any motor in the group is dead
	 * @param inputMotors -  CANTalon list containing all the motors
	 * @param duration - Time needed to be running properly
	 * @param name - Name for this trigger or motor group, for debugging and dashboard
	 */
	public MotorGroupCurrentTrigger(List<CANTalon> inputMotors, double duration, String name) {
		motorList = inputMotors;
		this.duration = duration;
		this.name = name;
		for(int i=0; i<motorList.size(); i++){
			badMotor.add(false); //Assume that no  motors are bad, thus badMotor = false
		}
		timer.start();
	}

	//TODO: Test MotorGroupCurrentTrigger. 
	public boolean get() {
		for(int i = 0; i < motorList.size(); i++) {
			// Loop through every motor
			badMotor.set(i, false);	// reset flag, assume motor is good

			for(int j = 0; j < motorList.size(); j++) {
				// Compare current for motor i to to every other motor
				if (i==j) continue;	// Don't compare motor to itself

				if ((motorList.get(j).getOutputCurrent() > 0.5) && (motorList.get(i).getOutputCurrent()/motorList.get(j).getOutputCurrent() <= 0.67)) {//If a motor is bad
					badMotor.set(i, true);//Set the index corresponding to the bad motor to true
				}
			}
		}
		
		if (!badMotor.contains(true)) {
			// All motors are good, so reset timer
			timer.reset();
		}
		
		printBadMotors();
		
		return timer.get() >= duration;//Tells us whether we have a bad motor or not
	}

	public String getMotorErrorString(){
		String s = "";
		
		for(int i =0; i<badMotor.size(); i++){
			if(badMotor.get(i)) 
				s = s + " " + i;
		}

		return "Bad motors: " + name + " - " + s;  
	}
	
	public void printBadMotors(){
		String s = "";
		
		for(int i =0; i<badMotor.size(); i++){
			if(badMotor.get(i)) 
				s = s + " " + i;
		}

		SmartDashboard.putString("Bad motors: "+name , s);  //Prints list of bad motor(s) to Smartdashboard
	}
}
