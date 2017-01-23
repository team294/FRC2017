package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearPort extends Subsystem {

	private final DoubleSolenoid gearPiston = new DoubleSolenoid(RobotMap.gearPistonOut, RobotMap.gearPistonIn);

	/**
	 * Set the gear piston to out
	 */
	public void out() {
		gearPiston.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * Set the gear piston to in
	 */
	public void in() {
		gearPiston.set(DoubleSolenoid.Value.kReverse);
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

