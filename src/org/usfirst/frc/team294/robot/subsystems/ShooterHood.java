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
<<<<<<< HEAD
	public void deploy() {
		shooterHoodSolenoid.set(true);
	}
	
=======
    public double getShooterHoodAngle() {
    	double angle = 0.0;
    	//calculate angle from potentiometer
    	return angle;
    }
    
    public double setShooterHoodAngle(double angle){
    	//placeholder command to fix errors by you lazy shlops >:(
    	return 0;
    }
   
    /**
     * Log the current status of the shooter hood
     */
    public void logShooterHoodStatus() {
    	Robot.log.writeLog("Shooter Hood: Current Angle: " + getShooterHoodAngle());
    }
    



>>>>>>> refs/remotes/origin/VexUltrasonic
	/**
	 * Stow the shooter hood in the down position
	 */
<<<<<<< HEAD
	public void stow() {
		shooterHoodSolenoid.set(false);
=======
	public void logAngle(double angle) {
		Robot.log.writeLog("Shooter Hood: Setting angle to " + angle);
		// Set the angle here
>>>>>>> refs/remotes/origin/VexUltrasonic
	}
	
  	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
  		//setDefaultCommand(new MySpecialCommand());
  	}
}

