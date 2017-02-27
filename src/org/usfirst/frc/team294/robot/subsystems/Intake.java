package org.usfirst.frc.team294.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.commands.ClimbSetToSpeed;
import org.usfirst.frc.team294.robot.commands.IntakeSetToSpeed;
import org.usfirst.frc.team294.robot.triggers.MotorCurrentTrigger;
import org.usfirst.frc.team294.robot.triggers.MotorGroupCurrentTrigger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Ball intake from ground
 */
public class Intake extends Subsystem {

	// Motors
    private final CANTalon intakeMotor = new CANTalon(RobotMap.intakeMotor);
    private final CANTalon climbMotor1 = new CANTalon(RobotMap.climbMotor1);
    private final CANTalon climbMotor2 = new CANTalon(RobotMap.climbMotor2);
    
    // Pneumatics
    private final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(RobotMap.intakeSolenoidFwd, RobotMap.intakeSolenoidRev);
    private final DoubleSolenoid hopperSolenoid = new DoubleSolenoid(RobotMap.hopperSolenoidFwd, RobotMap.hopperSolenoidRev);
    
	//Current Protection
	public final MotorCurrentTrigger intakeCurrentTrigger = new MotorCurrentTrigger(intakeMotor, 9, 3);
	List<CANTalon> climbMotors = new ArrayList<CANTalon>(Arrays.asList(climbMotor1, climbMotor2));
	public final MotorGroupCurrentTrigger climbCurrentTrigger = new MotorGroupCurrentTrigger(climbMotors, 35, 2);

    // Control variables for mechanical interlock
    public static enum Status {
    	deployed,stowed,unknown
    }
    
    private Status intakePos = Status.unknown;
    private Status hopperPos = Status.unknown;
    
    // Time to move hopper/intake in seconds (refine by testing)
    public final double HOPPER_DELAY = 1.5;
    public final double INTAKE_DELAY = 2.0;
    
    public Intake() {
    	
    	// Call the Subsystem constructor
    	super();
    	
    	// Set up subsystem components
    	intakeMotor.setVoltageRampRate(50);

		// Set up subsystem components
		intakeMotor.setVoltageRampRate(50);
		climbMotor2.changeControlMode(TalonControlMode.Follower);
		climbMotor2.set(climbMotor1.getDeviceID());

		// Stall protection
		//        intakeCurrentTrigger.whenActive(new IntakeSetToSpeed(0));

		// Add the subsystem to the LiveWindow
		LiveWindow.addActuator("Intake", "Intake Motor", intakeMotor);
		LiveWindow.addActuator("Intake", "Intake Solenoid", intakeSolenoid);
	}

	/**
	 * Adds current protection to intake and climber motors
	 */
	public void intakeCurrentProtection(){
		intakeCurrentTrigger.whenActive(new IntakeSetToSpeed(0.0));
		climbCurrentTrigger.whenActive(new ClimbSetToSpeed(0.0));
	}

	/**
	 * Set the speed of the intake motor
	 * @param speed of the motor, between -1 (outtake) and +1 (intake), 0 = stopped
	 */
	public void setSpeed(double speed) {
		intakeMotor.set(speed);
	}

	/**
	 * Get the speed of the intake motor
	 * @return speed between -1 (outtake) and +1 (intake). 0 = stopped
	 */
	public double getSpeed() {
		return -intakeMotor.get();
	}
	
    /**
     * Set the speed of the climbing mechanism
     * @param speed from 0 to +1. <b>Climber does not go down!</b>
     */
    public void setClimbSpeed(double speed) {
    	climbMotor1.changeControlMode(TalonControlMode.PercentVbus);
    	// Need to check if hopper and intake are stowed first
    	if (speed < 0) speed = 0;
    	if (speed > 1.0) speed = 1.0;
    	climbMotor1.set(speed);
    }

	/**
	 * Set up the intake controls on the SmartDashboard.  Call this once when the robot is 
	 * initialized (after the Intake subsystem is initialized).
	 */
	public void setupSmartDashboard(boolean bPIDF){
		updateSmartDashboard();
	}

