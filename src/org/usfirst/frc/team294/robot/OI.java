package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	// Joysticks
	public Joystick leftJoystick = new Joystick(0);
	public Joystick rightJoystick = new Joystick(1);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	public OI() {
		Button[] left = new Button[12];
	    Button[] right = new Button[12];
	    int j = 1;
	    int k = 1;
	    
	    // Declare left joystick buttons and set them to shift down
	     for (Button i : left) {
	    	 i =  new JoystickButton(leftJoystick, j++);
	    	 i.whenPressed(new ShiftDown());
	     }
	     
	     // Declare right joystick buttons and set them to shift up
	     for (Button i : right) {
	    	 i = new JoystickButton(rightJoystick, k++);
	    	 i.whenPressed(new ShiftUp());
	     }
	     
	     // DriveBySmartDashboard
	     SmartDashboard.putNumber("Drive: speed input", 0.0);
	     SmartDashboard.putNumber("Drive: curve input", 0.0);
	     SmartDashboard.putNumber("Drive: duration input", 0.0);
	     SmartDashboard.putData("Drive from inputs", new DriveAtAngleFromSmartDashboard());
	     
	     // Debug output
	     SmartDashboard.putNumber("driveTrain set speed", 0.0);

	     // Simple drive commands
	     SmartDashboard.putData("Drive 50% 1 sec", new DriveForward(0.5, 0.0, 1.0));
	     SmartDashboard.putData("Drive Stop", new DriveStop());
	}
	
	public void updateSmartDashboard() {
		
	}
}
