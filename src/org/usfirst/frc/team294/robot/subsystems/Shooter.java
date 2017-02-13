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
 * Output Motors, shooter and right
 */
public class Shooter extends Subsystem {

	// Motor Hardware
	private final CANTalon shooterMotor1 = new CANTalon(RobotMap.shooterMotor1);
	private final CANTalon shooterMotor2 = new CANTalon(RobotMap.shooterMotor2);
	DigitalInput jumper = new DigitalInput(RobotMap.jumper);
	double setSpeed;
	boolean error = false;

	public Shooter() {
		super();
		shooterMotor1.setVoltageRampRate(24.0);
		shooterMotor2.setVoltageRampRate(24.0);
									// for the second shooter
									//false means the jumper is present
		shooterMotor1.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
		shooterMotor1.configEncoderCodesPerRev(100);
			// shooterMotor.setPID(50.0, 0.2, 0, 40.0, 6000, 50, 0);
			/*
			 * It looks like the feedforward term sets a percent VBUS. It would
			 * therefore be better to multiply f by VBAT/12 to compensate for
			 * battery voltage variation.
			 */
			// shooterMotor.setPID(.100, 0.0, .06, .00845, 6000, 500, 0); //
			// this was for the one motor system

		shooterMotor1.setPID(.02, 0, 1, .0088, 500, 500, 0); // two
																		// motor
																		// system
		shooterMotor1.reverseSensor(false);
		shooterMotor1.reverseOutput(false);
		shooterMotor1.changeControlMode(TalonControlMode.Speed);
		shooterMotor2.reverseOutput(false);


		shooterMotor2.changeControlMode(TalonControlMode.Follower);
		shooterMotor2.set(RobotMap.shooterMotor1);
		shooterMotor1.enableBrakeMode(false);
		shooterMotor2.enableBrakeMode(false);
		shooterMotor1.set(0.0);
	}
	
	/**
	 * Sets the shooter motor to a specific speed
	 * 
	 * @param speed
	 *            Double from 6000 to -100 as rpm
	 */
	public void setShooterMotorToSpeed(double speed) {
		shooterMotor1.changeControlMode(TalonControlMode.Speed);
		if (speed > 6000.0){
			speed = 6000.0;
		}
		if (speed < -500.0){
			speed = -500.0;
		}// Only run reverse to clear a possible jam
		setSpeed = speed;
		shooterMotor1.set(speed);
	}
	
	public void periodicSetF(double fInit){
		double currentBatteryVoltage = shooterMotor1.getBusVoltage();
		double f = ((12.1/currentBatteryVoltage)*fInit);
		shooterMotor1.setF(f);
	}
	
	public void useVbusControl(double vbus){
		shooterMotor1.changeControlMode(TalonControlMode.PercentVbus);
		if (vbus > 1){
			vbus = 1;
		}
		if (vbus < -1){
			vbus = -1;
		}
		shooterMotor1.set(-vbus);	
	}
	
	public void setVoltage(double voltage){
		shooterMotor1.changeControlMode(TalonControlMode.Voltage);
		if(voltage < -12){
			voltage = -12;
		}if (voltage > 12){
			voltage = 12;
		}
		shooterMotor1.set(voltage);
	}
	
	public double getCurrentSpeed(){
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
	
	public void brakeTheShooterMotor(double speed) {
		shooterMotor1.changeControlMode(TalonControlMode.Voltage);
		shooterMotor1.set(0);

	}

	/**
	 * Sets the right motor to a specific speed
	 * 
	 * @param speed
	 *            Double from -1.0 to 1.0 as a percentage of battery voltage
	 */
	/*
	 * public void setRightMotorToSpeed(double speed) { if (speed > 1.0) speed =
	 * 1.0; if (speed < -1.0) speed = -1.0; shooterMotor1.set(speed); }
	 */

	public void updateSmartDashboard() {
		SmartDashboard.putNumber("Shooter Motor Speed", shooterMotor1.getSpeed());
		SmartDashboard.putNumber("VBus - Voltage", (shooterMotor1.getBusVoltage() - Math.abs(shooterMotor1.getOutputVoltage())));
		SmartDashboard.putNumber("Closed Loop Error", shooterMotor1.getSpeed() - setSpeed);
		SmartDashboard.putNumber("VBus", shooterMotor1.getBusVoltage());
		SmartDashboard.putBoolean("Shooter One", jumper.get());
		SmartDashboard.putNumber("Shooter Motor 1 Current", shooterMotor1.getOutputCurrent());
		SmartDashboard.putNumber("Shooter Motor 2 Current", shooterMotor2.getOutputCurrent());
		SmartDashboard.putNumber("Shooter Motor voltage", shooterMotor1.getOutputVoltage());
		SmartDashboard.putBoolean("Connection Error", error);
		SmartDashboard.putNumber("Shooter Motor 1000*F", shooterMotor1.getF() * 1000);
	}

	public void setupSmartDashboard() {
		//SmartDashboard.putNumber("Shooter Motor 1000*F", shooterMotor.getF() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*P", shooterMotor1.getP() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*I", shooterMotor1.getI() * 1000);
		SmartDashboard.putNumber("Shooter Motor 1000*D", shooterMotor1.getD() * 1000);
		SmartDashboard.putNumber("Shooter Motor Set Speed", shooterMotor1.get());
		SmartDashboard.putNumber("Shooter Motor Set Vbus", 0.0);		
		SmartDashboard.putNumber("Fixed Recovery Voltage", shooterMotor1.get()); 
		SmartDashboard.putNumber("Set Nominal F Value", 8.8);
	}

	public void setPIDFromSmartDashboard(){
		//shooterMotor.setF(SmartDashboard.getNumber("Shooter Motor 1000*F", 0) / 1000);
		shooterMotor1.setP(SmartDashboard.getNumber("Shooter Motor 1000*P", 0) / 1000);
		shooterMotor1.setI(SmartDashboard.getNumber("Shooter Motor 1000*I", 0) / 1000);
		shooterMotor1.setD(SmartDashboard.getNumber("Shooter Motor 1000*D", 0) / 1000);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}

