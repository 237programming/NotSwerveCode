package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.NetTablesPIDSource;
import org.usfirst.frc.team237.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ArmSubsystem extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public CANTalon jointTalon;
	public CANTalon extensionTalon;
	private PIDController anglePID;
	private AHRS gyro;
	private NetTablesPIDSource visionYSrc;
	
	public ArmSubsystem(AHRS g)
	{
		visionYSrc = new NetTablesPIDSource();
		visionYSrc.setDirection(NetTablesPIDSource.direction.y);
		gyro = g;
		anglePID = new PIDController(RobotMap.ArmMap.armRotationP, 
									 RobotMap.ArmMap.armRotationI, 
									 RobotMap.ArmMap.armRotationD,
									 gyro, jointTalon);
		
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
	
	public void setAHRS(AHRS g) {
		gyro = g;
	}
	public double calcSetPoint(double opposite){
		double val = Math.toDegrees(Math.atan((opposite/RobotMap.DriveMap.pixelPerFoot)/RobotMap.DriveMap.adjacentLength));
		return gyro.pidGet()+val;
	}
    public void searchTarget() {
    	double yLocation = visionYSrc.pidGet(); 
    	double setPoint = calcSetPoint(yLocation-RobotMap.DriveMap.centerScreenY);
    	SmartDashboard.putNumber("Target Vertical", setPoint);
    	jointTalon.setSetpoint(setPoint);
    }
    
	public void visionStart(){
		this.anglePID.initTable(NetworkTable.getTable("PID/Vertical PID"));
		this.anglePID.disable();
			this.anglePID.enable();
			//this.horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
			//this.horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
			this.searchTarget();
	}
	public void visionStop(){
		this.anglePID.disable();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

