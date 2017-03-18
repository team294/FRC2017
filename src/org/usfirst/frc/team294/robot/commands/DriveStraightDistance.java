package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ProfileGenerator;
import org.usfirst.frc.team294.utilities.ToleranceChecker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightDistance extends Command {

	// Available drive modes
	public enum DriveMode {
		ABSOLUTE, RELATIVE, GEAR_VISION, BOILER_VISION, SMARTDASHBOARD, BOILER_SMARTDASHBOARD, MOTION_PROFILE
	}
	public ProfileGenerator trapezoid;
	// Commented-out drive modes:  ULTRASONIC, ULTRASONIC_SMARTDASHBOARD
	
	public enum Units {rotations, inches};
	
	// Settings from command initialization
	private DriveMode driveMode;
	private double distance;
	private double speed;
	
    // Encoder and distance settings, copied from 2016 code and robot
	private double distErr, distSpeedControl;
	//private double kPdist = 3; // Practice Bot
	private double kPdist = 3;
	private double kDdist = 0.0;
	private double prevSpeed = 0.0;
	private double minSpeed = 0.1;
	
    // Steering settings, also copied from 2016 code. May need to be changed
    private double angleErr, curve;
    //private double kPangle = 0.025; // Practice Bot
    private double kPangle = 0.025;
    private double kIangle = 0.0;
    private double kDangle = 0.05;
    private double intErr = 0.0;
    private double prevErr = 0.0;
    
    // Expanded settings
    private boolean preciseDistance = true;
    
    private boolean success = false;
    private double distTol = 0.5  / Robot.inchesPerRevolution; // Default tolerance in inches, converted to rotations
	
	private ToleranceChecker tolerance = new ToleranceChecker(distTol, 5);
	
	// Commented-out drive modes.
//    * <p> <b>ULTRASONIC</b> = Reset encoders, drive per ultrasonic sensor (ignore <b>distance</b>)
//    * <p> <b>ULTRASONIC_SMARTDASHBOARD</b> = Reset encoders, drive until <b>distance</b> away from boiler (ignore <b>distance</b>)
	
	/**
	 * Drive the robot forward, using the NavX to adjust angle, using default tolerance (0.5")
	 * @param speed from 0 to +1.0, minimum 0.25
 	 * @param distance in units per the "units" parameter, + = forward, - = reverse
     * @param driveMode : (DriveStraightDistance.DriveMode)
     * <li><b>RELATIVE</b> = Reset encoders, drive <b>distance</b> <b>units</b> from current location</li>
     * <li><b>ABSOLUTE</b> = Drive <b>distance</b> <b>units</b> from prior location zero (don't reset encoders)</li>
     * <li><b>GEAR_VISION</b> = Reset encoders, drive per gear camera (ignore <b>distance</b>)</li>
     * <li><b>BOILER_VISION</b> = Reset encoders, drive per boiler camera (ignore <b>distance</b>)</li>
     * <li><b>SMARTDASHBOARD</b> = Reset encoders, drive per smartdashboard data (ignore <b>distance</b> and <b>speed</b>)</li>
     * <li><b>BOILER_SMARTDASHBOARD</b> = Reset encoders, drive until <b>distance</b> away from boiler (ignore <b>distance</b> and <b>speed</b>)</li></ul>
	 * @param units either inches or revolutions of the encoder (DriveStraightDistance.Units)
 	 * @param precise true = drive to within "distance  +/- tolerance", false = drive at least to "distance - tolerance" but OK to overshoot (less accurate but faster)  
     */
    public DriveStraightDistance(double speed, double distance, DriveMode driveMode, Units units, boolean precise) {
        requires(Robot.driveTrain);
        if (driveMode == DriveMode.GEAR_VISION) {
    		requires(Robot.gearVision);
    	}
    	if (driveMode == DriveMode.BOILER_VISION) {
    		requires(Robot.boilerVision);
    	}
        
        this.speed = Math.abs(speed);
        this.distance = (units == Units.rotations) ? distance : distance / Robot.inchesPerRevolution;
    	this.driveMode = driveMode;
        this.preciseDistance = precise;
    }

	/**
	 * Drive the robot forward, using the NavX to adjust angle
	 * @param speed from 0 to +1.0, minimum 0.25
 	 * @param distance in units per the "units" parameter, + = forward, - = reverse
     * @param driveMode : (DriveStraightDistance.DriveMode)
     * <li><b>RELATIVE</b> = Reset encoders, drive <b>distance</b> <b>units</b> from current location</li>
     * <li><b>ABSOLUTE</b> = Drive <b>distance</b> <b>units</b> from prior location zero (don't reset encoders)</li>
     * <li><b>GEAR_VISION</b> = Reset encoders, drive per gear camera (ignore <b>distance</b>)</li>
     * <li><b>BOILER_VISION</b> = Reset encoders, drive per boiler camera (ignore <b>distance</b>)</li>
     * <li><b>SMARTDASHBOARD</b> = Reset encoders, drive per smartdashboard data (ignore <b>distance</b> and <b>speed</b>)</li>
     * <li><b>BOILER_SMARTDASHBOARD</b> = Reset encoders, drive until <b>distance</b> away from boiler (ignore <b>distance</b> and <b>speed</b>)</li></ul>
	 * @param units either inches or revolutions of the encoder (DriveStraightDistance.Units)
 	 * @param precise true = drive to within "distance  +/- tolerance", false = drive at least to "distance - tolerance" but OK to overshoot (less accurate but faster)  
 	 * @param tolerance in units per the "units" parameter
     */
    public DriveStraightDistance(double speed, double distance, DriveMode driveMode, Units units, boolean precise, double tolerance) {
    	this(speed, distance, driveMode, units, precise);

    	distTol = (units == Units.rotations) ? Math.abs(tolerance) : Math.abs(tolerance) / Robot.inchesPerRevolution;
    	this.tolerance.setTolerance(distTol);
    }
    
	/**
	 * Drive the robot forward, using the NavX to adjust angle, using default tolerance and precisely achieved distance within the tolerance (0.5")
	 * @param speed from 0 to +1.0, minimum 0.25
 	 * @param distance in units per the "units" parameter, + = forward, - = reverse
     * @param driveMode : (DriveStraightDistance.DriveMode) <ul>
     * <li><b>RELATIVE</b> = Reset encoders, drive <b>distance</b> <b>units</b> from current location</li>
     * <li><b>ABSOLUTE</b> = Drive <b>distance</b> <b>units</b> from prior location zero (don't reset encoders)</li>
     * <li><b>GEAR_VISION</b> = Reset encoders, drive per gear camera (ignore <b>distance</b>)</li>
     * <li><b>BOILER_VISION</b> = Reset encoders, drive per boiler camera (ignore <b>distance</b>)</li>
     * <li><b>SMARTDASHBOARD</b> = Reset encoders, drive per smartdashboard data (ignore <b>distance</b> and <b>speed</b>)</li>
     * <li><b>BOILER_SMARTDASHBOARD</b> = Reset encoders, drive until <b>distance</b> away from boiler (ignore <b>distance</b> and <b>speed</b>)</li></ul>
	 * @param units either inches or revolutions of the encoder (DriveStraightDistance.Units)
     */
    public DriveStraightDistance(double speed, double distance, DriveMode driveMode, Units units) {
    	this(speed, distance, driveMode, units, true);
    }

    /**
     * Drive the robot forward, using the NavX to adjust angle, using default tolerance (0.5")
     * @param speed from 0 to +1, minimum 0.25
 	 * @param distance in units per the "units" parameter, + = forward, - = reverse
	 * @param units either inches or revolutions of the encoder (DriveStraightDistance.Units)
 	 * @param precise true = drive to within "distance  +/- tolerance", false = drive at least to "distance - tolerance" but OK to overshoot (less accurate but faster)  
     * @param resetEncoders true to reset encoders on start, false to leave encoders alone. False means cumulative distance.
     */
    public DriveStraightDistance(double speed, double distance, Units units, boolean precise, boolean resetEncoders) {
    	this(speed, distance, resetEncoders ? DriveMode.RELATIVE : DriveMode.ABSOLUTE, units, precise);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	success = false;
    	tolerance.reset();
    	Robot.driveTrain.resetDegrees();
    	trapezoid = new ProfileGenerator(0, distance, 0, 0.75, 0.5, 0.1);
    	
    switch (driveMode) {
    	case ABSOLUTE:
    		Robot.log.writeLogEcho("Drive to target ABSOLUTE " + distance * Robot.inchesPerRevolution + " inches away.");
    		break;
    	case RELATIVE:
    		Robot.driveTrain.resetEncoders();
//    		Robot.log.writeLogEcho("Drive to target RELATIVE " + distance * Robot.inchesPerRevolution + " inches away.");
    		Robot.log.writeLogEcho("Drive to target RELATIVE " + distance * Robot.inchesPerRevolution + " inches away.");
    		break; 
    	case GEAR_VISION:
    		Robot.driveTrain.resetEncoders();
    		distance = -Robot.gearVision.getGearDistance() / Robot.inchesPerRevolution;
    		Robot.log.writeLogEcho("Drive to target GEAR " + distance * Robot.inchesPerRevolution + " inches away.");
    		break;
    	case BOILER_VISION:
    		Robot.driveTrain.resetEncoders();
    		distance = Robot.boilerVision.getBoilerDistance();
        	distance = distance / Robot.inchesPerRevolution;  // Convert inches to rotations
    		Robot.log.writeLogEcho("Drive to target BOILER " + distance * Robot.inchesPerRevolution + " inches away.");
    		break;
//    	case ULTRASONIC:
//    		Robot.driveTrain.resetEncoders();
//    		//TODO: make this like boiler john
//    		SmartDashboard.putNumber("Ultrasonic Distance", Robot.driveTrain.getUltrasonicDistance());
//    		distance = -Robot.driveTrain.getUltrasonicDistance() / Robot.inchesPerRevolution;
//    		Robot.log.writeLogEcho("Drive to target ULTRASONIC " + distance + " inches away.");
//    		break;
    	case SMARTDASHBOARD:
    		Robot.driveTrain.resetEncoders();
    		distance = SmartDashboard.getNumber("Distance", 0) / Robot.inchesPerRevolution;
    		speed = Math.abs( SmartDashboard.getNumber("DriveSpeed", 0) );
    		//tolerance = SmartDashboard.getData("DriveTolerance", 0);
    		Robot.log.writeLogEcho("Drive to target SMARTDASHBOARD " + distance * Robot.inchesPerRevolution + " inches away.");
    		break;
    	case BOILER_SMARTDASHBOARD:
    		Robot.driveTrain.resetEncoders();
    		distance = -(Robot.boilerVision.getLastBoilerDistance() - SmartDashboard.getNumber("BoilerDistance", 0)); 
        	distance = distance / Robot.inchesPerRevolution;   // Convert inches to rotations
        	//SmartDashboard.putNumber("DisToBoilerDisToBoilerDisToBoilerDisToBoilerDisToBoiler", Robot.boilerVision.getBoilerDistance());
    		speed = Math.abs( SmartDashboard.getNumber("DriveSpeed", 0) );
    		Robot.log.writeLogEcho("Drive towards target BOILER " + distance * Robot.inchesPerRevolution + " inches.");
    		break;
//    	case ULTRASONIC_SMARTDASHBOARD:
//    		Robot.driveTrain.resetEncoders();
//    		distance = -((Robot.driveTrain.getUltrasonicDistance()/ Robot.inchesPerRevolution) - SmartDashboard.getNumber("UltrasonicDistance", 0)); 
//        	//distance = distance / Robot.inchesPerRevolution;   // Convert inches to rotations
//    		speed = SmartDashboard.getNumber("DriveSpeed", 0);
//    		Robot.log.writeLogEcho("Drive towards target BOILER " + distance + " inches.");
//    		break;
    	}    
    
    	angleErr = 0;
    	distSpeedControl = 1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	distErr = Math.signum(distance)*(Math.abs(distance) - Math.abs(trapezoid.getCurrentPosition()));
    	/*distErr = (distance > 0) 
    			? Math.min(distance - Robot.driveTrain.getLeftEncoder(), distance - Robot.driveTrain.getRightEncoder() )
    			: Math.max(distance - Robot.driveTrain.getLeftEncoder(), distance - Robot.driveTrain.getRightEncoder() );*/
    	if (preciseDistance) {
    		success = tolerance.success(Math.abs(distErr));
    	} else {
    		// Note:  A bad encoder reading on distance error will cause the command to end prematurely
    		success = (Math.abs(distErr) < distTol);
    	}
    	
    	if (!success) {
        	// Find speed to drive
        	distSpeedControl = distErr*kPdist+(distErr-prevErr)*kDdist;
        	prevErr = distErr;
        	distSpeedControl = (distSpeedControl>1) ? 1 : distSpeedControl;
        	distSpeedControl = (distSpeedControl<-1) ? -1 : distSpeedControl;
        	distSpeedControl *= speed;
        	
        	// Use minSpeed to stay out of dead band
        	if (distSpeedControl>0) {
        		distSpeedControl = (distSpeedControl<minSpeed) ? minSpeed : distSpeedControl;
       		} else {
       			distSpeedControl = (distSpeedControl>-minSpeed) ? -minSpeed : distSpeedControl;
       		}
        	if(distSpeedControl > trapezoid.getCurrentVelocity()) distSpeedControl = trapezoid.getCurrentVelocity();
        	
        	// Find angle to drive
        	angleErr = Robot.driveTrain.getGyroAngle();
        	angleErr = (angleErr>180) ? angleErr-360 : angleErr;
        	angleErr = (Math.abs(angleErr) <= 10.0) ? angleErr : 0.0;		// Assume if we are more than 10 deg off then we have a NavX failure
        	intErr = intErr + angleErr*0.02;
        	double dErr = angleErr - prevErr;
        	prevErr = angleErr;
        	SmartDashboard.putNumber("Gyro dubdubdubdubdubdubdubdubdub", angleErr);
        	
        	SmartDashboard.putNumber("Integrated Error: ", intErr);
            curve = angleErr*kPangle + intErr*kIangle + dErr*kDangle;
        	curve = (curve>0.5) ? 0.5 : curve;
        	curve = (curve<-0.5) ? -0.5 : curve;
        	curve = (distErr>=0) ? -curve : curve; // Flip sign if we are going forwards
        	SmartDashboard.putNumber("drive curve wwwwwwwwwwwwwwwwww", curve);
        	
        	Robot.driveTrain.driveAtAngle(distSpeedControl, curve);
    	}
	    if (Robot.smartDashboardDebug) {
    		Robot.log.writeLog("DriveStraightDistance-Distance-DistanceError-Speed-SpeedControl-AngleError-Curve" + "\t" + distance * Robot.inchesPerRevolution + "\t" + distErr + "\t" + speed + "\t" + distSpeedControl + "\t" + angleErr + "\t" + curve);
        }
    } 

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return success;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLogEcho("Autonomous Drive Completed: Rotations " + distance + " Inches " + distance * Robot.inchesPerRevolution);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLogEcho("Autonomous Drive Command Interrupted");
    }
}
