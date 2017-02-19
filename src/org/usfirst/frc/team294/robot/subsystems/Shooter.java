package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.ctre.CANTalon.VelocityMeasurementPeriod;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

/**
 * The shooter
 */
public class Shooter extends Subsystem {
	
	// Motor Hardware
	private final CANTalon shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
//	private final CANTalon shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);
	
	double setSpeed;
	boolean error = false;
	private double fNominal;
	public static Preferences robotPrefs;

	

	public Shooter() {
		super();
		robotPrefs = Preferences.getInstance();
		
		shooterMotor1.setVoltageRampRate(24.0);
//		shooterMotor2.setVoltageRampRate(24.0);
		
		
		shooterMotor1.reverseSensor(true);    // true for prototype false for practice!!!
		shooterMotor1.reverseOutput(false);
		
		shooterMotor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterMotor1.configEncoderCodesPerRev(100);
//		shooterMotor2.changeControlMode(TalonControlMode.Follower);
//		shooterMotor2.set(shooterMotor1.getDeviceID());
		shooterMotor1.enableBrakeMode(false);
//		shooterMotor2.enableBrakeMode(false);
		shooterMotor1.set(0.0);
		setupSmartDashboard();
		shooterMotor1.setPID(Robot.shooterP, Robot.shooterI, Robot.shooterD, Robot.shooterFNominal, 500, 500, 0); 
		periodicSetF();
		shooterMotor1.SetVelocityMeasurementWindow(1);		//These need to be tuned
		shooterMotor1.SetVelocityMeasurementPeriod(VelocityMeasurementPeriod.Period_1Ms);
		
	}
	
	/**
	 * Sets the shooter motor to speed according to rpm
	 * @param rpm from -1000 to 6000  (18000 if encoder is on motor
	 * Only run reverse to clear a possible jam
	 */
	public void setRPM(double rpm) {
		shooterMotor1.changeControlMode(TalonControlMode.Speed);
		
		rpm = (rpm > 18000.0) ? 6000.0 : rpm;
		rpm = (rpm < -1000.0) ? -1000.0 : rpm;
		
		setSpeed = rpm;
		shooterMotor1.set(rpm);
	}
	
	public void periodicSetF(){
		double currentBatteryVoltage = shooterMotor1.getBusVoltage();
		double f = ((12.1/currentBatteryVoltage)*Robot.shooterFNominal);
		shooterMotor1.setF(f);
	}
	
	
	/**
	 * Set the shooter speed according to voltage
	 * @param voltage from -12.0 to +12.0
	 */
	public void setVoltage(double voltage){
		shooterMotor1.changeControlMode(TalonControlMode.Voltage);
		voltage = (voltage > 12.0) ? 12.0 : voltage;
		voltage = (voltage < -12.0) ? -12.0 : voltage;
		shooterMotor1.set(voltage);
	}
	
	/**
	 * Get the speed of the shooter
	*/
	public double getSpeed(){
		return shooterMotor1.getSpeed();
	}
	
	public void logTalonStatus() {
		Robot.log.writeLog(
				"Motor Speed, " + shooterMotor1.getSpeed() +
				", Motor Voltage, " + shooterMotor1.getOutputVoltage() +
				", Motor Vbus, " + shooterMotor1.getBusVoltage() +
				", Motor Current, " + shooterMotor1.getOutputCurrent() +
				", Motor error, " +  (shooterMotor1.getSpeed() - setSpeed)
		);		
	}

	/**
	 * Update the Smart Dashboard
	 */
	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Shooter Motor Speed", shooterMotor1.getSpeed());
		SmartDashboard.putNumber("VBus - Voltage", (shooterMotor1.getBusVoltage() - Math.abs(shooterMotor1.getOutputVoltage())));
		SmartDashboard.putNumber("Closed Loop Error", shooterMotor1.getSpeed() - setSpeed);
		SmartDashboard.putNumber("VBus", shooterMotor1.getBusVoltage());
		SmartDashboard.putNumber("Shooter Motor 1 Current", shooterMotor1.getOutputCurrent());
		SmartDashboard.putNumber("Shooter Motor voltage", shooterMotor1.getOutputVoltage());
		SmartDashboard.putBoolean("Connection Error", error);
		SmartDashboard.putNumber("Shooter Motor 1000*F", shooterMotor1.getF() * 1000);
	}

	/**
	 * Setup the Smart Dashboard
	 */
	public void setupSmartDashboard() {
		fNominal = robotPrefs.getDouble("shooterFNominal", 0);
		SmartDashboard.putNumber("Shooter Motor 1000*F", shooterMotor1.getF() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*P", shooterMotor1.getP() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*I", shooterMotor1.getI() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*D", shooterMotor1.getD() * 1000);
		SmartDashboard.putNumber("Shooter Motor Set RPM", shooterMotor1.get());
		SmartDashboard.putNumber("Shooter Motor Set Voltage", 9); 
		SmartDashboard.putNumber("Set Nominal 1000* F Value", fNominal*1000);  
	}

	/**
	 * Sets the PID from the Smart Dashboard  Nominal
	 */
	public void setPIDFromSmartDashboard(){
		double p= SmartDashboard.getNumber("Shooter Motor 1000*P", 0)/1000;
		double i= SmartDashboard.getNumber("Shooter Motor 1000*I", 0) / 1000;
		double d= SmartDashboard.getNumber("Shooter Motor 1000*D", 0) / 1000;
		fNominal = SmartDashboard.getNumber("Set Nominal 1000* F Value",0) / 1000;
		
		shooterMotor1.setP(p) ;
		shooterMotor1.setI(i);
		shooterMotor1.setD(d);
		robotPrefs.putDouble("shooterP",p); 
		robotPrefs.putDouble("shooterI",i); 
		robotPrefs.putDouble("shooterD",d); 
		robotPrefs.putDouble("shooterFNominal",fNominal); 

	}

	
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}

