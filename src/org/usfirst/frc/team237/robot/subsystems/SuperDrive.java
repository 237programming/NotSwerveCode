package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.NetTablesPIDSource;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Relay;
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
	private PIDController leftDrivePID;
	private PIDController rightDrivePID;
	private PIDController encoderPID; 
	private PIDController yawPID; 
	private NetTablesPIDSource visionXSrc; 
	private double leftTolerance;
	private double rightTolerance;
	private AHRS gyro;
	//Define the drive
	//TankDrive drive; 
	
	boolean _onTarget = false; 
	public enum QRDrive {
		RIGHT,
		LEFT,
		BOTH
	}
	String DriveName = "Drive"; 
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public SuperDrive(AHRS g){
		gyro = g;
		this.leftMotor = new CANTalon(RobotMap.DriveMap.leftTalon);
		this.leftMotorPrime = new CANTalon(RobotMap.DriveMap.leftTalonPrime);
		this.rightMotor = new CANTalon(RobotMap.DriveMap.rightTalon);
		this.rightMotorPrime = new CANTalon(RobotMap.DriveMap.rightTalonPrime);
		this.visionXSrc = new NetTablesPIDSource(); 
		this.visionXSrc.setDirection(NetTablesPIDSource.direction.x);
		//this.leftMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
		//this.rightMotor.changeControlMode(CANTalon.TalonControlMode.Speed);
		this.leftMotorPrime.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.leftMotorPrime.set(RobotMap.DriveMap.leftTalon);
		this.rightMotorPrime.changeControlMode(CANTalon.TalonControlMode.Follower);
		this.rightMotorPrime.set(RobotMap.DriveMap.rightTalon);
		this.leftDrivePID = new PIDController(
				RobotMap.DriveMap.driveP,
				RobotMap.DriveMap.driveI,
				RobotMap.DriveMap.driveD,
				this.gyro,
				this.leftMotor);
		this.rightDrivePID = new PIDController(
				RobotMap.DriveMap.driveP,
				RobotMap.DriveMap.driveI,
				RobotMap.DriveMap.driveD,
				this.gyro,
				this.rightMotor);
		this.leftDrivePID.setContinuous(true);
		this.rightDrivePID.setContinuous(true);
		
		this.rightDrivePID.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		this.leftDrivePID.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		this.leftDrivePID.setToleranceBuffer(1);
		this.rightDrivePID.setToleranceBuffer(1);
		this.leftDrivePID.setAbsoluteTolerance(20.0);
		this.rightDrivePID.setAbsoluteTolerance(20.0);
		leftMotor.setProfile(1);
		rightMotor.setProfile(1);
		leftMotor.setPID(0.0, 0.0, 0.0);
		rightMotor.setPID(0.0, 0.0, 0.0);
		leftMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rightMotor.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		leftMotor.setProfile(0);
		rightMotor.setProfile(0);
		leftMotor.setPID(
				RobotMap.DriveMap.driveP,
				RobotMap.DriveMap.driveI,
				RobotMap.DriveMap.driveD);
		rightMotor.setPID(
				RobotMap.DriveMap.driveP,
				RobotMap.DriveMap.driveI,
				RobotMap.DriveMap.driveD);
		
		this.leftDrivePID.initTable(NetworkTable.getTable("PID/Horiontal PID"));
		this.rightDrivePID.initTable(NetworkTable.getTable("PID/Horiontal Negated PID"));

		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public static void toggleRelay(Relay relay)
    {
    	if(relay.get() == Relay.Value.kForward)
    	{
    		relay.set(Relay.Value.kOff);
    		System.out.println("LIGHT OFF");
    	}
    	else if(relay.get() == Relay.Value.kOff)
    	{
    		relay.set(Relay.Value.kForward);
    		System.out.println("LIGHT ON");
    	}
    	else relay.set(Relay.Value.kForward);
    }
    
    public void searchTarget() {
    	double xLocation = visionXSrc.pidGet(); 
    	double setPoint = calcSetPoint(xLocation-RobotMap.DriveMap.centerScreenX);
    	SmartDashboard.putNumber("Target Angle", setPoint);
    	leftDrivePID.setSetpoint(setPoint);
    	rightDrivePID.setSetpoint(setPoint);
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
		
		this.leftDrivePID.initTable(NetworkTable.getTable("PID/Horiontal PID"));
		this.leftDrivePID.disable();
		this.rightDrivePID.disable();
		this.leftDrivePID.setPID(
				RobotMap.DriveMap.horizontalP,
				RobotMap.DriveMap.horizontalI,
				RobotMap.DriveMap.horizontalD
				);
		this.rightDrivePID.setPID(
			RobotMap.DriveMap.horizontalP*RobotMap.DriveMap.driveNegated,
			RobotMap.DriveMap.horizontalI*RobotMap.DriveMap.driveNegated,
			RobotMap.DriveMap.horizontalD*RobotMap.DriveMap.driveNegated
		);
		this.leftDrivePID.enable();
		this.rightDrivePID.enable();
		//this.horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		//this.horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
		this.searchTarget();
	}
	public void visionPeriodic(){
		if (this.onTarget() == true) {
			System.out.println("Target Found");
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.stop();
		} else {
			this.leftDrivePID.enable();
			this.rightDrivePID.enable();
		}
		//this.searchTarget();
		SmartDashboard.putNumber("Robot Yaw", gyro.pidGet());

	}
	public void visionStop(){
		this.leftDrivePID.disable();
		this.rightDrivePID.disable();
		rightDrivePID.setPID(RobotMap.DriveMap.horizontalP, RobotMap.DriveMap.horizontalI, RobotMap.DriveMap.horizontalD);
		
		
	}
	public double getError(){
		return leftDrivePID.getError();
	}
	public boolean onTarget() {
		SmartDashboard.putBoolean("Left On Target", leftDrivePID.onTarget());
		if(Math.abs(leftDrivePID.getError()) < RobotMap.DriveMap.absTolerance) {
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
	public double getRobotPitch() {
		return gyro.getPitch();
	}
	public void driveFor(double rotations) {
		leftMotor.setSetpoint(rotations+leftMotor.getEncPosition());
		rightMotor.setSetpoint(rotations+rightMotor.getEncPosition());
	}
	
	public void rotateTo(double angle){
		this.leftDrivePID.disable();
		this.rightDrivePID.disable();
		this.leftDrivePID.setPID(
				RobotMap.DriveMap.horizontalP,
				RobotMap.DriveMap.horizontalI,
				RobotMap.DriveMap.horizontalD
				);
		this.rightDrivePID.setPID(
			RobotMap.DriveMap.horizontalP*RobotMap.DriveMap.driveNegated,
			RobotMap.DriveMap.horizontalI*RobotMap.DriveMap.driveNegated,
			RobotMap.DriveMap.horizontalD*RobotMap.DriveMap.driveNegated
		);
		this.leftDrivePID.enable();
		this.rightDrivePID.enable();
		this.leftDrivePID.setSetpoint(angle);
		this.rightDrivePID.setSetpoint(angle);
	}
	
	public void quickRotateTo(double angle, QRDrive dir ){
		if (dir == QRDrive.RIGHT){
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.rightDrivePID.setPID(
					RobotMap.DriveMap.horizontalP*RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.horizontalI*RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.horizontalD*RobotMap.DriveMap.driveNegated
				);
			this.rightDrivePID.enable();
		} else if (dir == QRDrive.LEFT) {
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.leftDrivePID.setPID(
					RobotMap.DriveMap.horizontalP,
					RobotMap.DriveMap.horizontalI,
					RobotMap.DriveMap.horizontalD
					);
			this.leftDrivePID.enable();
		}
		else {
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.leftDrivePID.setPID(
					RobotMap.DriveMap.horizontalP,
					RobotMap.DriveMap.horizontalI,
					RobotMap.DriveMap.horizontalD
					);
			this.rightDrivePID.setPID(
				RobotMap.DriveMap.horizontalP*RobotMap.DriveMap.driveNegated,
				RobotMap.DriveMap.horizontalI*RobotMap.DriveMap.driveNegated,
				RobotMap.DriveMap.horizontalD*RobotMap.DriveMap.driveNegated
			);
			this.leftDrivePID.enable();
			this.rightDrivePID.enable();
		}
		this.rightDrivePID.setSetpoint(angle);
		this.leftDrivePID.setSetpoint(angle);
	}
	public void post(){
		SmartDashboard.putNumber("Encoder Left Drive", leftMotor.getPosition());
		SmartDashboard.putNumber("Encoder Right Drive", rightMotor.getPosition());
	}
}
