package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;
import org.usfirst.frc.team294.robot.triggers.MotorCurrentTrigger;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Ball intake from ground
 */
public class Intake extends Subsystem {

    private final CANTalon intakeMotor = new CANTalon(RobotMap.intakeMotor);
    //intake movment stuff here
    
    //public final MotorCurrentTrigger motorCurrentTrigger = new MotorCurrentTrigger(intakeMotor, 35, 2);

    public Intake() {
    	// Call the Subsystem constructor
    	super();
    	
    	// Set up subsystem components
    	intakeMotor.setVoltageRampRate(50);

    	// Stall protection
        //motorCurrentTrigger.whenActive(new IntakeMotorStop());

    	// Add the subsystem to the LiveWindow
        LiveWindow.addActuator("Intake", "Intake Motor", intakeMotor);
        LiveWindow.addActuator("Intake", "Intake Solenoid", intakeSolenoid);
    }
    
    /**
     * Set the speed of the intake motor (rollers)
     * @param speed of the motor, between -1 (outtake) and +1 (intake), 0 = stopped
     */
    public void setSpeed(double speed) {
    	intakeMotor.set(-speed);
    }
    
    /**
     * Get the speed of the intake motor
     * @return speed between -1 (outtake) and +1 (intake). 0 = stopped
     */
    public double getSpeed() {
    	return -intakeMotor.get();
    }
    
    /**
     * Raise intake arm.
     */
    public void raiseIntake() {
    		//NEEDS CHANGES BASED ON HOW THE ARM IS DESINED
    		//intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    
    /**
     * Lower intake arm
     */
    public void lowerIntake() {
    		//NEEDS CHANGES BASED ON HOW THE ARM IS DESINED
        	//intakeSolenoid.set(DoubleSolenoid.Value.kForward);    		
    }

    /**
     * Turn off piston solenoid
     */
    public void stopPiston() {
    	intakeSolenoid.set(DoubleSolenoid.Value.kOff);
    }
    
    /**
     * Returns current solenoid setting for the intake arm.  <p>
     * <b>NOTE</b>: The intake could currently be <i>moving</i> to this position
     * and has not reached this position yet.
     * <b>NOTE</b>: The intake solenoid in the OFF state returns FALSE.
     * @return true = intake is up, false = intake is down or solenoid is off 
     */
    public boolean intakeIsUp() {
    	//ONLY WORKS IF USING SOLENOID!!!! (needs work)
    	switch (intakeSolenoid.get()) {
    	case kForward:
    		SmartDashboard.putString("Intake position", "Down");
    		break;
    	case kReverse:
    		SmartDashboard.putString("Intake position", "Up");
    		break;
    	case kOff:
    		SmartDashboard.putString("Intake position", "Off");
    		break;
    	}
    	SmartDashboard.putBoolean("Intake down sensor", intakeDownSensor.get());
//    	SmartDashboard.putBoolean("IntakeIsUp", intakeSolenoid.get()==DoubleSolenoid.Value.kReverse);
    	return intakeSolenoid.get()==DoubleSolenoid.Value.kReverse;
	//ONLY WORKS IF USING SOLENOID!!!!
    	//see 2016 code for more solenoid stuff/sensor stuff
    }
    
    /**
	 * Set up the intake controls on the SmartDashboard.  Call this once when the robot is 
	 * initialized (after the Intake subsystem is initialized).
	 */
    public void setupSmartDashboard(boolean bPIDF){
		updateSmartDashboard();
    }
 
	/**
	 * Send intake status to SmartDashboard
	 */
    public void updateSmartDashboard() {
 		SmartDashboard.putNumber("Intake motor setpoint", -intakeMotor.get());
 		SmartDashboard.putNumber("Intake motor current", intakeMotor.getOutputCurrent());
// 		SmartDashboard.putString("Intake position", intakeIsUp() ? "Up" : "Down");
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

