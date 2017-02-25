package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.commands.ConveyorSetFromRobot;
import org.usfirst.frc.team294.robot.commands.ConveyorSetFromRobot.States;
import org.usfirst.frc.team294.utilities.MotorCurrentTrigger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyers that move balls from the hopper to the shooter   */

public class BallFeed extends Subsystem {

	// Hardware
	public static CANTalon horConveyor = new CANTalon(RobotMap.horizontalConveyor);
	public static CANTalon vertConveyor = new CANTalon(RobotMap.verticalConveyor);
	public final MotorCurrentTrigger vertCurrentTrigger =  new MotorCurrentTrigger(vertConveyor, 5, 2);
	public final MotorCurrentTrigger horCurrentTrigger =  new MotorCurrentTrigger(horConveyor, 6, 2);
	
	public BallFeed() {
		super();
		
		horConveyor.changeControlMode(TalonControlMode.Voltage);
		vertConveyor.changeControlMode(TalonControlMode.Voltage);
		horConveyor.enableBrakeMode(false);
		vertConveyor.enableBrakeMode(false);
	}
	
	/**
	 * Adds current protection to the conveyor. If either conveyor trips this, both sections will stop
	 */
	public void ballFeedCurrentProtection(){
		vertCurrentTrigger.whenActive(new ConveyorSetFromRobot(States.stopped));
		horCurrentTrigger.whenActive(new ConveyorSetFromRobot(States.stopped));
	}
	
	/**
	 * Set the speed of the horizontal conveyor according to voltage *
	 * @param voltage from -12.0 (out) to +12.0 (in)
	 */
	
	public void setHorSpeed(double voltage) {  
//		voltage *= hFactor;
		if(voltage > 12.0) voltage = 12.0;
		if (voltage < -12.0) voltage = -12.0;
		horConveyor.set(voltage);
	}
	
	/**
	 * Set the speed of the vertical conveyor according to voltage
	 * @param voltage from -12.0 (out) to +12.0 (in)
	 */
	public void setVertSpeed(double voltage) {
		if(voltage > 12.0) voltage = 12.0;
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
	
	/**          The next two sections aren't called anywhere.  The speed is logged in logStatus, but reads Talon directly
	 * 					It doesn't read actual speed because there isn't an encoder on these motors.  RPC
	 * 
	 * Get the speed of the horizontal conveyor
	 * @return from -1 to +1 (This may depend on the control mode)
	 
	public double getHorSpeed() {
		return horConveyor.get();
	}
	*/
	
	/**
	 * Get the speed of the vertical conveyor
	 * @return from -1 to +1 (This may depend on the control mode)
	 
	public double getVertSpeed() {
		return vertConveyor.getSpeed();
	}
	*/
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

