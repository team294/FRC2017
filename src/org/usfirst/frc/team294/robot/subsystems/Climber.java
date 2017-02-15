package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	private final CANTalon climbMotor1 = new CANTalon(RobotMap.climbMotor1);
	private final CANTalon climbMotor2 = new CANTalon(RobotMap.climbMotor2);
	
	public Climber(){
		super();
		climbMotor2.changeControlMode(TalonControlMode.Follower);
	}
	
	public void climberSetToSpeed(double speed){
		if(Math.abs(speed) > 1.0) speed = 1.0 * Math.signum(speed);
		climbMotor1.set(speed);
		climbMotor2.set(speed);
	}
	
	public void stopClimber(){
		climbMotor1.set(0);
		climbMotor2.set(0);
	}
	
	public void driveClimberWithJoystick(){
        climbMotor1.changeControlMode(TalonControlMode.PercentVbus);    	
        climbMotor1.configPeakOutputVoltage(+12.0f, -12.0f);
        climbMotor2.configPeakOutputVoltage(+12.0f, -12.0f);
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

