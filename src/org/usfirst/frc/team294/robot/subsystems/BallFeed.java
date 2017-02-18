package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyers that move balls from the hopper to the shooter
 * 
 * They will both run at the same time, but maybe not at the same voltage
 * 
 */
public class BallFeed extends Subsystem {

	// Hardware
	public static CANTalon horConveyor = new CANTalon(RobotMap.horizontalConveyor);
	public static CANTalon vertConveyor = new CANTalon(RobotMap.verticalConveyor);
	private static double hFactor = 0.6;   //  hFactor allows horizontal to run at ratio to vertical voltage
	
	public BallFeed() {
		super();
		
		horConveyor.changeControlMode(TalonControlMode.Voltage);
		vertConveyor.changeControlMode(TalonControlMode.Voltage);
		horConveyor.enableBrakeMode(false);
		vertConveyor.enableBrakeMode(false);
	}
	
	/**
	 * Set the speed of the horizontal conveyor according to voltage *
	 * @param voltage from -12.0 (out) to +12.0 (in)
	 */
	
	/**  John - talk to Rob on these changes to the next two sections.  This is overly complicated. ???
	**/
	public void setHorSpeed(double voltage) {  
		voltage *= hFactor;
		if(voltage > 12.0) voltage = 12.0;
		else if (voltage < -12.0) voltage = -12.0;
		horConveyor.set(-voltage);
		if (voltage < -12.0) voltage = -12.0;
		horConveyor.set(voltage);
	}
	
	/**
	 * Set the speed of the vertical conveyor according to voltage
	 * @param voltage from -12.0 (out) to +12.0 (in)
	 */
	public void setVertSpeed(double voltage) {
		if(voltage > 12.0) voltage = 12.0;
		else if (voltage < -12.0) voltage = -12.0;
		vertConveyor.set(-voltage);
		if (voltage < -12.0) voltage = -12.0;
		vertConveyor.set(voltage);
	}
	
	/**
	 * Stops both conveyors
	 */
	public void stop() {
		horConveyor.set(0.0);
		vertConveyor.set(0.0);
	}
	
	/**
	 * Get the speed of the horizontal conveyor
	 * @return from -1 to +1 (This may depend on the control mode)
	 */
	public double getHorSpeed() {
		return horConveyor.get();
	}
	
	/**
	 * Get the speed of the vertical conveyor
	 * @return from -1 to +1 (This may depend on the control mode)
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

