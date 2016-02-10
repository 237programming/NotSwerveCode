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
    public void raiseWrist(){
    	rotateWrist.set(RobotMap.ArmMap.wristPositiveSpeed);
    }
    public void lowerWrist(){
    	rotateWrist.set(RobotMap.ArmMap.wirstNegativeSpeed);
    }
    public void stopWrist(){
    	rotateWrist.set(0);
    }
    public void inTake(){
    	leftIntake.set(1);
    	rightIntake.set(1);
    }
    public void outTake(){
    	leftIntake.set(-1);
    	rightIntake.set(-1);
    }
    public void stopTake(){
    	leftIntake.set(0);
    	rightIntake.set(0);
    }
    public void resetTalonEncoder(){
    	rotateWrist.setEncPosition(0);
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
    
}