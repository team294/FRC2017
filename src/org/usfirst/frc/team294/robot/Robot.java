package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team294.robot.commands.*;
import org.usfirst.frc.team294.robot.subsystems.*;
import org.usfirst.frc.team294.utilities.FileLog;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	// Hardware subsystems
	public static DriveTrain driveTrain;
	public static Shifter shifter;
	public static BallFeed ballFeed;
	public static GearGate gearGate;
	public static Intake intake;
	public static Shooter shooter;
	public static ShooterHood shooterHood;
	
	// Vision subsystems
	public static BoilerVision boilerVision;
	public static GearVision gearVision;
	
	// The OI
	public static OI oi;
	
	// Turn on/off SmartDashboard debugging
	public static boolean smartDashboardDebug = false;		// true to print lots of stuff on the SmartDashboard
	
	//Timer
	public static Timer teleopTime;
	
	// File logger
	public static FileLog log;
	
	// set up preferences (robot specific calibrations flash memory in roborio)
	public static Preferences robotPrefs;
	public static double shooterP;
	public static double shooterI;
	public static double shooterD;
	public static double shooterFNominal;
	public static double inchesPerRevolution; //This will never change. Why is it in the robot preferences instead of just left in DriveStraightDistance?
	public static boolean invertDrive;
	public static double intakeSpeed; // -1 to 1 //I understand why this in in place for testing, but will we need to change the intake speed that often during comp?
	public static double shootSpeedHighRPM; //RPM
	public static double shootSpeedLowRPM; //RPM
	public static double horizontalConveyorInVolts; //Voltage
	public static double verticalConveyorInVolts; //Voltage
	public static double horizontalConveyorOutVolts;
	public static double verticalConveyorOutVolts;
	public static double gearCamHorizOffsetInches; // Gear vision cam horizontal offset
	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		System.out.println("Robot init");
		log = new FileLog();	// Create log file first, so other code can use it
		

		
		//TODO:  For each robot preference:  Use more descriptive names?
		robotPrefs = Preferences.getInstance();
		if (robotPrefs.getDouble("inchesPerRev", 0) == 0) {
			DriverStation.reportError("Error:  Preferences missing from RoboRio for Inches per Revolution calibration. Distance disabled.", true);
			robotPrefs.putDouble("inchesPerRevolution", 18.0);
		}
		inchesPerRevolution = robotPrefs.getDouble("inchesPerRev", 0);
		
		shooterP = robotPrefs.getDouble("shooterP",0.15);// This has to be done before Shooter()
		shooterI = robotPrefs.getDouble("shooterI",0);
		shooterD = robotPrefs.getDouble("shooterD",0);
		shooterFNominal = robotPrefs.getDouble("shooterFNominal",.024);
		
		invertDrive = robotPrefs.getBoolean("invertDrive",true);
/**		if(invertDrive == false){
			robotPrefs.putBoolean("invertDrive",true);
		}
**/
		
		if(robotPrefs.getDouble("intakeSpeed",0) == 0){
			robotPrefs.putDouble("intakeSpeed",1.0);
		}
		intakeSpeed = robotPrefs.getDouble("intakeSpeed",0);

		
		shootSpeedHighRPM = robotPrefs.getDouble("shootSpeedHighRPM",4200);
		shootSpeedLowRPM = robotPrefs.getDouble("shootSpeedLowRPM",3800);

		
		if(robotPrefs.getDouble("horizontalConveyor",0) == 0){
				robotPrefs.putDouble("horizontalConeyorInVolts", 4.5);
			}	
		horizontalConveyorInVolts = robotPrefs.getDouble("horizontalConveyorInVolts",0);

		if(robotPrefs.getDouble("verticalConveyorInVolts",0) == 0){
			robotPrefs.putDouble("verticalConveyorInVolts", 7.5);
		}
		verticalConveyorInVolts = robotPrefs.getDouble("verticalConveyorInVolts",0);
		
		if(robotPrefs.getDouble("horizontalConveyorOut",0) == 0){
			robotPrefs.putDouble("horizontalConveyorOutVolts", -2.0);
		}
		horizontalConveyorOutVolts = robotPrefs.getDouble("horizontalConveyorOut", 0);
		
		if(robotPrefs.getDouble("verticalConveyorOut",0) == 0){
			robotPrefs.putDouble("verticalConveyorOutVolts", -2.0);
		}
		verticalConveyorOutVolts = robotPrefs.getDouble("verticalConveyorOutVolts",0);
		

		gearCamHorizOffsetInches = robotPrefs.getDouble("gearCam",0);
		
		log = new FileLog();
		driveTrain = new DriveTrain();
		shifter = new Shifter();
		shooter = new Shooter();
		intake = new Intake();
		gearGate = new GearGate();
		gearVision = new GearVision();
		boilerVision = new BoilerVision();
		shooterHood = new ShooterHood();
		ballFeed = new BallFeed();

		// Turn on current protection for motors
		ballFeed.ballFeedCurrentProtection();
		shooter.shooterCurrentProtection();
		intake.intakeCurrentProtection();
		driveTrain.leftCurrentProtection();
		driveTrain.rightCurrentProtection();
		
		//Automatic shutdown
		Robot.driveTrain.shutdownTimer.whenActive(new StopAllMotors());
		
		// Turn on drive camera
		CameraServer.getInstance().startAutomaticCapture();

		// Create OI
		oi = new OI();

		// Put scheduler and subsystems on SmartDashboard
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(shifter);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(gearGate);
		SmartDashboard.putData(shooterHood);
		SmartDashboard.putData(ballFeed);
//		SmartDashboard.putData(gearVision);
//		SmartDashboard.putData(boilerVision);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	public void disabledInit() {
		intake.updateConflicts();

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		
		teleopTime.reset();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */

	public void autonomousInit() {
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		intake.updateConflicts();
		log.writeLogEcho("Autonomous Mode Started");
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
//		driveTrain.logTalonStatus();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		intake.updateConflicts();
		log.writeLogEcho("Teleop Mode Started");
		teleopTime.start();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();	
		//for testing purposes 
		driveTrain.updateSmartDashboardEncoders();
		boilerVision.updateSmartDashboard();
		//driveTrain.logTalonStatus();
		intake.updateSmartDashboardClimbMotorCurrent();

		shooter.updateSmartDashboard(); 
		shooter.periodicSetF();
		
		intake.updateSmartDashboard();
//		intake.logIntakeStatus();
		
		teleopTime.get();
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
