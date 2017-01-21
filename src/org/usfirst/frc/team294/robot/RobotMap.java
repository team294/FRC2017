package org.usfirst.frc.team294.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//hardware addresses
	
	//CANbus Addresses
	public static int driveTrainLeftMotor1 = 5;
	public static int driveTrainLeftMotor2 = 6;
	//public static int driveTrainLeftMotor3 = 7;
	public static int driveTrainRightMotor1 = 9;
	public static int driveTrainRightMotor2 = 4;
	//public static int driveTrainRightMotor3 = 12;
	//public static int intakeMotor = 9;
	
	// If you are using multiple modules, make sure to define both the port
	// number and the module. For example you with a rangefinder:
	// public static int rangefinderPort = 1;
	// public static int rangefinderModule = 1;
}
