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
	    	 if (j == 3) i.whenPressed(new GyroTurnToAngle(0.3, 180));
	    	 else if (j == 4) i.whenPressed(new GyroTurnToAngle(0.3, 0));
	    	 else if (j == 5) i.whenPressed(new GyroTurnToAngle(0.3, -90));
	    	 else if (j == 6) i.whenPressed(new GyroTurnToAngle(0.3, 90.0));
	    	 else i.whenPressed(new ShiftDown());
	     }
	     
	     // Declare right joystick buttons and set them to shift up
	     for (Button i : right) {
	    	 i = new JoystickButton(rightJoystick, k++);
	    	 if (k > 2 && k < 7) i.whenPressed(new DriveWithJoysticks()); 
	    	 else i.whenPressed(new ShiftUp());
	     }
	     
	     //double speed = SmartDashboard.getDouble("Drive Speed");
	     //double curve = SmartDashboard.getDouble("Drive Curve");
	     SmartDashboard.putNumber("Drive Speed", 0.0);
	     SmartDashboard.putNumber("Drive Curve", 0.0);
	     SmartDashboard.putData("Drive at Angle", new DriveAtAngleFromSmartDashboard());
	     SmartDashboard.putData("Start Vision Alignment", new TurnToAngle(0));
	     SmartDashboard.putData("Drive 10 feet", new DriveWithEncoders(10));
	     
	     SmartDashboard.putNumber("Drive Forward Speed", 0.0);
	     SmartDashboard.putData("Drive 50%", new DriveForward(.5));
	     SmartDashboard.putData("Drive Stop", new DriveStop());
	     
	     SmartDashboard.putData("Turn to 90", new GyroTurnToAngle(0.4, 90));
	     SmartDashboard.putData("Turn to -90", new GyroTurnToAngle(0.4, -90));
	     SmartDashboard.putData("Turn to 180", new GyroTurnToAngle(0.4, 180));
	     SmartDashboard.putData("Turn to 0", new GyroTurnToAngle(0.4, 0));
	     
	     // Testing for various susbsytems
	     SmartDashboard.putData("Gear Piston Out", new SetGearSolenoid(true));
	     SmartDashboard.putData("Gear Piston In", new SetGearSolenoid(false));
	     SmartDashboard.putData("Stop Intake Motor", new IntakeSetToSpeed(0.0));
	     SmartDashboard.putData("Start Intake Motor", new IntakeSetToSpeed(0.5));
	     SmartDashboard.putData("Stop Shooter Motor", new ShooterSetToSpeed(0.0));
	     SmartDashboard.putData("Start Shooter Motor", new ShooterSetToSpeed(0.3));
	}
}
