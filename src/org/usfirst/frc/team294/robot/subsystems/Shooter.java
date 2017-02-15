package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The shooter
 */
public class Shooter extends Subsystem {

	// Motor Hardware
	private final CANTalon shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
	private final CANTalon shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);
	DigitalInput jumper = new DigitalInput(RobotMap.jumper);
	
	double setSpeed;
	boolean error = false;
	private double fNominal;
	

	public Shooter() {
		super();
		
		
		shooterMotor1.setVoltageRampRate(24.0);
		shooterMotor2.setVoltageRampRate(24.0);
		
		if (jumper.get() == false) { // jumper in digital 1 will set PIDF values
									// for the PRACTICE ROBOT
									//false means the jumper is present
			shooterMotor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			shooterMotor1.configEncoderCodesPerRev(100);
			// shooterMotor.setPID(50.0, 0.2, 0, 40.0, 6000, 50, 0);
			
			// shooterMotor.setPID(.100, 0.0, .06, .00845, 6000, 500, 0); //
			// this was for the one motor system

			fNominal = 	0.008;
			shooterMotor1.setPID(.02, 0, .2, fNominal, 500, 500, 0); // two
																		// motor
																	// system
			shooterMotor1.reverseSensor(false);    // true for prototype false for practice!!!
			shooterMotor1.reverseOutput(false);
			shooterMotor1.changeControlMode(TalonControlMode.Speed);
			shooterMotor2.reverseOutput(false);
		}
		else{			// COMPETITION ROBOT   
			shooterMotor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
			shooterMotor1.configEncoderCodesPerRev(100);
			fNominal = 0.0088;	
			shooterMotor1.setPID(.02, 0, 1, fNominal, 500, 500, 0); // two
																	
			shooterMotor1.reverseSensor(false);
			shooterMotor1.reverseOutput(false);
			shooterMotor1.changeControlMode(TalonControlMode.Speed);
			shooterMotor2.reverseOutput(false);
		
		}
		shooterMotor2.changeControlMode(TalonControlMode.Follower);
		shooterMotor2.set(shooterMotor1.getDeviceID());
		shooterMotor1.enableBrakeMode(false);
		shooterMotor2.enableBrakeMode(false);
		shooterMotor1.set(0.0);
		setupSmartDashboard();
		periodicSetF();
		
	}
	
	/**
	 * Sets the shooter motor to speed according to rpm
	 * @param rpm from -1000 to 18000
	 * Only run reverse to clear a possible jam
	 */
	public void setRPM(double rpm) {
		shooterMotor1.changeControlMode(TalonControlMode.Speed);
		
		rpm = (rpm > 18000.0) ? 18000.0 : rpm;
		rpm = (rpm < -1000.0) ? -1000.0 : rpm;
		
		setSpeed = rpm;
		shooterMotor1.set(rpm);
	}
	
	public void periodicSetF(){
		double currentBatteryVoltage = shooterMotor1.getBusVoltage();
		double f = ((12.1/currentBatteryVoltage)*fNominal);
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
	 * @return from -1 to +1 (NOT VERIFIED. MAY DEPEND ON CONTROL MODE)
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
		SmartDashboard.putBoolean("COMPETITION Robot", jumper.get());
		SmartDashboard.putNumber("Shooter Motor 1 Current", shooterMotor1.getOutputCurrent());
		SmartDashboard.putNumber("Shooter Motor 2 Current", shooterMotor2.getOutputCurrent());
		SmartDashboard.putNumber("Shooter Motor voltage", shooterMotor1.getOutputVoltage());
		SmartDashboard.putBoolean("Connection Error", error);
		SmartDashboard.putNumber("Shooter Motor 1000*F", shooterMotor1.getF() * 1000);
	}

	/**
	 * Setup the Smart Dashboard
	 */
	public void setupSmartDashboard() {
		SmartDashboard.putNumber("Shooter Motor 1000*F", shooterMotor1.getF() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*P", shooterMotor1.getP() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*I", shooterMotor1.getI() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*D", shooterMotor1.getD() * 1000);
		SmartDashboard.putNumber("Shooter Motor Set RPM", shooterMotor1.get());
//		SmartDashboard.putNumber("Shooter Motor Set Vbus", 0.0);		
		SmartDashboard.putNumber("Fixed Recovery Voltage", shooterMotor1.get()); 
		SmartDashboard.putNumber("Set Nominal 1000* F Value", fNominal*1000);  // This should come from reference PIDF values
	}

	/**
	 * Sets the PID from the Smart Dashboard  Nominal
	 */
	public void setPIDFromSmartDashboard(){
		//shooterMotor.setF(SmartDashboard.getNumber("Shooter Motor 1000*F", 0) / 1000);
		shooterMotor1.setP(SmartDashboard.getNumber("Shooter Motor 1000*P", 0) / 1000);
		shooterMotor1.setI(SmartDashboard.getNumber("Shooter Motor 1000*I", 0) / 1000);
		shooterMotor1.setD(SmartDashboard.getNumber("Shooter Motor 1000*D", 0) / 1000);
		fNominal = SmartDashboard.getNumber("Set Nominal 1000* F Value",0) / 1000;
	}

	/**
	 * Set the shooter motor to speed according to Vbus
	 * @param speed from -1 to +1
	 */
	public void setSpeed(double speed) {
		shooterMotor1.changeControlMode(TalonControlMode.PercentVbus);
		speed = (speed > 1.0) ? 1 : speed;
		speed = (speed < -1.0) ? -1.0 : speed;
		shooterMotor1.set(speed);
	}
	
	/**
	 * Set the shooter speed according to Vbus
	 * @param vbus from -1 to +1
	 
	public void useVbusControl(double vbus){
		shooterMotor1.changeControlMode(TalonControlMode.PercentVbus);
		vbus = (vbus > 1.0) ? 1.0 : vbus;
		vbus = (vbus < -1.0) ? -1.0 : vbus;
		shooterMotor1.set(-vbus);	
	}
	*/
	
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}

