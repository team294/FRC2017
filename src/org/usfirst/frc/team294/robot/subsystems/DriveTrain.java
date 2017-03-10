package org.usfirst.frc.team294.robot.subsystems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team294.robot.commands.LogMotorGroupOverCurrent;
import org.usfirst.frc.team294.robot.triggers.MotorGroupCurrentTrigger;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main Robot Drive Train
 */
public class DriveTrain extends Subsystem {

    // Drive Train hardware
	private final CANTalon leftMotor1 = new CANTalon(RobotMap.driveTrainLeftMotor1);
    private final CANTalon leftMotor2 = new CANTalon(RobotMap.driveTrainLeftMotor2);
    private final CANTalon leftMotor3 = new CANTalon(RobotMap.driveTrainLeftMotor3);
    private final CANTalon rightMotor1 = new CANTalon(RobotMap.driveTrainRightMotor1);
    private final CANTalon rightMotor2 = new CANTalon(RobotMap.driveTrainRightMotor2);
    private final CANTalon rightMotor3 = new CANTalon(RobotMap.driveTrainRightMotor3);
    private final RobotDrive robotDrive = new RobotDrive(leftMotor2, rightMotor2);
    
//    private final Ultrasonic ultrasonicSensor;
    
    // NavX.  Create the object in the DriveTrain() constructor, so that we can catch errors.
    private AHRS ahrs;
	
    // Gyro resets are tracked in software, due to latency in resets. This holds the value of the NavX's "zero" degrees
    private double yawZero = 0;
    
    // Track encoder resets in software due to latency in the Talon/CANbus
    private double leftEncoderZero = 0, rightEncoderZero = 0;
    
    //Current protection
    List<CANTalon> rightMotorList = new ArrayList<CANTalon>(Arrays.asList(rightMotor1, rightMotor2, rightMotor3));
    List<CANTalon> leftMotorList = new ArrayList<CANTalon>(Arrays.asList(leftMotor1, leftMotor2, leftMotor3));
	//TODO:  Fix MotorGroupCurrentTrigger
//    public final MotorGroupCurrentTrigger rightMotorsCurrentTrigger = new MotorGroupCurrentTrigger(rightMotorList, 2.0, "right drive");
//    public final MotorGroupCurrentTrigger leftMotorsCurrentTrigger = new MotorGroupCurrentTrigger(leftMotorList, 2.0, "left drive");
        
    public DriveTrain() {
    	super();
    	
    	// Set the other motors to follow motor 2 on each side
    	leftMotor1.changeControlMode(TalonControlMode.Follower);
        leftMotor1.set(leftMotor2.getDeviceID());
    	leftMotor3.changeControlMode(TalonControlMode.Follower);
        leftMotor3.set(leftMotor2.getDeviceID());
        
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearLeft,Robot.invertDrive);
		robotDrive.setInvertedMotor(RobotDrive.MotorType.kRearRight,Robot.invertDrive);

        rightMotor1.changeControlMode(TalonControlMode.Follower);
        rightMotor1.set(rightMotor2.getDeviceID());
        rightMotor3.changeControlMode(TalonControlMode.Follower);
        rightMotor3.set(rightMotor2.getDeviceID());

        // Set up encoders
        leftMotor2.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        rightMotor2.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        leftMotor2.configEncoderCodesPerRev(100);
        rightMotor2.configEncoderCodesPerRev(100);
    	leftMotor2.reverseSensor(true);
    	
    	setDriveControlByPower();

        // Configure basic drive settings
        leftMotor2.clearStickyFaults();
        rightMotor2.clearStickyFaults();
        leftMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
        rightMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
        leftMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
        rightMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
        leftMotor2.setVoltageRampRate(40);
        rightMotor2.setVoltageRampRate(40);

        //TODO:  Adjust ramp-rate limits so that we don't draw too much current or tip the robot
        
    	try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */

    			//TODO:  Verify navX comms works using 2017 robot configuration (via USB?) 
    			//ahrs = new AHRS(SPI.Port.kMXP);		// Code from 2016 robot (navX plugged directly into RoboRio SPI port) 
    			ahrs = new AHRS(SerialPort.Port.kUSB); 
        	} catch (RuntimeException ex ) {
        		DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        		Robot.log.writeLog("Error instantiating navX MXP:  " + ex.getMessage());
        	}
        ahrs.zeroYaw();
        
