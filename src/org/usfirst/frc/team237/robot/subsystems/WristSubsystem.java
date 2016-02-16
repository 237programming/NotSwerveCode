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
    	
    	private Talon intake;
    	private CANTalon rotateWrist;
    	private DigitalInput limitWrist;
    	
    // Initialize your subsystem here
    public WristSubsystem() {
    	
    	intake = new Talon(RobotMap.DriveMap.intake);
    	rotateWrist = new CANTalon(RobotMap.DriveMap.rotateWrist);
    	rotateWrist.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	rotateWrist.setPID(0.3, 0.0, 0.0);
    	rotateWrist.changeControlMode(CANTalon.TalonControlMode.Position);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    }
    public void raiseWrist(){
    	rotateWrist.set(RobotMap.ArmMap.wristPositiveSpeed);
    }
    public void lowerWrist(){
    	rotateWrist.set(RobotMap.ArmMap.wristNegativeSpeed);
    }
    public void stopWrist(){
    	rotateWrist.set(0);
    }
    public void inTake(){
    	intake.set(1);
    }
    public void outTake(){
    	intake.set(-1);
    }
    public void stopTake(){
    	intake.set(0);
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
    	rotateWrist.setSetpoint(rotateWrist.getPosition());
    	rotateWrist.changeControlMode(CANTalon.TalonControlMode.Position);
    	rotateWrist.enable();
    }
    
    public void disableWrist(){
    	
    	rotateWrist.disable();
    	rotateWrist.changeControlMode(CANTalon.TalonControlMode.Voltage);
    	
    }
    public void set(double speed){
    	disableWrist();
    	rotateWrist.set(speed);
    }
    
}