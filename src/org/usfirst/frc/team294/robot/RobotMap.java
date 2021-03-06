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
	// If these change update wiring map at bottom!
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
//    public static int shifterSolenoidFwd = 0;
//    public static int shifterSolenoidRev = 1;
    public static int intakeSolenoidFwd = 2;
    public static int intakeSolenoidRev = 3;
    public static int hopperSolenoidFwd = 4;
    public static int hopperSolenoidRev = 5;
//    public static int shooterHoodSolenoid = 6;
//    public static int gearSolenoid = 7;
    //NEW IDs FOR ACTIVE GEAR ON COMP ROBOT TODO get the new IDs 
    public static int shifterSolenoid = 0;
    public static int gearTiltSolenoid = 7;
    public static int gearShieldSolenoid = 1; //these might change
    public static int gearPuncherSolenoid = 6; //these might change

    // Analog I/O addresses
    public static int driveTrainGyro = 0;
    
    // Digital I/O addresses
    public static int cameraLightBoiler = 0;    
    public static int cameraLightGear = 1;
    public static int usTx = 8;		// Ultrasonic sensor
    public static int usRx = 9;		// Ultrasonic sensor
        
    // Field Map
    
    // Measurements in Inches
    public static double distanceToBaseline = -63.25;
    public static double distanceToGearSide = -35.25;
    public static double distanceToGearMiddle = -5.0;
    
    public enum AutoDistances {
    	toBaseLine, toGearSide, toGearMiddle
    }
    	
    // Measurements in Degrees
    public static double leftGearAngle = -30;
    public static double rightGearAngle = 30;
    
    public enum AutoAngles {
    	leftGear, rightGear
    }
    
	public enum StartPositions {
		left, middle, right, baselineOnly
	}
	
	public enum Teams {
		noBoilerShooting, blue, red, hopperBlue, hopperRed
	}
	
    public static double getDistance(AutoDistances distance) {
    	if (distance == AutoDistances.toBaseLine) return distanceToBaseline;
    	if (distance == AutoDistances.toGearSide) return distanceToGearSide;
    	if (distance == AutoDistances.toGearMiddle) return distanceToGearMiddle;
    	return 0.0;
    }
    
    public static double getAngle(AutoAngles angle) {
    	if (angle == AutoAngles.leftGear) return leftGearAngle;
    	if (angle == AutoAngles.rightGear) return rightGearAngle;
    	return 0.0;
    }
}

/******     ACTUAL MAP  of TALONS   **************
 * 				(THIS SIDE UP)
 * _________________________________________
 * INTAKE (11)				  
 * ________________________________________
 * VERTICAL CONVEYOR(15)	SHOOTER 2 (13)
 * _________________________________________
 * HORIZONTAL CONVEYOR(14)	SHOOTER 1 (12)
 * _________________________________________
 * CLIMB 2 (4)              CLIMB 1(8)
 * _________________________________________
 * DT LEFT 1 (5)            DT RIGHT 1 (8)
 * _________________________________________
 * DT LEFT 2 (6)            DT RIGHT 2 (9)
 * _________________________________________
 * DT LEFT 3 (7)            DT RIGHT 3 (10)
 *__________________________________________
 *
**************************************************/