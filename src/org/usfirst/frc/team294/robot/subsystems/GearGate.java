package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The gate and solenoid to capture and place gears
 */
public class GearGate extends Subsystem {

	private final Solenoid gearPiston = new Solenoid(RobotMap.gearSolenoid);
	
	public GearGate() {
		lightSensor = new DigitalInput (7);

	}

	/**
	 * Set the gear piston to out
	 */
	public void out() {
		gearPiston.set(true);
	}
	
	/**
	 * Set the gear piston to in
	 */
	public void in() {
		gearPiston.set(false);
	}
	
	DigitalInput lightSensor;
	


	public boolean isGearThere() {
			return lightSensor.get();
		
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

