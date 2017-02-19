package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Gear shifter for the drive train
 */
public class Shifter extends Subsystem {
	
	private boolean isLowGear = true;
	//private boolean isHighGear = false;

    private final DoubleSolenoid shifter = new DoubleSolenoid(RobotMap.shifterSolenoidFwd, RobotMap.shifterSolenoidRev);

    /**
     * Shift the gears down
     */
	public void shiftDown(){
		shifter.set(DoubleSolenoid.Value.kForward);
		isLowGear = true;
		//isHighGear = false;
	}
	
	/**
	 * Shift the gears up
	 */
	public void shiftUp(){
		shifter.set(DoubleSolenoid.Value.kReverse);
		isLowGear = false;
		//isHighGear = true;
	}
	/**
	 * gets the gear mode
	 * @return true = low false = high
	 */
	public boolean isLowMode(boolean lowGear){
		this.isLowGear = lowGear;
		return lowGear;
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

