package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
//import org.usfirst.frc.team294.robot.commands.DriveWithJoysticks;
import org.usfirst.frc.team294.robot.commands.DriveWithJoysticks;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Teh Drivetrain
 */
public class DriveTrain extends Subsystem {

    // Drive Train hardware
	private final CANTalon leftMotor1 = new CANTalon(RobotMap.driveTrainLeftMotor1);
    private final CANTalon leftMotor2 = new CANTalon(RobotMap.driveTrainLeftMotor2);
    //private final CANTalon leftMotor3 = new CANTalon(RobotMap.driveTrainLeftMotor3);
    private final CANTalon rightMotor1 = new CANTalon(RobotMap.driveTrainRightMotor1);
    private final CANTalon rightMotor2 = new CANTalon(RobotMap.driveTrainRightMotor2);
    //private final CANTalon rightMotor3 = new CANTalon(RobotMap.driveTrainRightMotor3);
    private final RobotDrive robotDrive = new RobotDrive(leftMotor2, rightMotor2);

    public DriveTrain() {
    	super();
    	
    	leftMotor2.reverseSensor(true);
    	
    	leftMotor1.changeControlMode(TalonControlMode.Follower);
    	//leftMotor3.changeControlMode(TalonControlMode.Follower);
        rightMotor1.changeControlMode(TalonControlMode.Follower);
        //rightMotor3.changeControlMode(TalonControlMode.Follower);
        leftMotor1.set(leftMotor2.getDeviceID());
        //leftMotor3.set(leftMotor2.getDeviceID());
        rightMotor1.set(rightMotor2.getDeviceID());
        //rightMotor3.set(rightMotor2.getDeviceID());
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

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	//setDefaultCommand(new DriveWithJoysticks());
    	setDefaultCommand(new DriveWithJoysticks());
    }
}

