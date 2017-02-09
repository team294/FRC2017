package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.Robot;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;


/**
 * The Hood over the Shooter
 */
public class ShooterHood extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	

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
	 * Set the angle of the shooter hood
	 */
	public void setAngle(double angle) {
		Robot.log.writeLog("Shooter Hood: Setting angle to " + angle);
		// Set the angle here
	}
	
   
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
  	public void initDefaultCommand() {
    }
}

