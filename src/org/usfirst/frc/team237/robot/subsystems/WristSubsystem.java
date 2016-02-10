package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class WristSubsystem extends Subsystem {
    	
    	private Talon leftIntake;
    	private Talon rightIntake;
    	private CANTalon rotateWrist;
    	private DigitalInput limitWrist;
    	
    // Initialize your subsystem here
    public WristSubsystem() {
    	
    	leftIntake = new Talon(RobotMap.DriveMap.leftIntake);
    	rightIntake = new Talon(RobotMap.DriveMap.rightIntake);
    	rotateWrist = new CANTalon(RobotMap.DriveMap.rotateWrist);
    	rotateWrist.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void setWristPosition(double angle) {
    	rotateWrist.setSetpoint(angle);	
    }
    
    public void enableWrist(){
    rotateWrist.enable();
    }
    
    public void disableWrist(){
    rotateWrist.disable();
    }
    
    protected double returnPIDInput() {
        // Return your input value for the PID loop
        // e.g. a sensor, like a potentiometer:
        // yourPot.getAverageVoltage() / kYourMaxVoltage;
    	return 0.0;
    }
    
    protected void usePIDOutput(double output) {
        // Use output to drive your system, like a motor
        // e.g. yourMotor.set(output);
    }
}
