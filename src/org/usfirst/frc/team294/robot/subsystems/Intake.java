package org.usfirst.frc.team294.robot.subsystems;

import org.usfirst.frc.team294.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Ball intake from ground
 */
public class Intake extends Subsystem {

    private final CANTalon intakeMotor = new CANTalon(RobotMap.intakeMotor);
    public Intake(){
    	
    }
    private final DoubleSolenoid intakeSolenoid = new DoubleSolenoid(RobotMap.intakeSolenoidDeploy, RobotMap.intakeSolenoidStow);
    public void logIntakeStatus(){
    	
    }
    public void deployIntake(){
    	intakeSolenoid.set(DoubleSolenoid.Value.kForward);
    }
    public void stowIntake(){
    	intakeSolenoid.set(DoubleSolenoid.Value.kReverse);
    }
    public boolean isDeployed(){
    	/*if the intakeSolenoid is set to kForward then this will return true if not it will return false*/
    	return (intakeSolenoid.get() == DoubleSolenoid.Value.kForward);
    }
    /**
     * Set the speed of the intake motor
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
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

