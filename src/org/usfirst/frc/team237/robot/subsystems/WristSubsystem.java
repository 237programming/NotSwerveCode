package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class WristSubsystem extends Subsystem {
    	
    	private Talon intake;
    	private CANTalon rotateWrist;
    	private DigitalInput limitWrist;
    	private double tolerance = 0.25;
    // Initialize your subsystem here
    public WristSubsystem() {
    	
    	intake = new Talon(RobotMap.DriveMap.intake);
    	rotateWrist = new CANTalon(RobotMap.DriveMap.rotateWrist);
    	rotateWrist.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
    	rotateWrist.setForwardSoftLimit(-10.0);
    	rotateWrist.setReverseSoftLimit(1.0);
    	rotateWrist.reverseOutput(true);
    	//rotateWrist.setPID(0.125, 0.0, 0.0);
    	rotateWrist.setPID(0.08, 0, 0);
    	rotateWrist.changeControlMode(CANTalon.TalonControlMode.Position);
        // Use these to get going:
        // setSetpoint() -  Sets where the PID controller should move the system
        //                  to
        // enable() - Enables the PID controller.
    	SmartDashboard.putNumber("RotateWristEncoder", rotateWrist.getPosition());
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
    	intake.set(-1);
    }
    public void outTake(){
    	intake.set(1);
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
    	
    	rotateWrist.changeControlMode(CANTalon.TalonControlMode.Position);
    	rotateWrist.setSetpoint(rotateWrist.get());
    	rotateWrist.enable();
    }
    
    public void disableWrist(){
    	
    	rotateWrist.disable();
    	rotateWrist.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    	rotateWrist.enable();
    	
    }
    public void set(double speed){
    	rotateWrist.set(speed);
    }
    public void hold(){
    	enableWrist();
    }
    public void release(){
    	disableWrist();
    }
    public boolean onTarget(){
    	if (rotateWrist.get() < rotateWrist.getSetpoint() + tolerance && rotateWrist.get() > rotateWrist.getSetpoint() - tolerance){
    		return true;
    	} else {
    		return false; 
    	}
    }
    public void post(){
    	SmartDashboard.putNumber("wrist Setpoint", rotateWrist.getSetpoint());
    	SmartDashboard.putNumber("RotateWristEncoder", rotateWrist.getPosition());
    }
}