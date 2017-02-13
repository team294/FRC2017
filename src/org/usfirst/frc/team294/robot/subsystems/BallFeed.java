package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyers that move balls from the hopper to the shooter
 * 
 */
public class BallFeed extends Subsystem {

	public static CANTalon horConveyor = new CANTalon(RobotMap.horizontalConveyor);
	public static CANTalon vertConveyor = new CANTalon(RobotMap.verticalConveyor);

	/**
	 * Set the speed of the horizontal conveyer
	 * @param speed from -1 (out) to +1 (in)
	 */
	public void setHorSpeed(double voltage) {
		horConveyor.changeControlMode(TalonControlMode.Voltage);
		if(voltage > 12){
			voltage = 12;
		}
		if (voltage < -12){
			voltage = -12;
		}
		horConveyor.set(-voltage);
	}
	
	/**
	 * Set the speed of the vertical conveyer
	 * @param voltage from -1 (out) to +1 (in)
	 */
	public void setVertSpeed(double voltage) {
		vertConveyor.changeControlMode(TalonControlMode.Voltage);
		if(voltage > 12){
			voltage = 12;
		}
		if (voltage < -12){
			voltage = -12;
		}
		vertConveyor.set(-voltage);
	}
	
	/**
	 * Stops both conveyers
	 */
	public void stop() {
		horConveyor.set(0.0);
		vertConveyor.set(0.0);
	}
	
	/**
	 * Get the speed of the horizontal conveyer
	 * @return from -1 to +1
	 */
	public double getHorSpeed() {
		return horConveyor.get();
	}
	
	/**
	 * Get the speed of the vertical conveyer
	 * @return from -1 to +1
	 */
	public double getVertSpeed() {
		return vertConveyor.getSpeed();
	}
	
	/**
	 * Logs the speed of both conveyors to the robot log
	 */
	public void logStatus() {
		Robot.log.writeLog(
				"Ball Feed: Horizontal Conveyor-- Speed: " + horConveyor.get() +
				" Vertical Conveyor-- Speed: " + vertConveyor.get()
				);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