//		ultrasonicSensor = new Ultrasonic(RobotMap.usTx,RobotMap.usRx);
//		ultrasonicSensor.setAutomaticMode(true);
    }
    
    public void leftCurrentProtection(){
    	//TODO: Verify that messages are printed when there is a motor error
    	//TODO:  Fix MotorGroupCurrentTrigger
//    	leftMotorsCurrentTrigger.whenActive(new LogMotorGroupOverCurrent(leftMotorsCurrentTrigger));
    }
    
    public void rightCurrentProtection(){
    	//TODO: Verify that messages are printed when there is a motor error
    	//TODO:  Fix MotorGroupCurrentTrigger
//    	rightMotorsCurrentTrigger.whenActive(new LogMotorGroupOverCurrent(rightMotorsCurrentTrigger));
    }

    /**
     * Set the drive controller to use power settings, instead of using the
     * encoder PID controller.
     */
    public void setDriveControlByPower() {
        leftMotor2.changeControlMode(TalonControlMode.PercentVbus);    	
        rightMotor2.changeControlMode(TalonControlMode.PercentVbus);
        leftMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
        rightMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
        //TODO -- Safety was set to false.  Check if it works with True.
        robotDrive.setSafetyEnabled(true);
    }
    
    /**
     * Use this in the execute method of a DriveWithJoysticks command.  <p>
     * <b>NOTE:</b> Be sure to call setDriveControlByPower() in the initialize method.
     * @param leftStick Left joystick
     * @param rightStick Right joystick
     */
    public void driveWithJoystick(double leftStick, double rightStick) {
    	robotDrive.tankDrive(leftStick, rightStick, false);
    }

    /**
     * Stop the drive train motors
     */
	public void stop() {
		setDriveControlByPower();
		robotDrive.drive(0, 0);
	}
    
	/**
	 * Drive the robot straight forward
	 * @param speed +1 to -1, + = forward, - = backward
	 */

	public void driveForward(double speed) {
		setDriveControlByPower();
		robotDrive.drive(-speed, 0);
	}

	/**
	 * Drive the robot straight backward
	 * @param speed +1 to -1, + = backward, - = forward
	 */
	public void driveBackward(double speed) {
		setDriveControlByPower();
		robotDrive.drive(speed, 0);
	}
	
	/**
	 * Drive the robot at an angle
	 * @param speed +1 to -1, + = backward, - = forward
	 * @param curve +1 to -1, >0 = left, <0 = right
	 */
	public void driveAtAngle(double speed, double curve) {
		setDriveControlByPower();
		robotDrive.drive(-speed, curve);
		//SmartDashboard.putNumber("driveTrain set speed", speed);
	}

    /**
     * Reset the encoders in software
     */
    public void resetEncoders() {
    	leftEncoderZero = leftMotor2.getPosition();
    	rightEncoderZero = rightMotor2.getPosition();
		Robot.log.writeLogEcho("Drive encoders reset");
    }

    /**
     * Get the left and right positions and the left and right speeds from the encoder
     * and update these values on the SmartDashboard.
     */
    public void updateSmartDashboardEncoders() {
    	SmartDashboard.putNumber("Left Position", leftMotor2.getPosition() - leftEncoderZero);
    	SmartDashboard.putNumber("Right Position", rightMotor2.getPosition() - rightEncoderZero);
    	SmartDashboard.putNumber("Left Speed", leftMotor2.getSpeed());
    	SmartDashboard.putNumber("Right Speed", rightMotor2.getSpeed());
    	getGyroAngle();
    }

    /**
     * Reads the value of the encoder on left motor 2
     * @return
     */
    public double getLeftEncoder() { //TODO 
    	SmartDashboard.putNumber("Left Position", leftMotor2.getPosition() - leftEncoderZero);
    	SmartDashboard.putNumber("Left Speed", leftMotor2.getSpeed());
    	return leftMotor2.getPosition() - leftEncoderZero;
    }
    
    /**
     * Read the value of the encoder on right motor 2
     * @return
     */

    public double getRightEncoder() {
    	SmartDashboard.putNumber("Right Position", rightMotor2.getPosition() - rightEncoderZero);
    	SmartDashboard.putNumber("Right Speed", rightMotor2.getSpeed());
    	return rightMotor2.getPosition() - rightEncoderZero;
    }
    
    /**
     * Reads the raw value of the left encoder without adjusting for resets
     * Testing purposes only
     * @return
     */
    public double getLeftEncoderRaw() {
    	return leftMotor2.getPosition();
    }
    
    /**
     * Reads the raw value of the right encoder without adjusting for resets
     * Testing purpsoes only
     * @return
     */
    public double getRightEncoderRaw() {
    	return rightMotor2.getPosition();
    }
    
    /**
     * Logs the talon output to the log file
     */
	public void logTalonStatus() {
		Robot.log.writeLog(
				"Motors:  Left Motor 2 (Main)-- TempC, " + leftMotor2.getTemperature() +
				", Set, " + leftMotor2.getSetpoint() + 
				", BusVolt, " + leftMotor2.getBusVoltage() + 
				", OutVolt, " + leftMotor2.getOutputVoltage() + 
				", OutCur, " + leftMotor2.getOutputCurrent() + 
				", Get, " + leftMotor2.get() +
				
				", Left Motor 1 (Follower)-- TempC, " + leftMotor1.getTemperature() + 
				", Set, " + leftMotor1.getSetpoint() + 
				", BusVolt, " + leftMotor1.getBusVoltage() + 
				", OutVolt, " + leftMotor1.getOutputVoltage() + 
				", OutCur, " + leftMotor1.getOutputCurrent() + 
				", Get, " + leftMotor1.get() +
				
				", Left Motor 3 (Follower)-- TempC, " + leftMotor1.getTemperature() + 
				", Set, " + leftMotor3.getSetpoint() + 
				", BusVolt, " + leftMotor3.getBusVoltage() + 
				", OutVolt, " + leftMotor3.getOutputVoltage() + 
				", OutCur, " + leftMotor3.getOutputCurrent() + 
				", Get, " + leftMotor3.get() +
				
				", Right Motor 2 (Main)-- TempC, " + rightMotor2.getTemperature() + 
				", Set, " + rightMotor2.getSetpoint() + 
				", BusVolt, " + rightMotor2.getBusVoltage() + 
				", OutVolt, " + rightMotor2.getOutputVoltage() + 
				", OutCur, " + rightMotor2.getOutputCurrent() + 
				", Get, " + rightMotor2.get() +
				
				", Right Motor 1 (Follower)-- TempC, " + rightMotor1.getTemperature() + 
				", Set, " + rightMotor1.getSetpoint() + 
				", BusVolt, " + rightMotor1.getBusVoltage() + 
				", OutVolt, " + rightMotor1.getOutputVoltage() + 
				", OutCur, " + rightMotor1.getOutputCurrent() + 
				", Get, " + rightMotor1.get() +
				
				", Right Motor 3 (Follower)-- TempC, " + rightMotor3.getTemperature() + 
				", Set, " + rightMotor3.getSetpoint() + 
				", BusVolt, " + rightMotor3.getBusVoltage() + 
				", OutVolt, " + rightMotor3.getOutputVoltage() + 
				", OutCur, " + rightMotor3.getOutputCurrent() + 
				", Get, " + rightMotor3.get()
				);
	}
	
	/** 
	 * Reset the angle of the NavX in the software to 0.
	 */
	public void resetDegrees() {
		// Note:  Do NOT use ahrs.reset(), since it can have occasional latency issues.
		
		yawZero = ahrs.getAngle();
		Robot.log.writeLogEcho("Gyro angle reset");
	}
    
    /**
     * Return the current angle of the gyro
     * @return current angle from 0 to 360
     */
    public double getGyroAngle() {
		double angle;
		
		angle = ahrs.getAngle() - yawZero; 
		
		// Normalize to 0 to 360 degrees
		angle = (angle + 360) % 360;
		//TODO
		SmartDashboard.putNumber("navX angle", angle>180.0 ? angle-360.0 : angle);
		//Robot.log.writeLog("Gyro: Current Angle: " + angle);
		
		return angle;
    }
    
    /**
     * Get the gyro rate
     * @return
     */
    public double getGyroRate() {
    	return ahrs.getRate();
    }
    
    /**
     * Get the distance range of the ultrasonic sensor in inches
     * @return
     */
//    public double getUltrasonicDistance() {
//    	return ultrasonicSensor.getRangeInches();
//    }

    /**
     * Update the SmartDashboard with NavX readings.
     */
    public void updateGyroSmartDashboard() {
        SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
        SmartDashboard.putNumber(   "IMU_FusedHeading",              ahrs.getFusedHeading());
        SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
    }

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveWithJoysticks());
    }
}

