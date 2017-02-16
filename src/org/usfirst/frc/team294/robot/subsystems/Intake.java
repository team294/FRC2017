package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
//import org.usfirst.frc.team294.robot.triggers.MotorCurrentTrigger;

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
   
    //public final MotorCurrentTrigger motorCurrentTrigger = new MotorCurrentTrigger(intakeMotor, 35, 2);

    public Intake() {
    	
    	// Call the Subsystem constructor
    	super();
    	
    	// Set up subsystem components
    	intakeMotor.setVoltageRampRate(50);
		climbMotor2.changeControlMode(TalonControlMode.Follower);
        climbMotor2.set(climbMotor1.getDeviceID());

    	// Stall protection
        //motorCurrentTrigger.whenActive(new IntakeMotorStop());

    	// Add the subsystem to the LiveWindow
        LiveWindow.addActuator("Intake", "Intake Motor", intakeMotor);
        LiveWindow.addActuator("Intake", "Intake Solenoid", intakeSolenoid);
    }

    /**
     * Get the current position of the intake
     * @return true if the intake is deployed, false if not
     */
    public boolean getPosition(){
    	return (intakeSolenoid.get() == DoubleSolenoid.Value.kForward);
    }
    
    /**
     * Set the speed of the intake motor
     * @param speed of the motor, between -1 (outtake) and +1 (intake), 0 = stopped
     */
    public void setSpeed(double speed) {
    	intakeMotor.set(-speed);
    }
    
    /**
     * Get the speed of the intake motor
     * @return speed between -1 (outtake) and +1 (intake). 0 = stopped
     */
    public double getSpeed() {
    	return -intakeMotor.get();
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
     * Deploy the hopper out
     */
    public void deployHopper() {
    	hopperSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    
    /**
     * Stow the hopper (for climbing)
     */
    public void stowHopper() {
    	hopperSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    /**
     * Set the speed of the climbing mechanism
     * @param speed from -1 (down) to +1 (climb)
     */
	public void setClimbSpeed(double speed){
		//Need to check in hopper and intake are stowed first
        climbMotor1.changeControlMode(TalonControlMode.Speed);    
        climbMotor2.changeControlMode(TalonControlMode.Speed);  
		if(Math.abs(speed) > 1.0) speed = 1.0 * Math.signum(speed);
		climbMotor1.set(speed);
		climbMotor2.set(speed);
	}
	
	/*
	 * Returns the average current of the climb motors
	 */
	public double getClimberCurrent() {
		double motor1Current = climbMotor1.getOutputCurrent();
		double motor2Current = climbMotor2.getOutputCurrent();
		return (motor1Current + motor2Current)/2;
	}
	
	/*
	 * Stops the climber (as the name implies)
	 */
	public void stopClimber(){
		climbMotor1.set(0);
		climbMotor2.set(0);
	}
	
	public void driveClimberWithJoystick(){
        climbMotor1.changeControlMode(TalonControlMode.PercentVbus);    
        climbMotor2.changeControlMode(TalonControlMode.PercentVbus);  
        climbMotor1.configPeakOutputVoltage(+12.0f, -12.0f);
        climbMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
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
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

