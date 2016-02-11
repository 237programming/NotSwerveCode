package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class ArmSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public CANTalon jointTalon;
	public CANTalon extensionTalon;
	
	public ArmSubsystem()
	{
		jointTalon = new CANTalon(RobotMap.ArmMap.jointTalon);
		extensionTalon = new CANTalon(RobotMap.ArmMap.extensionTalon);
		
		jointTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		extensionTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
		
		extensionTalon.setPID(1, 0, 0);
		jointTalon.setPID(1, 0, 0);
	}
	
	public void setAngle(double angle) {
		jointTalon.setSetpoint(angle);
	}
	public void setExtension(double distance) {
		extensionTalon.setSetpoint(distance);
	}
	
	public void jointEnable() {
		jointTalon.enable();
	}
	public void extensionEnable() {
		extensionTalon.enable();
	}
	
	public void jointDisable() {
		jointTalon.disable();
	}
	public void extensionDisable() {
		extensionTalon.disable();
	}
	
	public void angleArmUp() {
		jointDisable();
		jointTalon.set(RobotMap.ArmMap.manualAngle);
	}
	public void angleArmDown() {
		jointDisable();
		jointTalon.set(RobotMap.ArmMap.manualAngle * -1.0);
	}
	public void extendArm() {
		extensionDisable();
		extensionTalon.set(RobotMap.ArmMap.manualExtension);
	}
	public void retractArm() {
		extensionDisable();
		extensionTalon.set(RobotMap.ArmMap.manualExtension * -1.0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

