package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.NetTablesPIDSource;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.RobotDrive;

import com.kauailabs.navx.frc.*;

/**
 *
 */
public class SuperDrive extends Subsystem {
	private CANTalon leftMotor ;
	private CANTalon leftMotorPrime ;
	private CANTalon rightMotor;
	private CANTalon rightMotorPrime;
	// define all PID loops
	private PIDController horizontalPID;
	private PIDController horizontalNegatedPID;
	private PIDController encoderPID; 
	private PIDController yawPID; 
	private NetTablesPIDSource visionXSrc; 
	private double leftTolerance;
	private double rightTolerance;
	private AHRS gyro = new AHRS(SerialPort.Port.kMXP);
	//Define the drive
	//TankDrive drive; 
	
	boolean _onTarget = false; 
	
	String DriveName = "Drive"; 
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public SuperDrive(){
		this.leftMotor = new CANTalon(RobotMap.DriveMap.leftTalon);
		this.leftMotorPrime = new CANTalon(RobotMap.DriveMap.leftTalonPrime);
		this.rightMotor = new CANTalon(RobotMap.DriveMap.rightTalon);
		this.rightMotorPrime = new CANTalon(RobotMap.DriveMap.rightTalonPrime);
		this.visionXSrc = new NetTablesPIDSource(); 
		this.visionXSrc.setDirection(NetTablesPIDSource.direction.x);

		this.leftMotorPrime.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.leftMotorPrime.set(RobotMap.DriveMap.leftTalon);
		this.rightMotorPrime.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.rightMotorPrime.set(RobotMap.DriveMap.rightTalon);
		this.horizontalPID = new PIDController(
				RobotMap.DriveMap.horizontalP,
				RobotMap.DriveMap.horizontalI,
				RobotMap.DriveMap.horizontalD,
				this.gyro,
				this.leftMotor);
		this.horizontalNegatedPID = new PIDController(
				RobotMap.DriveMap.horizontalP*RobotMap.DriveMap.driveNegated,
				RobotMap.DriveMap.horizontalI*RobotMap.DriveMap.driveNegated,
				RobotMap.DriveMap.horizontalD*RobotMap.DriveMap.driveNegated,
				this.gyro,
				this.rightMotor);
		this.horizontalPID.setContinuous(true);
		this.horizontalNegatedPID.setContinuous(true);
		
		this.horizontalNegatedPID.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		this.horizontalPID.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		this.horizontalPID.setToleranceBuffer(1);
		this.horizontalNegatedPID.setToleranceBuffer(1);
		this.horizontalPID.setAbsoluteTolerance(20.0);
		this.horizontalNegatedPID.setAbsoluteTolerance(20.0);

		this.horizontalPID.initTable(NetworkTable.getTable("PID/Horiontal PID"));
		this.horizontalNegatedPID.initTable(NetworkTable.getTable("PID/Horiontal Negated PID"));
		//horizontalPID.startLiveWindowMode();
		//SmartDashboard.putNumber("Input", horizontalPID.getTable().getNumber("p", 0.0));
		// initialize control loops 
		//leftMotor.createTableListener()
		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    public void searchTarget() {
    	double xLocation = visionXSrc.pidGet(); 
    	double setPoint = calcSetPoint(xLocation-RobotMap.DriveMap.centerScreenX);
    	SmartDashboard.putNumber("Target Angle", setPoint);
    	horizontalPID.setSetpoint(setPoint);
    	horizontalNegatedPID.setSetpoint(setPoint);
    }
    public void setLeft(double speed) {
		speed *= RobotMap.DriveMap.driveNegated;
		//TODO: add the actual set motor speed.
		this.leftMotor.set(speed);
		//leftMotorPrime.set(speed);
	}
	
	public void setRight(double speed ) {
		speed *= RobotMap.DriveMap.driveNegated;
		//TODO: add the actual set motor speed.
		this.rightMotor.set(speed);
		//rightMotorPrime.set(speed);
	}
	
	public void set(double speedLeft, double speedRight){
		setLeft(speedLeft);
		setRight(speedRight); 
	}
	public void stop(){
		setRight(0.0);
		setLeft(0.0);
	}
	public void visionStart(){
		
		this.horizontalPID.initTable(NetworkTable.getTable("PID/Horiontal PID"));
		this.horizontalPID.enable();
		this.horizontalNegatedPID.enable();
		//this.horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		//this.horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
		this.searchTarget();
	}
	public void visionPeriodic(){
		if (this.onTarget() == true) {
			System.out.println("Target Found");
			this.horizontalPID.disable();
			this.horizontalNegatedPID.disable();
			this.stop();
		} else {
			this.horizontalPID.enable();
			this.horizontalNegatedPID.enable();
		}
		//this.searchTarget();
		SmartDashboard.putNumber("Robot Yaw", gyro.pidGet());
		//horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		//horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
	}
	public void visionStop(){
		this.horizontalPID.disable();
		this.horizontalNegatedPID.disable();
		//horizontalPID.free();
		//horizontalNegatedPID.free();
	}
	public double getError(){
		return horizontalPID.getError();
	}
	public boolean onTarget() {
		SmartDashboard.putBoolean("Left On Target", horizontalPID.onTarget());
		if(Math.abs(horizontalPID.getError()) < RobotMap.DriveMap.absTolerance) {
			return true;
		}
		return false;
	}
	public void setTolerance(double lTolerance, double rTolerance) {
		this.leftTolerance = lTolerance;
		this.rightTolerance = rTolerance;
	}
	public void resetTarget(){
		this._onTarget = false;
		
	}
	public void setAHRS(AHRS inputGyro)
	{
		gyro = inputGyro;
	}
	public double calcSetPoint(double opposite){
		double val = Math.toDegrees(Math.atan((opposite/RobotMap.DriveMap.pixelPerFoot)/RobotMap.DriveMap.adjacentLength));
		return gyro.pidGet()+val;
	}
}