	/**
	 * Send intake status to SmartDashboard
	 */
    public void updateSmartDashboard() {
 		SmartDashboard.putNumber("Intake motor setpoint", -intakeMotor.get());
 		SmartDashboard.putNumber("Intake motor current", intakeMotor.getOutputCurrent());
// 		SmartDashboard.putString("Intake position", intakeIsUp() ? "Up" : "Down");
    }
    
    /**
     * Updates the conflicts between the hopper and the intake
     * Call before enabling the robot to avoid mechanical interlock
     */
    public void updateConflicts() {
    	if (intakeSolenoid.get() == DoubleSolenoid.Value.kForward) intakePos = Status.deployed;
    	else if (intakeSolenoid.get() == DoubleSolenoid.Value.kReverse) intakePos = Status.stowed;
    	else intakePos = Status.unknown;
    	if (hopperSolenoid.get() == DoubleSolenoid.Value.kForward) hopperPos = Status.deployed;
    	else if (hopperSolenoid.get() == DoubleSolenoid.Value.kReverse) hopperPos = Status.stowed;
    	else hopperPos = Status.unknown;
    }
    
    /**
     * Set the value of the hopper tracker in the code
     * @param position Intake.Positions.stowed,deployed,unknown
     */
    public void setHopperTracker(Status position) {
    	hopperPos = position;
    }
    
    /**
     * Set the value of the intake tracker in the code
     * @param position Intake.Positions.stowed,deployed,unknown
     */
    public void setIntakeTracker(Status position) {
    	intakePos = position;
    }
    
    /**
     * Read the value of the software hopper tracker
     * @return Intake.Positions.deployed,stowed,unknown
     */
    public Status getHopperTracker() {
    	return hopperPos;
    }
    
    /**
     * Read the value of the software intake tracker
     * @return Intake.Positions.deployed,stowed,unknown
     */
    public Status getIntakeTracker() {
    	return intakePos;
    }

	/**
	 * Deploy the intake
	 */
	public void deployIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Stows the intake
	 */
	public void stowIntake() {
		intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Deploy the hopper
	 */
	public void deployHopper() {
		hopperSolenoid.set(DoubleSolenoid.Value.kForward);
	}

	/**
	 * Stow the hopper
	 */
	public void stowHopper() {
		hopperSolenoid.set(DoubleSolenoid.Value.kReverse);
	}

	/**
	 * Stops the intake motor
	 */
	public void stopIntake() {
		intakeMotor.set(0.0);
	}

	/**
	 * Stops the climber
	 * <p> <b>Does not change control mode</b>
	 */
	public void stopClimber() {
		climbMotor1.set(0.0);
	}

	/**
	 * Logs the speed of the intake to the robot log
	 */
	public void logIntakeStatus() {
		Robot.log.writeLog(
				"Intake: Intake Motor-- Speed: " + intakeMotor.get()
				);
	}

	/**
	 * Logs the speed of both conveyors to the robot log
	 */
	public void logClimbStatus() {
		Robot.log.writeLog(
				"Climber: Climb Motor 1 (Main)-- Speed: " + climbMotor1.get() +
				" Climb Motor 2 (Follower)-- Speed: " + climbMotor2.get()
				);
	}

	/**
	 * Gets the average current of both climber motors
	 * @return the average current in amps
	 */
	public double getAverageClimberCurrent(){
		double aveCurrent;
		aveCurrent = (this.climbMotor1.getOutputCurrent() + this.climbMotor2.getOutputCurrent())/2;
		return aveCurrent;
	}
	
	/**
	 * Updates SmartDashboard with climber current
	 */
    public void updateSmartDashboardClimbMotorCurrent() {
    	SmartDashboard.putNumber("Climber Motor Current", getAverageClimberCurrent());
    }
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		//setDefaultCommand(new MySpecialCommand());
	}
}

