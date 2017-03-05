package org.usfirst.frc.team294.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.Trigger;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team294.robot.Robot;
import org.usfirst.frc.team294.robot.RobotMap.StartPositions;
import org.usfirst.frc.team294.robot.commands.*;
import org.usfirst.frc.team294.robot.commands.ConveyorSetFromRobot.States;
import org.usfirst.frc.team294.robot.triggers.AxisTrigger;
import org.usfirst.frc.team294.robot.triggers.POVTrigger;

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
	
	// Threshold position checker
	// For top and bottom knobs unless otherwise specified
	double[] knobThreshold=new double[]{-0.911,-0.731,-0.551,-0.367,-0.1835,-0.0035,0.1775,0.3605,0.5455,0.7285,0.91};
	// For middle knob only
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
	public Joystick xboxController = new Joystick(3);
	
	private boolean driveDirection = true;
	
	public OI() {
		
		// Create button arrays for the input devices
		Button[] left = new Button[12];
	    Button[] right = new Button[12];
	    Button[] coP =  new Button[15];
	    Button[] xbB = new Button[11];
	    Trigger xbLT = new AxisTrigger(xboxController, 2, 0.9);
        Trigger xbRT = new AxisTrigger(xboxController, 3, 0.9);

        //TODO:  Make sure all controllers are set up to the correct commands. 
        // What does this mean? Test if the buttons call the commands indicated?

		Trigger xbPovUp = new POVTrigger(xboxController, 0);
        Trigger xbPovRight = new POVTrigger(xboxController, 90);
        Trigger xbPovDown = new POVTrigger(xboxController, 180);
        Trigger xbPovLeft = new POVTrigger(xboxController, 270);
	    	    
	    // Declare left and right joystick buttons
	    for (int i = 1; i < left.length; i++) {
	    	left[i] = new JoystickButton(leftJoystick, i);
	    	right[i] = new JoystickButton(rightJoystick, i);
	    	if (i == 3) {
	    		right[i].whenPressed(new SwitchDriveDirection(true));
	    		left[i].whenPressed(new SwitchDriveDirection(false));
	    	} else {
	    		right[i].whenPressed(new ShiftUp());
	    		left[i].whenPressed(new ShiftDown());
	    	}
	    }

	     // Declare codriver panel switches
	    for (int i = 1; i < coP.length; i++) {
	    	coP[i] = new JoystickButton(coPanel, i);
		}
	    
	    // Xbox controller buttons
	    for (int i = 1; i < xbB.length; i++) {
	    	xbB[i] = new JoystickButton(xboxController, i);
	    }
	    
	    //TODO:  Need control panel buttons for hopper and intake solenoid movements
	    // We need to determine whether the "emergency" commands will be on the panel or the SmartDashboard
	    // Which is dependent on the primary control the codriver uses (Xbox or panel), so we can't yet
	    // Determine whether we need to add individual solenoid movements to the panel.
	    
	    // Bind commands to the codriver panel switches
	    coP[1].whenPressed(new StopAllMotors());
//	    coP[2].whenPressed(new PrepareToClimb());
	    coP[2].whenPressed(new ClimbSequenceStart());
	    //coP[3].whenPressed(new StartManualClimbControl());
	    coP[4].whenPressed(new ShooterSetRPM(Robot.shootSpeedLowRPM));
	    coP[5].whenPressed(new ShooterSetRPM(Robot.shootSpeedHighRPM));
	    coP[6].whenPressed(new ConveyorSetFromRobot(States.in));
	    coP[6].whenReleased(new ConveyorSetFromRobot(States.stopped));
	    coP[7].whenPressed(new ConveyorSetFromRobot(States.out));
	    coP[7].whenReleased(new ConveyorSetFromRobot(States.stopped));
	    coP[8].whenPressed(new MoveGearGate(false));
	    coP[9].whenPressed(new IntakeSetToSpeed(-Robot.intakeSpeed));
	    coP[10].whenPressed(new MoveGearGate(true));
	    coP[11].whenPressed(new IntakeSetToSpeed(Robot.intakeSpeed));
	    coP[12].whenPressed(new DeployIntakeAndHopper());
//	    coP[13].whenPressed(new MoveShooterHood(false));
//	    coP[14].whenPressed(new MoveShooterHood(true));
	    coP[13].whenPressed(new DeployIntakeAndHopper()); //for testing can be reset when we get a shooter hood
	    coP[14].whenPressed(new StowIntakeAndHopper()); //for testing can be reset when we get a shooter hood
	    
	    // Xbox controller buttons
	    xbB[1].whenPressed(new MoveShooterHood(false));
	    xbB[2].whenPressed(new MoveGearGate(true));
	    xbB[3].whenPressed(new MoveGearGate(false));
	    xbB[4].whenPressed(new MoveShooterHood(true));
	    xbB[5].whenPressed(new IntakeSetToSpeed(Robot.intakeSpeed));
	    xbB[6].whenPressed(new IntakeSetToSpeed(-Robot.intakeSpeed));
	    xbB[7].whenPressed(new ClimbSequenceStart());
	    xbB[8].whenPressed(new DeployIntakeAndHopper());
	    xbB[9].whenPressed(new StopAllMotors());
	    //xbB[10].whenPressed(new StartManualClimbControl()); //Command does not yet exist
	    
	    xbPovUp.whenActive(new ShooterSetRPM(Robot.shootSpeedHighRPM));
	    xbPovDown.whenActive(new ShooterSetRPM(Robot.shootSpeedLowRPM));
	    xbPovLeft.whenActive(new ShooterSetRPM(Robot.shootSpeedLowRPM));
	    xbPovRight.whenActive(new ShooterSetRPM(Robot.shootSpeedHighRPM));
	    
	    // Xbox triggers
	    xbLT.whenActive(new ConveyorSetFromRobot(States.out)); // This runs the conveyors out. The number is subject to change.
	    xbLT.whenInactive(new ConveyorSetFromRobot(States.stopped));
	    xbRT.whenActive(new ConveyorSetFromRobot(States.in));
	    xbRT.whenInactive(new ConveyorSetFromRobot(States.stopped));
			     
	    // Smart Dashboard Commands
	    
	    //Debug mode
		SmartDashboard.putData("Debug Dashboard", new SmartDashboardDebug());
	    
	    // Subsystem Testing Commands
	    SmartDashboard.putData("Gear Piston Out", new MoveGearGate(true));
	    SmartDashboard.putData("Gear Piston In", new MoveGearGate(false));
	    SmartDashboard.putData("Stop Intake Motor", new IntakeSetToSpeed(0.0));
	    SmartDashboard.putData("Start Intake Motor", new IntakeSetToSpeed(Robot.intakeSpeed));

	    
	    // Climb Motor Tests
	    SmartDashboard.putData("Start Climb Motor", new ClimbSetToSpeed(0.4));
	    SmartDashboard.putData("Stop Climb Motor", new ClimbSetToSpeed(0.0));
	    SmartDashboard.putData("Start Climb Sequence", new ClimbSequenceStart());
	    
	    // Intake and Hopper Tests
	    SmartDashboard.putData("Deploy Intake", new MoveIntakeIfSafe(true));
	    SmartDashboard.putData("Stow Intake", new MoveIntakeIfSafe(false));
	    SmartDashboard.putData("Deploy Hopper", new MoveHopperIfSafe(true));
	    SmartDashboard.putData("Stow Hopper", new MoveHopperIfSafe(false)); 
	    SmartDashboard.putData("Deploy Intake and Hopper", new DeployIntakeAndHopper());
	    
	    // Autonomous Command Testing
	    SmartDashboard.putData("Autonomous Gear Left", new AutoDriveAndGear(StartPositions.left));
	    SmartDashboard.putData("Autonomous Gear Right", new AutoDriveAndGear(StartPositions.right));
	    SmartDashboard.putData("Autonomous Gear Middle", new AutoDriveAndGear(StartPositions.middle)); 
	    
	    // Shooter controls
	    SmartDashboard.putData("Set Shooter RPM Low", new ShooterSetToRPMFromSmartDashboardLow());
	    SmartDashboard.putData("Set Shooter RPM High", new ShooterSetToRPMFromSmartDashboardHigh());
	    SmartDashboard.putData("Shooter Motor Voltage", new ShooterSetVoltageFromSmartDashboard());    
		SmartDashboard.putData("Set Shooter PIDF values", new ShooterSetPIDF(0));
		SmartDashboard.putData("Stop Shooter Motor", new ShooterSetVoltage(0.0));
		    
		// Encoders
		Robot.driveTrain.updateSmartDashboardEncoders();
		    
		// Stop Command
		SmartDashboard.putData("Drive Stop", new DriveStop());	
		 
		// Conveyor Changes
		SmartDashboard.putData("Conveyors In", new ConveyorSetFromRobot(States.in));
		SmartDashboard.putData("Conveyors Out", new ConveyorSetFromRobot(States.out));
		SmartDashboard.putData("Conveyors Stopped", new ConveyorSetFromRobot(States.stopped));
	    
	    // Gyro Testing Commands 
/*	    SmartDashboard.putData("Turn to 90", new GyroTurnToAngle(0.4, 90, 2.0));
	    SmartDashboard.putData("Turn to -90", new GyroTurnToAngle(0.4, -90, 2.0));
	    SmartDashboard.putData("Turn to 180", new GyroTurnToAngle(0.4, 180, 2.0));
	    SmartDashboard.putData("Turn to 5", new GyroTurnToAngle(0.4, 5, 2.0));
	    SmartDashboard.putData("Turn to -5", new GyroTurnToAngle(0.4, -5, 2.0));
	    SmartDashboard.putData("Turn to 10", new GyroTurnToAngle(0.4, 10, 2.0));
	    SmartDashboard.putData("Turn to -10", new GyroTurnToAngle(0.4, -10, 2.0));
		SmartDashboard.putData("Turn to 0", new GyroTurnToAngle(0.4, 0));
*/	    
	    // DriveStraightDistance tests
	    SmartDashboard.putData("Drive Straight Distance", new DriveStraightDistance(0.4, 0.0, DriveStraightDistance.DriveMode.SMARTDASHBOARD, DriveStraightDistance.Units.inches));
	    SmartDashboard.putNumber("DriveSpeed", 0);
	    SmartDashboard.putNumber("Distance", 0);
//	    SmartDashboard.putNumber("BoilerDistance", 0);
//	    SmartDashboard.putNumber("UltrasonicDistance", 0);
//	    SmartDashboard.putData("Drive to Boiler_SmartDashboard", new DriveStraightDistance(0.4, 0.0, DriveStraightDistance.DriveMode.BOILER_SMARTDASHBOARD, DriveStraightDistance.Units.inches));
//	    SmartDashboard.putData("Drive 12 inches", new DriveStraightDistance(0.4, -12.0, DriveStraightDistance.DriveMode.RELATIVE, DriveStraightDistance.Units.inches));
//	    SmartDashboard.putData("Drive to Ultraonic", new DriveStraightDistance(0.4, 0.0, DriveStraightDistance.DriveMode.ULTRASONIC, DriveStraightDistance.Units.inches));
//	    SmartDashboard.putData("Drive to Ultrasonic_SmartDashboard", new DriveStraightDistance(0.4, 0.0, DriveStraightDistance.DriveMode.ULTRASONIC_SMARTDASHBOARD, DriveStraightDistance.Units.inches));
	    
	    if (Robot.smartDashboardDebug) {
        	setupSmartDashboardDebug();
        }
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
		} else {
			return null;
		}			
	} 
	
	/**
	 * Sets the drive direction
	 * @param direction true = gear in the front false = shooter in the front
	 */
	public void setDriveDirection(boolean direction){
		this.driveDirection = direction;
	}
	
	/**
	 * Gets the drive direction of the robot
	 * @return true for ? false for ?
	 */
	public boolean getDriveDirection(){
		return driveDirection;
	}
	public void setupSmartDashboardDebug() {
		//TODO: PUT STUFF IN HERE
	}
}
