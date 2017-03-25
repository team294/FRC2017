package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The gate and solenoid to capture and place gears
 */
public class GearGate extends Subsystem {

//	private final Solenoid gearPiston = new Solenoid(RobotMap.gearSolenoid);
	private final Solenoid gearTiltPiston = new Solenoid(RobotMap.gearTiltSolenoid);
	private final Solenoid gearShieldPiston = new Solenoid(RobotMap.gearShieldSolenoid);
	private final Solenoid gearPuncherPiston = new Solenoid(RobotMap.gearPuncherSolenoid);

	/**
	 * Set the gear piston to out
	 *
	public void out() {
		gearPiston.set(true);
	}
	
	/**
	 * Set the gear piston to in
	 *
	public void in() {
		gearPiston.set(false);		
	}
	
	/**
	 * Get the position of the gear solenoid
	 * @return true for out, false for in
	 *
	public boolean getPosition() {
		return gearPiston.get();
	}*/
///////////////////////////////////
	/**
	 * Set the gear gate to tilt out
	 */
	public void gearTiltOut() {
		Robot.log.writeLog("GearGate - tilting out");
		gearTiltPiston.set(true);
	}
	
	/**
	 * Set the gear gate to tilt in
	 */
	public void gearTiltIn() {
		Robot.log.writeLog("GearGate - tilting in");
		gearTiltPiston.set(false);		
	}
	
	/**
	 * Get the position of the gear gate solenoid
	 * @return true for out, false for in
	 */
	public boolean isGearTiltOut() {
		return gearTiltPiston.get();
	}
/////////////////////////////	
	/**
	 * Set the gear shield piston to open
	 */
	public void gearShieldOpen() {
		Robot.log.writeLog("GearGate - opening shield");
		gearShieldPiston.set(true);
	}
	
	/**
	 * Set the gear shield piston to close
	 */
	public void gearShieldClose() {
		Robot.log.writeLog("GearGate - closing shield");
		gearShieldPiston.set(false);		
	}
	
	/**
	 * Get the position of the shield gear solenoid
	 * @return true for open, false for close
	 */
	public boolean isGearShieldOpen() {
		return gearShieldPiston.get();
	}
/////////////////////////////////
	/**
	 * Set the gear puncher piston to deploy
	 */
	public void gearPuncherDeploy() {
		Robot.log.writeLog("GearGate - punching!!!");
		gearPuncherPiston.set(true);
	}
	
	/**
	 * Set the gear puncher piston to retract
	 */
	public void gearPuncherRetract() {
		Robot.log.writeLog("GearGate - punch retracting");
		gearPuncherPiston.set(false);		
	}
	
	/**
	 * Get the position of the gear puncher solenoid
	 * @return true for deploy, false for retract
	 */
	public boolean isGearPuncherOut() {
		return gearPuncherPiston.get();
	}
/////////////////////////////////

	public void updateSmartDashboard() {
		SmartDashboard.putBoolean("Gear Tilt Out", isGearTiltOut());
		SmartDashboard.putBoolean("Gear Shield Open", isGearShieldOpen());
		SmartDashboard.putBoolean("Gear Punch Out", isGearPuncherOut());
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

