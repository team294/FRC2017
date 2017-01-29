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
	//add potentiometer here
	private final CANTalon shooterHoodMotor = new CANTalon (RobotMap.shooterHoodMotor);
    private double currentAngle;
    public ShooterHood(){
    	
    }
    public double getShooterHoodAngle() {
    	return currentAngle;
    	//calculate angle from potentiometer
    }
    public void setShooterHoodSpeed(double speed) {
    	
    }
    public void logShooterHoodStatus() {
    	
    }
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

