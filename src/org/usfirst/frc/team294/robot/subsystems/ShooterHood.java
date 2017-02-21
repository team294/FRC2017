package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Solenoid;

/**
 * The Hood over the Shooter
 */
public class ShooterHood extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	public static Solenoid shooterHoodSolenoid = new Solenoid(RobotMap.shooterHoodSolenoid);
    
	/**
	 * Deploy the shooter hood into the up position
	 */
	public void deploy() {
		shooterHoodSolenoid.set(true);
	}
	
	/**
	 * Stow the shooter hood in the down position
	 */
	public void stow() {
		shooterHoodSolenoid.set(false);
	}
	
  	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
  		//setDefaultCommand(new MySpecialCommand());
  	}
}

