package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The conveyers that move balls from the hopper to the shooter
 */
public class BallFeed extends Subsystem {

	private final CANTalon horConveyer = new CANTalon(RobotMap.horizontalConveyer);
	private final CANTalon vertConveyer = new CANTalon(RobotMap.verticalConveyer);

	/**
	 * Set the speed of the horizontal conveyer
	 * @param speed from -1 (out) to +1 (in)
	 */
	public void setHorSpeed(double speed) {
		speed = (speed > 1) ? 1 : speed;
		speed = (speed < -1) ? -1 : speed;
		horConveyer.set(-speed);
	}
	
	/**
	 * Set the speed of the vertical conveyer
	 * @param speed from -1 (out) to +1 (in)
	 */
	public void setVertSpeed(double speed) {
		speed = (speed > 1) ? 1 : speed;
		speed = (speed < -1) ? -1 : speed;
		vertConveyer.set(-speed);
	}
	
	/**
	 * Stops both conveyers
	 */
	public void stop() {
		horConveyer.set(0.0);
		vertConveyer.set(0.0);
	}
	
	/**
	 * Get the speed of the horizontal conveyer
	 * @return from -1 to +1
	 */
	public double getHorSpeed() {
		return horConveyer.get();
	}
	
	/**
	 * Get the speed of the vertical conveyer
	 * @return from -1 to +1
	 */
	public double getVertSpeed() {
		return vertConveyer.get();
	}
	
	/**
	 * Logs the speed of both conveyors to the robot log
	 */
	public void logStatus() {
		Robot.log.writeLog(
				"Ball Feed: Horizontal Conveyor-- Speed: " + horConveyer.get() +
				" Vertical Conveyor-- Speed: " + vertConveyer.get()
				);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

