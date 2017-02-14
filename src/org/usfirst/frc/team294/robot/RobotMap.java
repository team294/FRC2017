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
	public static int climbMotor1 = 3;
	public static int climbMotor2 = 4;
	public static int driveTrainLeftMotor1 = 5;
	public static int driveTrainLeftMotor2 = 6;
	public static int driveTrainLeftMotor3 = 7;
	public static int driveTrainRightMotor1 = 8;
	public static int driveTrainRightMotor2 = 9;
	public static int driveTrainRightMotor3 = 10;
	public static int intakeMotor = 11;
	public static int shooterMotor1 = 12;	
	public static int shooterMotor2 = 13;
	public static int horizontalConveyor = 14;
	public static int verticalConveyor = 15;

	// Pneumatic controller PCM IDs
    public static int shifterSolenoidFwd = 0;
    public static int shifterSolenoidRev = 1;
    public static int intakeSolenoidFwd = 2;
    public static int intakeSolenoidRev = 3;
    public static int gearSolenoid = 4;
    public static int hopperSolenoidFwd = 5;
    public static int hopperSolenoidRev = 6;
    public static int shooterHoodSolenoid = 7;
    
<<<<<<< HEAD
    // Analog I/O addresses
    public static int driveTrainGyro = 0;
    
    // Digital I/O addresses
    public static int usTx = 8;
    public static int usRx = 9;
<<<<<<< HEAD
=======
	// RoboRIO digital I/O addresses
    public static int jumper = 1;
    
>>>>>>> refs/remotes/origin/Add-Shooter
=======
    
    // Field Map (Magic Numbers)
    	// Measurements in Inches
    public static double distanceToLeftGearBeforeTurn = -93.25;
    public static double distanceToLeftGearAfterTurn = -35.25;
    public static double distanceToMiddleGear = -93.25;
    public static double distanceToRightGearBeforeTurn = -93.25;
    public static double distanceToRightGearAfterTurn = -35.25;
    	// Measurements in Degrees
    public static double turnToLeftGear = -30;
    public static double turnToRightGear = 30;
>>>>>>> 06df4b07c286d4ce82f558ead9572bc75bf3484c
}
