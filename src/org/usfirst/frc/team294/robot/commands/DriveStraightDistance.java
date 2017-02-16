package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ToleranceChecker;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightDistance extends Command {

	// Available drive modes
	public enum DriveMode {
		ABSOLUTE, RELATIVE, GEAR_VISION, BOILER_VISION, ULTRASONIC, SMARTDASHBOARD, BOILER_SMARTDASHBOARD
	}
	
	public enum Units {rotations, inches};
	
	// Settings from command initialization
	private DriveMode driveMode;
	private double distance;
	private double speed;
	private Units units;

	
    // Encoder and distance settings, copied from 2016 code and robot
	private double distErr, distSpeedControl;
	private double kPdist = 3;
	private double inchesPerRevolution = 19; //will need to be changed for wheel size on 2017 robot
	private double minSpeed = 0.1;
	
    // Steering settings, also copied from 2016 code. May need to be changed
    private double angleErr, curve;
    private double kPangle = 0.018;
    
    // Expanded settings
    private boolean preciseDistance = true;
    
    private boolean success = false;
    private final double DIST_TOL = 0.0000001;
	
	private ToleranceChecker tolerance = new ToleranceChecker(DIST_TOL, 5);
	
	/**
	 * Drive the robot forward, using the NavX to adjust angle
	 * @param speed from 0 to +1.0, minimum 0.25
 	 * @param distance
	 * @param units either inches or revolutions of the encoder
     * @param drivMode :
     * <p> <b>RELATIVE</b> = (NOT SET UP YET) Reset encoders, drive <b>distance</b> <b>units</b> from current location
     * <p> <b>ABSOLUTE</b> = (NOT SET UP YET) Drive <b>distance</b> <b>units</b> from prior location zero (don't reset encoders)
     * <p> <b>GEAR_VISION</b> = Reset encoders, drive per gear camera (ignore <b>angle</b>)
     * <p> <b>BOILER_VISION</b> = Reset encoders, drive per boiler camera (ignore <b>angle</b>)
     * <p> <b>ULTRASONIC</b> = Reset encoders, drive per ultrasonic sensor (ignore <b>angle</b>)
     * <p> <b>SMARTDASHBOARD</b> = Reset encoders, drive per smartdashboard data (ignore <b>angle</b>)
     */
    public DriveStraightDistance(double speed, double distance, DriveMode driveMode, Units units) {
        requires(Robot.driveTrain);
        if (driveMode == DriveMode.GEAR_VISION) {
    		requires(Robot.gearVision);
    	}
    	if (driveMode == DriveMode.BOILER_VISION) {
    		requires(Robot.boilerVision);
    	}
        
        this.speed = Math.abs(speed);
        this.distance = (units == Units.rotations) ? distance : distance / inchesPerRevolution;
    	this.driveMode = driveMode;
    	this.units = units;
    }

    /**
     * Drive the robot forward, using the NavX to adjust angle
     * @param speed from 0 to +1, minimum 0.25
     * @param distance
     * @param units either inches or revolutions of the encoder
     * @param precise true to allow the robot to back up, false to ignore overshoot
     * @param resetEncoders true to reset encoders on start, false to leave encoders alone. False means cumulative distance
     */
    public DriveStraightDistance(double speed, double distance, Units units, boolean precise, boolean resetEncoders) {
        requires(Robot.driveTrain);
        
        this.speed = Math.abs(speed);
        this.distance = (units == Units.rotations) ? distance : distance / inchesPerRevolution;
        this.preciseDistance = precise;
        this.driveMode = resetEncoders ? DriveMode.RELATIVE : DriveMode.ABSOLUTE;
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	success = false;
    	tolerance.reset();
    	Robot.driveTrain.resetDegrees();
    	
    switch (driveMode) {
    	case ABSOLUTE:
//    		Robot.log.writeLogEcho("Gyro: Start turn to angle absolute " + angle  + " degrees, current heading " +
//    				Robot.driveTrain.getGyroAngle() + " degrees.");
    		break;
    	case RELATIVE:
    		Robot.driveTrain.resetEncoders();
    		Robot.log.writeLogEcho("Drive to target RELATIVE " + distance + " inches away.");
    		break; 
    	case GEAR_VISION:
    		Robot.driveTrain.resetEncoders();
    		distance = -Robot.gearVision.getGearDistance() / inchesPerRevolution;

    		Robot.log.writeLogEcho("Drive to target GEAR " + distance + " feet away.");
    		break;
    	case BOILER_VISION:
    		Robot.driveTrain.resetEncoders();
    		//TODO:  Add code for boiler vision.  Return value in inches!
    		distance = 0;	// Don't do anything, since boiler vision code isn't ready
        	distance = distance / inchesPerRevolution;  // Convert inches to rotations

    		Robot.log.writeLogEcho("Drive to target BOILER " + distance + " feet away.");
    		break;
    	case ULTRASONIC:
    		Robot.driveTrain.resetEncoders();
    		distance = -Robot.driveTrain.getUltrasonicDistance() / inchesPerRevolution;

    		Robot.log.writeLogEcho("Drive to target ULTRASONIC " + distance + " feet away.");
    		break;
    	case SMARTDASHBOARD:
    		Robot.driveTrain.resetEncoders();
    		distance = SmartDashboard.getNumber("Distance", 0) / inchesPerRevolution;

    		speed = SmartDashboard.getNumber("DriveSpeed", 0);
    		Robot.log.writeLogEcho("Drive to target SMARTDASHBOARD " + distance + " feet away.");
    		break;
    	case BOILER_SMARTDASHBOARD:
    		Robot.driveTrain.resetEncoders();
    		//TODO:  Fix this code
    		distance = -(Robot.boilerVision.getBoilerDistance() - (Robot.boilerVision.getBoilerDistance() - SmartDashboard.getNumber("BoilerDistance", 0))); 
        	distance = distance / inchesPerRevolution;   // Convert inches to rotations

    		speed = SmartDashboard.getNumber("DriveSpeed", 0);
    		Robot.log.writeLogEcho("Drive to target SMARTDASHBOARD " + distance + " feet away.");
    		break;
    	}    
    
    	angleErr = 0;
    	distSpeedControl = 1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	distErr = Math.min(distance - Robot.driveTrain.getLeftEncoder(), distance - Robot.driveTrain.getRightEncoder() );
    	if (preciseDistance) {
    		success = tolerance.success(Math.abs(distErr));
    	} else {
    		// A bad reading on distance error will cause the command to end prematurely
    		success = (Math.abs(distErr) < DIST_TOL);
    	}
    	
    	if (!success) {
        	// Find speed to drive
        	distSpeedControl = distErr*kPdist;
        	distSpeedControl = (distSpeedControl>1) ? 1 : distSpeedControl;
        	distSpeedControl = (distSpeedControl<-1) ? -1 : distSpeedControl;
        	distSpeedControl *= speed;
        	
        	// Use minSpeed to stay out of dead band
        	if (distSpeedControl>0) {
        		distSpeedControl = (distSpeedControl<minSpeed) ? minSpeed : distSpeedControl;
       		} else {
       			distSpeedControl = (distSpeedControl>-minSpeed) ? -minSpeed : distSpeedControl;
       		}
        	
        	// Find angle to drive
        	angleErr = Robot.driveTrain.getGyroAngle();
        	angleErr = (angleErr>180) ? angleErr-360 : angleErr;
        	angleErr = (Math.abs(angleErr) <= 10.0) ? angleErr : 0.0;		// Assume if we are more than 10 deg off then we have a NavX failure
        	
            curve = angleErr*kPangle;
        	curve = (curve>0.5) ? 0.5 : curve;
        	curve = (curve<-0.5) ? -0.5 : curve;
        	curve = (distErr>=0) ? -curve : curve; // Flip sign if we are going forwards
        	
        	Robot.driveTrain.driveAtAngle(distSpeedControl, curve);
    	}
    } 

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return success;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLogEcho("Autonomous Drive Completed: Rotations " + distance + " Inches " + distance * inchesPerRevolution);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    	Robot.log.writeLogEcho("Autonomous Drive Command Interrupted");
    }
}
