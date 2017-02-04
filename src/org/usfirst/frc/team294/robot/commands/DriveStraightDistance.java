package org.usfirst.frc.team294.robot.commands;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.utilities.ToleranceChecker;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightDistance extends Command {

	public enum Units {rotations, inches};
	
	private double distance;
	private double speed;
	
    // Encoder and distance settings, copied from 2016 code and robot
	private double distErr, distSpeedControl;
	private double kPdist = 3;
	private double inchesPerRevolution = 18.5; //will need to be changed for wheel size on 2017 robot
	private double minSpeed = 0.1;
	
    // Steering settings, also copied from 2016 code. May need to be changed
    private double angleErr, curve;
    private double kPangle = 0.018;
	
	private ToleranceChecker tolerance = new ToleranceChecker(0.5, 5);
	
    public DriveStraightDistance(double speed, double distance, Units units) {
        requires(Robot.driveTrain);
        
        this.speed = Math.abs(speed);
        this.distance = (units == Units.inches) ? distance : distance / inchesPerRevolution;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	tolerance.reset();
    	Robot.driveTrain.resetDegrees();
    	Robot.driveTrain.resetEncoders();
    	angleErr = 0;
    	distSpeedControl = 1;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	distErr = Math.min( distance - Robot.driveTrain.getLeftEncoder(), distance - Robot.driveTrain.getRightEncoder() );

    	tolerance.check(Math.abs(distErr));
    	
    	if (!tolerance.success()) {
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
        return tolerance.success();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}
