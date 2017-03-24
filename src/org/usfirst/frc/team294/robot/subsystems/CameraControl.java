package org.usfirst.frc.team294.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *
 */
public class CameraControl extends Subsystem {
	
    private final DigitalOutput cameraLightGear = new DigitalOutput(RobotMap.cameraLightGear);
    private final DigitalOutput cameraLightBoiler = new DigitalOutput(RobotMap.cameraLightBoiler);
    
    private boolean camerasOn = false;
    private boolean boilerCameraDirection = false;
    
    /**
     * Turns on the gear camera and light.  Turns off boiler light.
     */
    public void activateGearCamera() {
    	NetworkTable table;

    	cameraLightGear.set(true);
    	cameraLightBoiler.set(false);
    	
    	//Switch cameras on Raspberry Pi
		table = NetworkTable.getTable("PiReport");
		table.putString("camera", "Gear");

		camerasOn = true;
		boilerCameraDirection = false;
    }
    
    /**
     * Turns on the boiler camera and light.  Turns off gear light.
     */
    public void activateBoilerCamera() {
    	NetworkTable table;

    	cameraLightGear.set(false);
    	cameraLightBoiler.set(true);
    	
    	//Switch cameras on Raspberry Pi
		table = NetworkTable.getTable("PiReport");
		table.putString("camera", "Boiler");

		camerasOn = true;
		boilerCameraDirection = true;
    }
    
    /**
     * Turn off both camera lights
     */
    public void turnOffLights() {
    	cameraLightGear.set(false);
    	cameraLightBoiler.set(false);    	

    	camerasOn = false;
    }
    
    /**
     * Returns if either the boiler or gear camera is on
     * @return
     */
    public boolean isCamerasOn() {
    	return camerasOn;
    }
    
    /**
     * Returns direction of camera that is on (if lights are on)
     * @return true = boiler, false = gear
     */
    public boolean isBoilerCurrentDirection() {
    	return boilerCameraDirection;
    }
    
    /**
     * Sets the camera/lights on for the drive direction that is "front" 
     */
    public void setCamerasFromDriveDirection() {
    	if (Robot.oi.getDriveDirection()) {
    		// Shooter toward boiler is front
    		activateBoilerCamera();
    	} else {
    		// Gear is front
    		activateGearCamera();    		
    	}
    	
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    
    }
}

