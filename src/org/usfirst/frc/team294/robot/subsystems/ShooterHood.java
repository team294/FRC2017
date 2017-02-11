package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Hood over the Shooter
 */
public class ShooterHood extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static DoubleSolenoid shooterHoodSolenoid = new DoubleSolenoid(RobotMap.shooterHoodFwd, RobotMap.shooterHoodRev);

	/**
	 * Deploy the shooter hood into the up position
	 */
	public void deployShooterHood() {
		shooterHoodSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	/**
	 * Stow the shooter hood in the down position
	 */
	public void stowShooterHood() {
		shooterHoodSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

