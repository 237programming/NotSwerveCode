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
	public CANTalon slaveExtension;
	private PIDController anglePID;
	private AHRS gyro;
	private NetTablesPIDSource visionYSrc;
	private double jointTolerance = 0.5;
	private double extensionTolerance = 5.0;
	private double extensionForwardLimit = 595; 
	public ArmSubsystem(AHRS g)
	{
		jointTalon = new CANTalon(RobotMap.ArmMap.jointTalon);
		visionYSrc = new NetTablesPIDSource();
		visionYSrc.setDirection(NetTablesPIDSource.direction.y);
		gyro = g;
		anglePID = new PIDController(RobotMap.ArmMap.armRotationP, 
									 RobotMap.ArmMap.armRotationI, 
									 RobotMap.ArmMap.armRotationD,
									 gyro, jointTalon);
		
		extensionTalon = new CANTalon(RobotMap.ArmMap.extensionTalon);
		slaveExtension = new CANTalon(RobotMap.ArmMap.slaveExtension);
		slaveExtension.changeControlMode(CANTalon.TalonControlMode.Follower);
		slaveExtension.set(RobotMap.ArmMap.extensionTalon);
		
		jointTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Absolute);
		jointTalon.setPosition(237000);
		extensionTalon.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
		jointTalon.reverseOutput(false);
		extensionTalon.setPID(-0.5, 0, 0);
		jointTalon.setPID(0.5, 0.0, 0);
		
		jointTalon.ConfigFwdLimitSwitchNormallyOpen(true);
		jointTalon.ConfigRevLimitSwitchNormallyOpen(true);
		extensionTalon.reverseSensor(true);
		extensionTalon.ConfigFwdLimitSwitchNormallyOpen(true);
		extensionTalon.ConfigRevLimitSwitchNormallyOpen(true);
		
		//jointTalon.setForwardSoftLimit(-35000);
		
	}
	
	public void setAngle(double angle) {
		if (angle < -40) {
    		angle = -40;
    	}
		jointTalon.setSetpoint(angle);
	}
	public void setExtensionDistance(double distance) {
		if (distance >= extensionForwardLimit) distance = extensionForwardLimit-10;
		extensionTalon.setSetpoint(distance);
	}
	
	public void jointEnable() {		
		jointTalon.changeControlMode(CANTalon.TalonControlMode.Position);
		jointTalon.setSetpoint(jointTalon.getPosition());
		jointTalon.enable();
	}
	public void extensionEnable() {
		extensionTalon.setSetpoint(extensionTalon.getPosition());
		extensionTalon.changeControlMode(CANTalon.TalonControlMode.Position);
		extensionTalon.enable();
	}
	public void jointDisable() {
		jointTalon.disable();
		jointTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		jointTalon.enable();
		
	}
	public void extensionDisable() {
		extensionTalon.disable();
		extensionTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		extensionTalon.enable();
	}
	
	public void angleArmUp() {
		//jointDisable();
		jointTalon.set(RobotMap.ArmMap.manualAngle);
	}
	public void angleArmDown() {
		//jointDisable();
		if (jointTalon.getPosition() <= -40){
			jointTalon.set(0);
		} else {
			jointTalon.set(RobotMap.ArmMap.manualAngle * -1.0);
		}
		
		
	}
	public void extendArm() {
		//extensionDisable();
		
			extensionTalon.set(RobotMap.ArmMap.manualExtension);
		
	}
	public void retractArm() {
		//extensionDisable();
		if ( extensionTalon.getPosition() < extensionForwardLimit) {
			extensionTalon.set(RobotMap.ArmMap.manualExtension * -1.0);
		} else {
			extensionTalon.set(0.0);
		}
	}
	public void stopExtension(){
		extensionTalon.set(0.0);
	}
	public void setAHRS(AHRS g) {
		gyro = g;
	}
	public double calcSetPoint(double opposite){
		double val = Math.toDegrees(Math.atan((opposite/RobotMap.DriveMap.pixelPerFoot)/RobotMap.DriveMap.adjacentLength));
		return gyro.pidGet()+val;
	}
	public double remap(double val, double fromMin, double fromMax, double toMin, double toMax){
		return ((val - fromMin)*(toMax - toMin))/(fromMax - fromMin) + toMin;
	}
    public void searchTarget() {
    	double yLocation = visionYSrc.pidGet(); 
    	double setPoint = calcSetPoint(yLocation-RobotMap.DriveMap.centerScreenY);
    	
    	SmartDashboard.putNumber("Target Vertical", setPoint);
    	setAngle(setPoint);
    }
    
	public void visionStart(){
		//this.anglePID.initTable(NetworkTable.getTable("PID/Vertical PID"));
		//this.anglePID.enable();
		//this.horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		//this.horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
		this.searchTarget();
	}
	public void visionStop(){
		//this.anglePID.disable();
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void holdJoint(){
    	jointEnable();
    }
    public void releaseJoint(){
    	jointDisable();
    }
    public boolean onTargetJoint(){
    	if (jointTalon.get() < jointTalon.getSetpoint() + jointTolerance && jointTalon.get() > jointTalon.getSetpoint() - jointTolerance){
    		return true;
    	} else {
    		return false; 
    	}
    }
    public boolean onTargetExtension(){
    	if (extensionTalon.getPosition() < extensionTalon.getSetpoint() + extensionTolerance && extensionTalon.getPosition() > extensionTalon.getSetpoint() - extensionTolerance){
    		return true;
    	} else {
    		return false; 
    	}
    }
    public boolean isShoulderAtZero(){
    	return jointTalon.isFwdLimitSwitchClosed();
    }
    public void setShoulderEncZero(){
    	jointTalon.setPosition(0);
    }
    public boolean isExtesionAtZero(){
    	return extensionTalon.isRevLimitSwitchClosed();
    }
    public void setExtensionEncZero(){
    	extensionTalon.setPosition(0);
    }
    public void setEncHigh(){
    	jointTalon.setPosition(237000);
    }
    public void post(){
    	SmartDashboard.putNumber("Arm Extension Encoder", extensionTalon.getPosition());
    	SmartDashboard.putNumber("Arm extension setpoint", extensionTalon.getSetpoint());
    	SmartDashboard.putNumber("Arm Angle Encoder", jointTalon.getPosition());
		SmartDashboard.putNumber("Arm Angle Setpoint", jointTalon.getSetpoint());
		SmartDashboard.putBoolean("Arm Fw limit closed", jointTalon.isFwdLimitSwitchClosed());
		SmartDashboard.putBoolean("Arm Rev limit closed", jointTalon.isRevLimitSwitchClosed());
    }
}