package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Gear shifter for the drive train
 */
public class Shifter extends Subsystem {
	
//	private boolean gear; //Tracks the shifter setting in software. True is high gear, false is low.
	
	//This is a legitimate question- Why do we need to track the shifter in the code?
	//I understand why on the hopper and intake, because of interlock. Is there a reason to
	//Also do this on the shifter? Is there a situation in which we need to know this?

//    private final DoubleSolenoid shifter = new DoubleSolenoid(RobotMap.shifterSolenoidFwd, RobotMap.shifterSolenoidRev);
	private final Solenoid shifter = new Solenoid(RobotMap.shifterSolenoid);

    /**
     * Shift the gears down
     *//*
	public void shiftDown(){
		shifter.set(DoubleSolenoid.Value.kReverse);
		gear = false;
	}
	
	/**
	 * Shift the gears up
	 *//*
	public void shiftUp(){
		shifter.set(DoubleSolenoid.Value.kForward);
		gear = true;
	}

	/**
	 * Returns the state of the shifter
	 * @return true for high gear, false for low
	 *//*
	public boolean get() {
		return gear;
	}
*/
	
	/**
	 * Shift the gears up
	 */
	public void shiftUp() {
		shifter.set(true);
	}
	
	/**
	 * Set the gear piston to in
	 */
	public void shiftDown() {
		shifter.set(false);		
	}

	/**
	 * Returns the state of the shifter
	 * @return true for high gear, false for low
	 */
	public boolean isShifterInHighGear() {
		return shifter.get();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

