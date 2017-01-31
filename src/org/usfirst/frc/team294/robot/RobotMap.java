package org.usfirst.frc.team294.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	// Hardware addresses
	
	// CANbus Addresses
	public static int driveTrainLeftMotor1 = 5;
	public static int driveTrainLeftMotor2 = 6;
	public static int driveTrainLeftMotor3 = 7;
	public static int driveTrainRightMotor1 = 9;
	public static int driveTrainRightMotor2 = 8;
	public static int driveTrainRightMotor3 = 12;
	public static int intakeMotor = 9;
	public static int shooterMotor = 20;
	public static int shooterHoodMotor = 15;
    
	// Pneumatic controller PCM IDs
    public static int shifterSolenoidFwd = 0;
    public static int shifterSolenoidRev = 1;
    public static int intakeSolenoidDeploy = 2;
    public static int intakeSolenoidStow = 3;
    public static int gearSolenoid = 4;
    
    // Analog I/O addresses
    public static int driveTrainGyro = 0;
}
