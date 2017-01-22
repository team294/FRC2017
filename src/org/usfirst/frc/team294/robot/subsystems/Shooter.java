package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	
	public static CANTalon shooterMotor = new CANTalon(RobotMap.shooterMotor);

	/**
	 * Set the speed of the shooter
	 * @param speed
	 */
    public void setSpeed(double speed) {
    	shooterMotor.set(speed);
    }
    
    /**
     * Get the speed of the shooter
     * @return
     */
    public double getSpeed() {
    	return shooterMotor.getSpeed();
    }
    
    /**
     * Stop the shooter
     */
    public void stop() {
    	setSpeed(0.0);
    }
	
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

