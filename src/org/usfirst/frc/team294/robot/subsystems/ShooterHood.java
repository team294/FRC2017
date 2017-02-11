package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Hood over the Shooter
 */
public class ShooterHood extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	

	public static DoubleSolenoid shooterHoodSolenoid = new DoubleSolenoid(RobotMap.shooterHoodFwd, RobotMap.shooterHoodRev);

	// add potentiometer here
	
	// will this be a motor or a servo?
	private final CANTalon shooterHoodMotor = new CANTalon (RobotMap.shooterHoodMotor);
 
	/**
	 * Get the current angle of the shooter hood
	 * @return double from -180 to +180 (this may change; not all angles will be used because of space)
	 */
    public double getShooterHoodAngle() {
    	double angle = 0.0;
    	//calculate angle from potentiometer
    	return angle;
    }
   
    /**
     * Log the current status of the shooter hood
     */
    public void logShooterHoodStatus() {
    	Robot.log.writeLog("Shooter Hood: Current Angle: " + getShooterHoodAngle());
    }
    
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
	
   
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
  	public void initDefaultCommand() {
    }
}

