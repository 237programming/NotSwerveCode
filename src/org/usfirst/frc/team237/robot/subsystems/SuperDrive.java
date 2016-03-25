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
	private NetTablesPIDSource visionSrc; 
	private double leftTolerance;
	private double rightTolerance;
	public boolean isTargeting = false;
    public Relay relay = new Relay(0);
	private AHRS gyro;
	private double currentTarget;
	public boolean noTarget; 
	//Define the drive
	//TankDrive drive; 
	
	boolean _onTarget = false; 
	public enum QRDrive {
		RIGHT,
		LEFT,
		BOTH
	}
	
	public enum ControlMode {
		PercentVBus,
		Position
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
		this.visionSrc = new NetTablesPIDSource(); 
		this.visionSrc.setDirection(NetTablesPIDSource.direction.x);
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
		leftMotor.reverseSensor(true);
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
    
    public void toggleRelay()
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
    	noTarget = false;
    	double xLocation = visionSrc.getCenterX();
    	if (xLocation == 237237237.0){
    		noTarget = true;
    	}
    	double yLocation = visionSrc.getCenterY();
    	System.out.println(xLocation);
    	double setPoint = calcSetPoint(xLocation-(RobotMap.DriveMap.centerScreenX));
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
		isTargeting = true;
		this.leftDrivePID.initTable(NetworkTable.getTable("PID/Horiontal PID"));
		this.leftDrivePID.disable();
		this.rightDrivePID.disable();
		this.searchTarget();
		//if (this.leftDrivePID.getSetpoint() > gyro.getAngle())
		//{
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
		//leftDrivePID.setContinuous(true);
		//rightDrivePID.setContinuous(true);
		//}
		//else
		//{
		//	this.leftDrivePID.setPID(
		//			RobotMap.DriveMap.horizontalP*RobotMap.DriveMap.driveNegated,
		//			RobotMap.DriveMap.horizontalI*RobotMap.DriveMap.driveNegated,
		//			RobotMap.DriveMap.horizontalD*RobotMap.DriveMap.driveNegated
		//			);
		//	this.rightDrivePID.setPID(
		//		RobotMap.DriveMap.horizontalP,
		//		RobotMap.DriveMap.horizontalI,
		//		RobotMap.DriveMap.horizontalD
		//			);
		//}
				
				
		this.leftDrivePID.enable();
		this.rightDrivePID.enable();
		
		//this.horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		//this.horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
		//this.searchTarget();
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
		isTargeting = false;
		
	}
	public double getErrorLeft(){
		return leftDrivePID.getError();
	}
	public boolean onTarget() {
		SmartDashboard.putBoolean("Left On Target", leftDrivePID.onTarget());
		if(gyro.getAngle() > currentTarget- RobotMap.DriveMap.absTolerance && gyro.getAngle() < currentTarget+ RobotMap.DriveMap.absTolerance)  {
			System.out.println("TARGET FOUND");
			return true;
		} /*else if(gyro.getAngle() > rightDrivePID.getSetpoint()- RobotMap.DriveMap.absTolerance && gyro.getAngle() < rightDrivePID.getSetpoint()+ RobotMap.DriveMap.absTolerance)  {
			System.out.println("TARGET FOUND");
			return true;
			
		}*/
		else return false;
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
		//double setPt = gyro.getAngle()+val;
		//double val = opposite/8; 
		double setPt = gyro.pidGet()+val-6;
		currentTarget = setPt;
		if(currentTarget < 0) currentTarget += 360;
		else if (currentTarget >= 360) currentTarget -= 360;
		SmartDashboard.putNumber("Target Angle", currentTarget);
		return setPt;
	}
	public double getRobotPitch() {
		return gyro.getRoll();
	}
	public void changeControlMode(ControlMode cm) {
		if(cm == ControlMode.Position) {
			leftMotor.setProfile(0);
			rightMotor.setProfile(0);
			leftMotor.changeControlMode(CANTalon.TalonControlMode.Position);
			rightMotor.changeControlMode(CANTalon.TalonControlMode.Position);
		}
		else if(cm == ControlMode.PercentVBus) {
			leftMotor.setProfile(1);
			rightMotor.setProfile(1);
			leftMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
			rightMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		}
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
	public void rotateToRelative(double angle){
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
		this.leftDrivePID.setSetpoint(gyro.getAngle() + angle);
		this.rightDrivePID.setSetpoint(gyro.getAngle() + angle);
	}
	
	public void quickRotateTo(double angle, QRDrive dir ){
		if (dir == QRDrive.RIGHT){
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.rightDrivePID.setPID(
					RobotMap.DriveMap.quickTurnP*RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.quickTurnI*RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.quickTurnD*RobotMap.DriveMap.driveNegated
				);
			this.rightDrivePID.enable();
		} else if (dir == QRDrive.LEFT) {
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.leftDrivePID.setPID(
					RobotMap.DriveMap.quickTurnP *RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.quickTurnI *RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.quickTurnD*RobotMap.DriveMap.driveNegated
					);
			this.leftDrivePID.enable();
		}
		else {
			this.leftDrivePID.disable();
			this.rightDrivePID.disable();
			this.leftDrivePID.setPID(
					RobotMap.DriveMap.quickTurnP*RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.quickTurnI*RobotMap.DriveMap.driveNegated,
					RobotMap.DriveMap.quickTurnD*RobotMap.DriveMap.driveNegated
					);
			this.rightDrivePID.setPID(
				RobotMap.DriveMap.quickTurnP,
				RobotMap.DriveMap.quickTurnI,
				RobotMap.DriveMap.quickTurnD
			);
			this.leftDrivePID.enable();
			this.rightDrivePID.enable();
		}
		leftDrivePID.setContinuous(true);
		rightDrivePID.setContinuous(true);
		this.rightDrivePID.setSetpoint(angle);
		this.leftDrivePID.setSetpoint(angle);
	}
	public double getLeftEncCount() {
		return leftMotor.getPosition();
	}
	public double getRightEncCount() {
		return rightMotor.getPosition();
	}
	
	public void quickRotateRelative(double angle, QRDrive direction) {
		double setAngle = gyro.getAngle() + angle;
		if(setAngle <= 0) setAngle += 360;
		quickRotateTo(setAngle, direction);
	}
	
	public void rotateNoControl(double setAngle){
		double oppositeAngle = setAngle + 180;
		double yawCapture = gyro.getYaw();
		if (oppositeAngle > 360){
			oppositeAngle -= 360;
		} else if (oppositeAngle < 360){
			oppositeAngle += 360; 
		}
		currentTarget = setAngle; 
		if (yawCapture >= oppositeAngle){
			set(-0.5,0.5);
		} else if (yawCapture < oppositeAngle){
			set(0.5,-0.5);
		}
	}
	
	public void post(){
		SmartDashboard.putNumber("Encoder Left Drive", leftMotor.getPosition());
		SmartDashboard.putNumber("Encoder Right Drive", rightMotor.getPosition());
		SmartDashboard.putNumber("Current Yaw", gyro.getAngle());
		SmartDashboard.putNumber("Current Pitch", gyro.getRoll());
		SmartDashboard.putNumber("PID Left err", leftMotor.getError());
		SmartDashboard.putNumber("PID Right err", rightMotor.getError());
		SmartDashboard.putBoolean("Gyro Alive?", gyro.isConnected());
		
		
	}
}
