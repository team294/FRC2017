package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;

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
	public static GearVision gearVision;
	public static Intake intake;
	public static Shooter shooter;
	public static ShooterHood shooterHood;
	
	
	// Vision subsystems
	public static BoilerVision boilerVision;
	
	// The OI
	public static OI oi;
	
	// File logger
	public static FileLog log;
	
	// set up preferences (robot specific calibrations flash memory in roborio)
	public static Preferences robotPrefs;
	public static double shooterP;
	public static double shooterI;
	public static double shooterD;
	public static double shooterFNominal;
	public static double inchesPerRevolution;
	public static boolean inchesPerRevolutionEnabled;
	public static boolean invertDrive;
	public static double intakeSpeed; // -1 to 1
	public static double shootSpeedHigh;
	public static double shootSpeedLow;
	public static double horizontalConveyor;
	public static double verticalConveyor;
	public static double horizontalConveyorOut;
	public static double verticalConveyorOut;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		
		System.out.println("Robot init");
		
		robotPrefs = Preferences.getInstance();
		shooterP = robotPrefs.getDouble("shooterP",0);  // This has to be done before Shooter()
		shooterI = robotPrefs.getDouble("shooterI",0);
		shooterD = robotPrefs.getDouble("shooterD",0);
		shooterFNominal = robotPrefs.getDouble("shooterFNominal",0);
		inchesPerRevolution = robotPrefs.getDouble("inchesPerRev", 0);
		if (inchesPerRevolution==0) {
			DriverStation.reportError("Error:  Preferences missing from RoboRio for Inches per Revolution calibration. Distance disabled.", true);
			inchesPerRevolutionEnabled = false;
			inchesPerRevolution = 100000;	//  set to a very large number for a minimum distance.  0 would go forever
		} else {
			inchesPerRevolutionEnabled = true;
		}
		invertDrive = robotPrefs.getBoolean("invertDrive",false);
		intakeSpeed = robotPrefs.getDouble("intakeSpeed",0);
		shootSpeedHigh = robotPrefs.getDouble("shootSpeedHighRPM",0);
		shootSpeedLow = robotPrefs.getDouble("shootSpeedLowRPM",0);
		horizontalConveyor = robotPrefs.getDouble("horizontalConveyor",0);
		verticalConveyor = robotPrefs.getDouble("verticalConveyor",0);
		horizontalConveyorOut = robotPrefs.getDouble("horizontalConveyorOut",0);
		verticalConveyorOut = robotPrefs.getDouble("verticalConveyorOut",0);
		
		log = new FileLog();
		driveTrain = new DriveTrain();
		shifter = new Shifter();
		shooter = new Shooter();
		intake = new Intake();
		gearGate = new GearGate();
		gearVision = new GearVision();
		boilerVision = new BoilerVision();
		log = new FileLog();
		gearVision = new GearVision();
		shooterHood = new ShooterHood();
		ballFeed = new BallFeed();
		
		ballFeed.ballFeedCurrentProtection();
		shooter.shooterCurrentProtection();
		intake.intakeCurrentProtection();
		driveTrain.leftCurrentProtection();
		driveTrain.rightCurrentProtection();
	
		robotPrefs = Preferences.getInstance();
			
		oi = new OI();

		// Put scheduler and subsystems on SmartDashboard
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(shifter);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(gearGate);

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
		
		driveTrain.logTalonStatus();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		intake.updateConflicts();
		log.writeLogEcho("Teleop Mode Started");
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

		shooter.updateSmartDashboard(); 
		shooter.periodicSetF();
		
    	// Stall protection
//		Robot.intake.intakeCurrentTrigger.whenActive(new IntakeSetToSpeed(0));
//		Robot.shooter.shooterMotorCurrentTrigger.whenActive(new ShooterSetVoltage(0));
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
