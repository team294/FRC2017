package org.usfirst.frc.team294.robot;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.Timer;

import org.usfirst.frc.team294.robot.subsystems.*;
import org.opencv.core.Mat;
import org.usfirst.frc.team294.robot.commands.AutoGearAndScore;
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

	// Vision subsystems
	public static BoilerVision boilerVision;
	public static GearVision gearVision;

	// The OI
	public static OI oi;

	// Turn on/off SmartDashboard debugging
	public static boolean smartDashboardDebug = false;		// true to print lots of stuff on the SmartDashboard

	//Timer
	public static Timer teleopTime;
	public static double startTime;

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
	public static double driveP;
	public static double driveI;
	public static double driveD;
	public static double angleP;
	public static double angleI;
	public static double angleD;
	
	// Variable for auto command
	Command autonomousCommand;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {

		System.out.println("Robot init");
		log = new FileLog();	// Create log file first, so other code can use it
		readPreferences();		// Read preferences next, so that subsystems can use the preference values.

		teleopTime = new Timer();

		// Create the subsytems
		driveTrain = new DriveTrain();
		shifter = new Shifter();
		shooter = new Shooter();
		intake = new Intake();
		gearGate = new GearGate();
		gearVision = new GearVision();
		boilerVision = new BoilerVision();
		ballFeed = new BallFeed();

		// Turn on current protection for motors
		ballFeed.ballFeedCurrentProtection();
		shooter.shooterCurrentProtection();
		intake.intakeCurrentProtection();
		driveTrain.leftCurrentProtection();
		driveTrain.rightCurrentProtection();

		// Turn on drive camera
		//CameraServer.getInstance().startAutomaticCapture().setResolution(320, 240);

		// Create OI
		oi = new OI();

		// Put scheduler and subsystems on SmartDashboard
		SmartDashboard.putData(Scheduler.getInstance());
		SmartDashboard.putData(driveTrain);
		SmartDashboard.putData(shifter);
		SmartDashboard.putData(shooter);
		SmartDashboard.putData(intake);
		SmartDashboard.putData(gearGate);
		SmartDashboard.putData(ballFeed);
		//		SmartDashboard.putData(gearVision);
		//		SmartDashboard.putData(boilerVision);

		//new Thread(() -> {
		//	UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		//	camera.setResolution(320, 240);

		//	CvSink cvSink = CameraServer.getInstance().getVideo();
		// outputStream = CameraServer.getInstance().putVideo("Blur", 320, 240);

//			Mat source = new Mat();
	//		Mat output = new Mat();

		//	while(!Thread.interrupted()) {
			//	cvSink.grabFrame(source);
			//	outputStream.putFrame(output);
		//	}
		//}).start();
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
		//stops all motors
		Robot.shooter.stop();
		Robot.ballFeed.stop();
		Robot.intake.stopIntake();
		Robot.intake.stopClimber();
		Robot.driveTrain.stop();

		//SmartDashboard.putNumber("Gear Angle", Robot.gearVision.getGearAngleOffset());
		//SmartDashboard.putNumber("Gear Distance", Robot.gearVision.getGearDistance());
		//for (int i = 0; i < 10; i++) {
			SmartDashboard.putNumber("Boiler Angle", Robot.boilerVision.getBoilerAngleOffset());
			SmartDashboard.putNumber("Boiler Distance", Robot.boilerVision.getBoilerDistance());
		//}
		//System.out.print("Gear Distance: ");
		//System.out.println(Robot.gearVision.getGearDistance());
		//System.out.print("Angle Distance: ");
		//System.out.println(Robot.gearVision.getGearAngleOffset());
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
		// schedule the autonomous command (example)
		intake.updateConflicts();
		log.writeLogEcho("Autonomous Mode Started");

		autonomousCommand = new AutoGearAndScore(oi.readMiddleKnobTeam(), oi.readBottomKnobStartPosition());

		if (autonomousCommand != null)
			autonomousCommand.start();
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
		startTime = teleopTime.get();
		//SmartDashboard.putNumber("Gyro dubdubdubdubdubdubdubdubdub", driveTrain.getGyroAngle());

		//DeployIntakeAndHopper();
		//ShooterSetRPM(Robot.shootHighSpeed);
		readPreferences();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();	

		Robot.shooter.updateSmartDashboardShooterSpeed();
		SmartDashboard.putNumber("Gear Angle", Robot.gearVision.getGearAngleOffset());
		SmartDashboard.putNumber("Gear Distance", Robot.gearVision.getGearDistance());

		intake.logClimbStatus();
		gearGate.updateSmartDashboard();
//		intake.logIntakeStatus();
//		driveTrain.logTalonStatus();
		
		if (Robot.smartDashboardDebug) {
			//SmartDashboard stuff here 
			driveTrain.updateSmartDashboardEncoders();
			boilerVision.updateSmartDashboard();

			SmartDashboard.putNumber("Gear Angle", Robot.gearVision.getGearAngleOffset());
			SmartDashboard.putNumber("Gear Distance", Robot.gearVision.getGearDistance());

			shooter.updateSmartDashboard(); 
			intake.updateSmartDashboard();
		}

		//shooter.periodicSetF();
		//May be interfering with PID's, taking out temporarily

		oi.readBottomKnobRaw();
		oi.readMiddleKnobRaw();

		/*		if (false) {//(teleopTime.get() - startTime) >= 300) { //auto stops all non drive train motors after set time
			Robot.shooter.stop();
	    	Robot.ballFeed.stop();
	    	Robot.intake.stopIntake();
	    	Robot.intake.stopClimber();	
	    	System.out.println("All Motors Timed Out Reenable to Reset");

	    }*/
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}

	/**
	 * Read the preferences from the RoboRio flash memory.
	 * For any missing preferences, set the preference to default values.
	 */
	public void readPreferences() {
		//TODO:  Create function to read and set defaults for one number preference, then move most prefs
		//  to calling this function.  This will eliminate much of the duplicate code below.

		//TODO:  For each robot preference:  Use more descriptive names?
		robotPrefs = Preferences.getInstance();

		if (robotPrefs.getDouble("inchesPerRev", 0) == 0) {
			DriverStation.reportError("Error:  Preferences missing from RoboRio for Inches per Revolution calibration.", true);
			robotPrefs.putDouble("inchesPerRev", 12.5); //this needs to be changed when we find the new value
		}
		inchesPerRevolution = robotPrefs.getDouble("inchesPerRev", 12.5);

		shooterP = robotPrefs.getDouble("shooterP",0.15);// This has to be done before Shooter()
		shooterI = robotPrefs.getDouble("shooterI",0);
		shooterD = robotPrefs.getDouble("shooterD",0);
		shooterFNominal = robotPrefs.getDouble("shooterFNominal",.024);

		invertDrive = robotPrefs.getBoolean("invertDrive",true);

		if(robotPrefs.getDouble("intakeSpeed",0) == 0){
			robotPrefs.putDouble("intakeSpeed",1.0);
		}
		intakeSpeed = robotPrefs.getDouble("intakeSpeed",0);


		shootSpeedHighRPM = robotPrefs.getDouble("shootSpeedHighRPM",4200);
		shootSpeedLowRPM = robotPrefs.getDouble("shootSpeedLowRPM",3800);


		if(robotPrefs.getDouble("horizontalConveyorInVolts",0) == 0){
			robotPrefs.putDouble("horizontalConveyorInVolts", 12.0);
		}	
		horizontalConveyorInVolts = robotPrefs.getDouble("horizontalConveyorInVolts",0);

		if(robotPrefs.getDouble("verticalConveyorInVolts",0) == 0){
			robotPrefs.putDouble("verticalConveyorInVolts", 12.0);
		}
		verticalConveyorInVolts = robotPrefs.getDouble("verticalConveyorInVolts",0);

		if(robotPrefs.getDouble("horizontalConveyorOutVolts",0) == 0){
			robotPrefs.putDouble("horizontalConveyorOutVolts", -12.0);
		}
		horizontalConveyorOutVolts = robotPrefs.getDouble("horizontalConveyorOutVolts", 0);

		if(robotPrefs.getDouble("verticalConveyorOutVolts",0) == 0){
			robotPrefs.putDouble("verticalConveyorOutVolts", -10.0);
		}
		verticalConveyorOutVolts = robotPrefs.getDouble("verticalConveyorOutVolts",0);

		driveP = robotPrefs.getDouble("driveP",3);
		driveI = robotPrefs.getDouble("driveI",0);
		driveD = robotPrefs.getDouble("driveD",0);
		angleP = robotPrefs.getDouble("angleP",.025);
		angleI = robotPrefs.getDouble("angleI",0);
		angleD = robotPrefs.getDouble("angleD",0.05);
		
		gearCamHorizOffsetInches = robotPrefs.getDouble("gearCam",0);

	}	

}
