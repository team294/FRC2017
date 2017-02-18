package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

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

	// Start the command when the button is released  and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
	
	//Threshold position checker
	//For top and bottom knobs unless otherwise specified
	double[] knobThreshold=new double[]{-0.911,-0.731,-0.551,-0.367,-0.1835,-0.0035,0.1775,0.3605,0.5455,0.7285,0.91};
	//For middle knob only
	double[] middleKnobThreshold=new double[] {-0.751,-0.25,0.2525,0.7525};

	// Create the positions for the knobs
	// These can be renamed once uses are decided
	public enum TopKnob { 
		PositionOne,PositionTwo,PositionThree,PositionFour,PositionFive,PositionSix,
		PositionSeven,PositionEight,PositionNine,PositionTen,PositionEleven,PositionTwelve }
	
	public enum MiddleKnob {
		PositionOne,PositionTwo,PositionThree,PositionFour,PositionFive }
	
	public enum BottomKnob {
		PositionOne,PositionTwo,PositionThree,PositionFour,PositionFive,PositionSix,
		PositionSeven,PositionEight,PositionNine,PositionTen,PositionEleven,PositionTwelve }
	
	// Make the knob positions from above into an array for each knob
	TopKnob[] TopKnobPositions = new TopKnob[] {
			TopKnob.PositionOne,TopKnob.PositionTwo,TopKnob.PositionThree,TopKnob.PositionFour,
			TopKnob.PositionFive,TopKnob.PositionSix,TopKnob.PositionSeven,TopKnob.PositionEight,
			TopKnob.PositionNine,TopKnob.PositionTen,TopKnob.PositionEleven,TopKnob.PositionTwelve
	};
	
	MiddleKnob[] MiddleKnobPositions = new MiddleKnob[] {
			MiddleKnob.PositionOne, MiddleKnob.PositionTwo,MiddleKnob.PositionThree,MiddleKnob.PositionFour,MiddleKnob.PositionFive
	};
	
	BottomKnob[] BottomKnobPositions = new BottomKnob[] {
			BottomKnob.PositionOne,BottomKnob.PositionTwo,BottomKnob.PositionThree,BottomKnob.PositionFour,
			BottomKnob.PositionFive,BottomKnob.PositionSix,BottomKnob.PositionSeven,BottomKnob.PositionEight,
			BottomKnob.PositionNine,BottomKnob.PositionTen,BottomKnob.PositionEleven,BottomKnob.PositionTwelve
	};
	
	// Create arrays to hold the commands assigned to the knob positions. 
	// For any knobs that aren't associated with commands, can remove the corresponding array
	Command[] TopKnobCommands = new Command[] {
			// Declare commands for the top knob here
	};
	
	Command[] MiddleKnobCommands = new Command[] {
			// Declare commands for the middle knob here
	};
	
	Command[] BottomKnobCommands = new Command[] {
			// Declare commands for the bottom knob here
	};
	
	// Joysticks
	public Joystick leftJoystick = new Joystick(0);
	public Joystick rightJoystick = new Joystick(1);
	public Joystick coPanel = new Joystick(2);
	
	public Button[] left = new Button[12];
    public Button[] right = new Button[12];
    public Button[] coP =  new Button[15];
	
	public OI() {
		
		// Create button arrays for the input devices
		Button[] left = new Button[12];
	    Button[] right = new Button[12];
	    Button[] coP =  new Button[15];
	    	    
	    // Declare left and right joystick buttons
	    for (int i = 1; i < left.length; i++) {
	    	left[i] = new JoystickButton(leftJoystick, i);
	    	left[i].whenPressed(new ShiftUp());
	    	right[i] = new JoystickButton(rightJoystick, i);
	    	right[i].whenPressed(new ShiftUp());
	    }
	    
	    // Declare codriver panel switches
	    for (int i = 1; i < coP.length; i++) {
	    	coP[i] = new JoystickButton(coPanel, i);
		}
	    
	    /*
	     * Commented until we start using the practice bot
	    // Bind commands to the codriver panel switches
	    coP[1].whenPressed(new ConveyerSetToSpeed(1.0)); // Need to update all numbers to voltage instead of Vbus
	    coP[1].whenReleased(new ConveyerSetToSpeed(0.0));
	    coP[2].whenPressed(new ShooterSetToSpeed(1.0)); // This will likely change according to the position of the shooter hood. A new command will be required
	    coP[3].whenPressed(new ConveyerSetToSpeed(-1.0));
	    coP[3].whenReleased(new ConveyerSetToSpeed(0.0));
	    //coP[4].whenPressed(new PrepareToClimb()); // Command does not yet exist
	    coP[5].whenPressed(new ClimbSetToSpeed(1.0)); // This will likely change according to measured values on the robot
	    coP[6].whenPressed(new MoveShooterHood(true));
	    coP[7].whenPressed(new MoveShooterHood(false));
	    //coP[8].whenPressed(new stowIntakeAndHopper()); // Command does not yet exist
	    coP[9].whenPressed(new IntakeSetToSpeed(-1.0));
	    //coP[10].whenPressed(new deployIntakeAndHopper()); // Command does not yet exist
	    coP[11].whenPressed(new IntakeSetToSpeed(1.0));
	    coP[12].whenPressed(new StopAllMotors());
	    coP[13].whenPressed(new MoveGearGate(false));
	    coP[14].whenPressed(new MoveGearGate(true));
		*/
		
	    // Gyro Testing Commands
	    SmartDashboard.putData("Turn to 90", new GyroTurnToAngle(0.4, 90));
	    SmartDashboard.putData("Turn to -90", new GyroTurnToAngle(0.4, -90));
	    SmartDashboard.putData("Turn to 180", new GyroTurnToAngle(0.4, 180));
	    SmartDashboard.putData("Turn to 0", new GyroTurnToAngle(0.4, 0));
	    SmartDashboard.putData("Turn to gear", new GyroTurnToAngle(0.4, 0, 3.0, GyroTurnToAngle.TurnMode.GEAR_VISION));
	     
	    // Subsystem Testing Commands
	    SmartDashboard.putData("Gear Piston Out", new MoveGearGate(true));
	    SmartDashboard.putData("Gear Piston In", new MoveGearGate(false));
	    SmartDashboard.putData("Stop Intake Motor", new IntakeSetToSpeed(0.0));
	    SmartDashboard.putData("Start Intake Motor", new IntakeSetToSpeed(0.5));
	    
	    // Autonomous Command Testing
	    SmartDashboard.putData("Autonomous Gear Left", new AutoDriveAndGearLeft());
	    SmartDashboard.putData("Autonomous Gear Right", new AutoDriveAndGearRight());
	    SmartDashboard.putData("Autonomous Gear Middle", new AutoDriveAndGearMiddle());
	    
	    //  Shooter controls
	    SmartDashboard.putData("Set Shooter RPM", new ShooterSetToRPMFromSmartDashboard());
	    SmartDashboard.putData("Shooter Motor Voltage", new ShooterSetVoltageFromSmartDashboard());    
	    SmartDashboard.putData("Set Shooter PIDF values", new ShooterSetPIDF(0));
	    SmartDashboard.putData("Stop Shooter Motor", new ShooterSetVoltage(0.0));
	    SmartDashboard.putData("Start BallFeed", new ConveyorSetVoltage(7.5));   //  Pass the voltage to vertical conveyor
	    SmartDashboard.putData("Stop BallFeed", new ConveyorSetVoltage(0.0)); 
	    
	    // Encoders
	    SmartDashboard.putNumber("Left Encoder Raw", Robot.driveTrain.getLeftEncoderRaw());
	    SmartDashboard.putNumber("Right Encoder Raw", Robot.driveTrain.getRightEncoderRaw());
	    
	    // Stop Command
	    SmartDashboard.putData("Drive Stop", new DriveStop());
	    
	}
	
	/**
	 * Reads the top knob
	 * @return Raw position 0 (full ccw) to 11 (full cw)
	 */
	public int readTopKnobRaw() {
		double knobReading;
		int i = 0;

		knobReading = coPanel.getRawAxis(4);
		int len = knobThreshold.length;
		for (i = 0; i < len; i++) {
			if (knobReading < knobThreshold[i]) break;
		}
        
		if (i == len) return len - 1;
		return i;
	}
	
	/**
	 * Reads the top knob
	 * @return OI.TopKnobPositions
	 */
	public TopKnob readTopKnob() {
		return TopKnobPositions[readTopKnobRaw()];
	}

	     /// Stop Command
	     SmartDashboard.putData("Drive Stop", new DriveStop());
	     
	     
	     SmartDashboard.putData("Ultron WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW", new GetDistance ());
	     SmartDashboard.putData("Is Gear There?", new IsGearThere());
	    // SmartDashboard.getDouble("GetDistance");
	    // SmartDashboard.("GetDistance");
	    // SmartDashboard.getData("GetDistance");

	/**
	 * Reads the middle knob.
	 * @return Raw position 0 (full ccw) to 4 (cw).  Positions above 4 are indeterminate due to resistors missing.
	 */
	public int readMiddleKnobRaw() {
		double knobReading2;
		int i = 0;
		
		knobReading2 = coPanel.getRawAxis(6);
		int len = middleKnobThreshold.length;
		for(i = 0; i < len; i++) {
			if (knobReading2 < middleKnobThreshold[i]) break;
		}
		
		return i;
	}
	
	/**
	 * Reads the middle knob.
	 * @return OI.MiddleKnobPositions
	 */
	public MiddleKnob readMiddleKnob(){
		return MiddleKnobPositions[readMiddleKnobRaw()];
	}

	/**
	 * Reads the bottom knob.
	 * @return Raw position 0 (full ccw) to 11 (full cw)
	 */
	public int readBottomKnobRaw() {
		double knobReading;
		int i = 0;
		
		knobReading = coPanel.getRawAxis(3);
		int len = knobThreshold.length;
		for(i = 0; i < len; i++) {
			if (knobReading < knobThreshold[i]) break;
		}
        
		if (i == len) return len - 1;
		return i;
	}

	/**
	 * Reads the bottom knob.
	 * @return OI.BottomKnobPositions
	 */
	public BottomKnob readBottomKnob() {
		return BottomKnobPositions[readBottomKnobRaw()];
	}
	
	// The getMiddleKnobCommand() can be replicated for other knobs that need to be read for commands
	
	/**
	 * Get the command based on the position of the middle knob 
	 * @return Command
	 */
	public Command getMiddleKnobCommand() {
		int i = readMiddleKnobRaw();
		if (i < MiddleKnobCommands.length) {
			return MiddleKnobCommands[i];
		}
		return null;		
	}
}
