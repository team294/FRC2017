package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.ctre.CANTalon.TalonControlMode;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
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
    
    private AHRS ahrs = new AHRS(SPI.Port.kMXP);
	
    // Gyro resets are tracked in software, due to latency in resets. This holds the value of the NavX's "zero" degrees
    private double yawZero = 0;
    

    public DriveTrain() {
    	// Call the Subsystem constructor
    	super();
    	    	
    	// Set the other motors to follow motor 2 on each side
    	leftMotor1.changeControlMode(TalonControlMode.Follower);
    	leftMotor3.changeControlMode(TalonControlMode.Follower);
        rightMotor1.changeControlMode(TalonControlMode.Follower);
        rightMotor3.changeControlMode(TalonControlMode.Follower);
        leftMotor1.set(leftMotor2.getDeviceID());
        leftMotor3.set(leftMotor2.getDeviceID());
        rightMotor1.set(rightMotor2.getDeviceID());
        rightMotor3.set(rightMotor2.getDeviceID());
//    	leftMotor3.changeControlMode(TalonControlMode.Follower);
        rightMotor1.changeControlMode(TalonControlMode.Follower);
//		rightMotor3.changeControlMode(TalonControlMode.Follower);
        leftMotor1.set(leftMotor2.getDeviceID());
//		leftMotor3.set(leftMotor2.getDeviceID());
        rightMotor1.set(rightMotor2.getDeviceID());
//		rightMotor3.set(rightMotor2.getDeviceID());
        
        // Configure encoders on motor 2 on each side
        leftMotor2.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        rightMotor2.setFeedbackDevice(FeedbackDevice.CtreMagEncoder_Relative);
        leftMotor2.configEncoderCodesPerRev(100);
        rightMotor2.configEncoderCodesPerRev(100);
    	leftMotor2.reverseSensor(true);

        // Configure basic drive settings
        leftMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
        rightMotor2.configNominalOutputVoltage(+0.0f, -0.0f);
        leftMotor2.setVoltageRampRate(40);
        rightMotor2.setVoltageRampRate(40);
        
        ahrs.zeroYaw();
        
        SmartDashboard.putBoolean(  "IMU_Connected",        ahrs.isConnected());
        SmartDashboard.putBoolean(  "IMU_IsCalibrating",    ahrs.isCalibrating());
        SmartDashboard.putNumber(   "IMU_Yaw",              ahrs.getYaw());
        SmartDashboard.putNumber(   "IMU_Pitch",            ahrs.getPitch());
        SmartDashboard.putNumber(   "IMU_Roll",             ahrs.getRoll());
        setDriveControlByPower();
        
        // Configure the RobotDrive
        robotDrive.setExpiration(0.1);
        robotDrive.setSensitivity(0.5);
        robotDrive.setMaxOutput(1.0);
    	
        // Setup the navX
        try {
            /* Communicate w/navX MXP via the MXP SPI Bus.                                     */
            /* Alternatively:  I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB     */
            /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for details. */
            ahrs = new AHRS(SPI.Port.kMXP); 
        } catch (RuntimeException ex ) {
            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
        }
        ahrs.zeroYaw();
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
        robotDrive.setSafetyEnabled(true);
    }
    
    /**
     * Use this in the execute method of a DriveWithJoysticks command.  <p>
     * <b>NOTE:</b> Be sure to call setDriveControlByPower() in the initialize method.
     * @param leftStick Left joystick
     * @param rightStick Right joystick
     */
    public void driveWithJoystick(Joystick leftStick, Joystick rightStick) {
        leftMotor2.clearStickyFaults();
        rightMotor2.clearStickyFaults();
    	robotDrive.tankDrive(leftStick, rightStick);
    }

    /**
     * Stop the drive train motors
     */
	public void stop() {
		setDriveControlByPower();
		robotDrive.drive(0, 0);
    	SmartDashboard.putNumber("driveTrain set speed", 0);
	}
    
	/**
	 * Drive the robot straight forward
	 * @param speed +1 to -1, + = forward, - = backward
	 */
	public void driveForward(double speed) {
		setDriveControlByPower();
		robotDrive.drive(-speed, 0);
    	SmartDashboard.putNumber("driveTrain set speed", speed);
	}

	/**
	 * Drive the robot straight backward
	 * @param speed +1 to -1, + = backward, - = forward
	 */
	public void driveBackward(double speed) {
		setDriveControlByPower();
		robotDrive.drive(speed, 0);
    	SmartDashboard.putNumber("driveTrain set speed", -speed);
	}
	
	/**
	 * Drive the robot at an angle
	 * @param speed +1 to -1, + = backward, - = forward
	 * @param curve +1 to -1, <0 = left, >0 = right
	 */
	public void driveAtAngle(double speed, double curve) {
		setDriveControlByPower();
		robotDrive.drive(-speed, curve);
    	SmartDashboard.putNumber("driveTrain set speed", speed);
	}
	
    /**
     * get the left and right positions and the left and right speeds from the encoders
     */
    public void getEncoder() {
    	SmartDashboard.putNumber("Left Position", leftMotor2.getPosition());
    	SmartDashboard.putNumber("Right Position", rightMotor2.getPosition());
    	SmartDashboard.putNumber("Left Speed", leftMotor2.getSpeed());
    	SmartDashboard.putNumber("Right Speed", rightMotor2.getSpeed());
    }
    
    /**
     * Reads the value of the encoder on left motor 2
     * @return
     */
    public double readLeftEncoder() {
    	return leftMotor2.getPosition();
    }
    
    /**
     * Reads the value of the encoder on right motor 2
     * @return
     */
    public double getRightEncoder() {
    	return rightMotor2.getPosition();
    }

    /**
     * Logs the drive Talon status to a file
     */
	public void logTalonStatus() {
		Robot.log.writeLog(
				"Motors:  Left Motor 2 (Main)-- TempC " + leftMotor2.getTemperature() +
				" Set " + leftMotor2.getSetpoint() + 
				" BusVolt " + leftMotor2.getBusVoltage() + 
				" OutVolt " + leftMotor2.getOutputVoltage() + 
				" OutCur " + leftMotor2.getOutputCurrent() + 
				" Get " + leftMotor2.get() +
				
				" Left Motor 1 (Follower)-- TempC " + leftMotor1.getTemperature() + 
				" Set " + leftMotor1.getSetpoint() + 
				" BusVolt " + leftMotor1.getBusVoltage() + 
				" OutVolt " + leftMotor1.getOutputVoltage() + 
				" OutCur " + leftMotor1.getOutputCurrent() + 
				" Get " + leftMotor1.get() +
				
				/* " Left Motor 3 (Follower)-- TempC " + leftMotor1.getTemperature() + 
				" Set " + leftMotor3.getSetpoint() + 
				" BusVolt " + leftMotor3.getBusVoltage() + 
				" OutVolt " + leftMotor3.getOutputVoltage() + 
				" OutCur " + leftMotor3.getOutputCurrent() + 
				" Get " + leftMotor3.get() +
				*/
				
				" Right Motor 2 (Main)-- TempC " + rightMotor2.getTemperature() + 
				" Set " + rightMotor2.getSetpoint() + 
				" BusVolt " + rightMotor2.getBusVoltage() + 
				" OutVolt " + rightMotor2.getOutputVoltage() + 
				" OutCur " + rightMotor2.getOutputCurrent() + 
				" Get " + rightMotor2.get() +
				
				" Right Motor 1 (Follower)-- TempC " + rightMotor1.getTemperature() + 
				" Set " + rightMotor1.getSetpoint() + 
				" BusVolt " + rightMotor1.getBusVoltage() + 
				" OutVolt " + rightMotor1.getOutputVoltage() + 
				" OutCur " + rightMotor1.getOutputCurrent() + 
				" Get " + rightMotor1.get()
				
				/*+ " Right Motor 3 (Follower)-- TempC " + rightMotor3.getTemperature() + 
				" Set " + rightMotor3.getSetpoint() + 
				" BusVolt " + rightMotor3.getBusVoltage() + 
				" OutVolt " + rightMotor3.getOutputVoltage() + 
				" OutCur " + rightMotor3.getOutputCurrent() + 
				" Get " + rightMotor3.get()
				*/
				);
	}
	
	/** 
	 * Reset the angle of the NavX in the software
	 */
	public void resetDegrees() {
		yawZero = ahrs.getAngle();
	}
    
    /**
     * Return the current angle of the gyro
     * @return current angle from 0 to 360
     */
    public double getGyroAngle() {
		double angle;
		
		angle = ahrs.getAngle() - yawZero; 
		
		// Normalize to 0 to 360 degrees
		angle = angle - Math.floor(angle/360)*360;
		
		SmartDashboard.putNumber("navX angle", angle>180.0 ? angle-360.0 : angle);
		Robot.log.writeLog(" Gyro: Current Angle: " + angle);
		
		return angle;
    }
    
    /**
     * Get the gyro rate
     * @return
     */
    public double getGyroRate() {
    	return ahrs.getRate();
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new DriveWithJoysticks());
    }
}

