package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Subsystem;

import com.ctre.CANTalon;
/**
 *
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
     * Set the angle of the shooter hood
     * @param angle from -180 to +180 (this may change; not all angles will be used because of space)
     */
    public void setShooterHoodAngle(double angle) {
    	
    }
    
    public void logShooterHoodStatus() {
    	
    }
    
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

