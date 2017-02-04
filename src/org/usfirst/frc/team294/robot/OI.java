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
	    	 if (k > 2 && k < 7) i.whenPressed(new DriveWithJoysticks()); 
	    	 else i.whenPressed(new ShiftUp());
	     }

	     // Gyro Testing Commands
	     SmartDashboard.putData("Turn to 90", new GyroTurnToAngle(0.4, 90));
	     SmartDashboard.putData("Turn to -90", new GyroTurnToAngle(0.4, -90));
	     SmartDashboard.putData("Turn to 180", new GyroTurnToAngle(0.4, 180));
	     SmartDashboard.putData("Turn to 0", new GyroTurnToAngle(0.4, 0));
	     
	     // Subsystem Testing Commands
	     SmartDashboard.putData("Gear Piston Out", new SetGearSolenoid(true));
	     SmartDashboard.putData("Gear Piston In", new SetGearSolenoid(false));
	     SmartDashboard.putData("Stop Intake Motor", new IntakeSetToSpeed(0.0));
	     SmartDashboard.putData("Start Intake Motor", new IntakeSetToSpeed(0.5));
	     SmartDashboard.putData("Stop Shooter Motor", new ShooterSetToSpeed(0.0));
	     SmartDashboard.putData("Start Shooter Motor", new ShooterSetToSpeed(0.3));

	     // Encoders
//	     SmartDashboard.putData("Drive 10 feet", new DriveWithEncoders(10));

	     // Stop Command
	     SmartDashboard.putData("Drive Stop", new DriveStop());
	}
}
