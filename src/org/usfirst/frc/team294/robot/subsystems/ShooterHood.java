package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The Hood over the Shooter
 */
public class ShooterHood extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public static CANTalon shooterHoodMotor = new CANTalon(RobotMap.shooterHoodMotor);

	/**
	 * Set the angle of the shooter hood
	 */
	public void setAngle(double angle) {
		Robot.log.writeLog("Shooter Hood: Setting angle to " + angle);
		// Set the angle here
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

