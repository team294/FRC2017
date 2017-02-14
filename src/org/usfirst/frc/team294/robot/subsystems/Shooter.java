package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The shooter to shoot balls
 */
public class Shooter extends Subsystem {
	
	private final CANTalon shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
	private final CANTalon shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);

	public Shooter() {
		super();
		
		// Set the second motor to follow the first
		shooterMotor2.changeControlMode(TalonControlMode.Follower);
        shooterMotor2.set(shooterMotor1.getDeviceID());
	}
	
	/**
	 * Set the speed of the shooter
	 * @param speed
	 */
    public void setSpeed(double speed) {
    	shooterMotor1.set(speed);
    }
    
    /**
     * Get the speed of the shooter
     * @return
     */
    public double getSpeed() {
    	return shooterMotor1.getSpeed();
    }
    
    /**
     * Stop the shooter
     */
    public void stop() {
    	setSpeed(0.0);
    }
	
	/**
	 * Logs the speed of the shooter to the robot log
	 */
	public void logStatus() {
		Robot.log.writeLog(
				"Shooter: Shooter Motor 1 (Main)-- Speed: " + shooterMotor1.get() +
				" Shooter Motor 2 (Follower)-- Speed: " + shooterMotor2.get()
				);
	}
    
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

