package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The gate and solenoid to capture and place gears
 */
public class GearGate extends Subsystem {

	private final Solenoid gearPistonIn = new Solenoid(RobotMap.gearSolenoidIn);
//	private final Solenoid gearPistonOut = new Solenoid(RobotMap.gearSolenoidOut);

	/**
	 * Set the gear piston to out
	 */
	public void out() {
//		gearPistonOut.set(true);
		gearPistonIn.set(false);
	}
	
	/**
	 * Set the gear piston to in
	 */
	public void in() {
		gearPistonIn.set(true);
//		gearPistonOut.set(false);
		
	}
	
	/**
	 * Get the position of the gear solenoid
	 * @return true for out, false for in
	 */
	public boolean getPosition() {
		return gearPistonIn.get();
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

