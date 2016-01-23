package org.usfirst.frc.team237.robot.subsystems;

import java.awt.Robot;

import org.usfirst.frc.team237.robot.RobotMap;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;

public class PIDDrive extends PIDSubsystem{
	// changed some things around for ease of use. 
	// --Dan 
	CANTalon leftMotor ;
	CANTalon leftMotorPrime ;
	CANTalon rightMotor;
	CANTalon rightMotorPrime;
	
	//PIDController horizontalPIDLeft;
	//PIDController horizontalPIDLeft2;
	//PIDSource visionInput;
	
	
	String DriveName = "Drive"; 
	
	public PIDDrive () {
		super("PIDDrive", 1.0, 0.0, 0.0);
		setAbsoluteTolerance(0.2);
		// set up PID stuff 
		getPIDController().setContinuous(false);
		getPIDController().setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		getPIDController().setInputRange(RobotMap.DriveMap.autoEncMin, RobotMap.DriveMap.autoEncMax);
		getPIDController().setSetpoint(RobotMap.DriveMap.encoderSetPoint);
		// create motors. 
		leftMotor = new CANTalon(RobotMap.DriveMap.leftTalon);
		leftMotorPrime = new CANTalon(RobotMap.DriveMap.leftTalonPrime);
		rightMotor = new CANTalon(RobotMap.DriveMap.rightTalon);
		rightMotorPrime	= new CANTalon(RobotMap.DriveMap.rightTalonPrime);
//		horizontalPIDLeft = new PIDController(RobotMap.DriveMap.horizontalP, RobotMap.DriveMap.horizontalI, RobotMap.DriveMap.horizontalD, 0, visionInput, leftMotor);
//		horizontalPIDLeft2 = new PIDController(RobotMap.DriveMap.horizontalP, RobotMap.DriveMap.horizontalI, RobotMap.DriveMap.horizontalD, 0, visionInput, leftMotor);
//		horizontalPIDLeft.setContinuous(false);
//		horizontalPIDLeft.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
//		horizontalPIDLeft.setInputRange(RobotMap.DriveMap.minInput, RobotMap.DriveMap.maxInput);
//		horizontalPIDLeft.setSetpoint(RobotMap.DriveMap.setPoint);
//		horizontalPIDLeft2.setContinuous(false);
//		horizontalPIDLeft2.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
//		horizontalPIDLeft2.setInputRange(RobotMap.DriveMap.minInput, RobotMap.DriveMap.maxInput);
//		horizontalPIDLeft2.setSetpoint(RobotMap.DriveMap.setPoint);
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		double encoderAvg = (leftMotor.getPosition() + rightMotor.getPosition())/2;
		return encoderAvg;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		leftMotor.pidWrite(output);
		leftMotorPrime.pidWrite(output);
		rightMotor.pidWrite(output);
		rightMotorPrime.pidWrite(output);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
		LiveWindow.addActuator(DriveName, "PIDSubsystem", getPIDController());
		
	}
	
	public void autoInit() {
		getPIDController().enable();
	}
	
	public void autoEnd() {
		getPIDController().disable();
		
	}
	
	public void setLeft(double speed) {
		speed *= RobotMap.DriveMap.driveMultiplier;
		//TODO: add the actual set motor speed.
		leftMotor.set(speed);
		leftMotorPrime.set(speed);
	}
	
	public void setRight(double speed ) {
		speed *= RobotMap.DriveMap.driveMultiplier;
		//TODO: add the actual set motor speed.
		rightMotor.set(speed);
		rightMotorPrime.set(speed);
	}
	
	public void set(double speedLeft, double speedRight){
		setLeft(speedLeft);
		setRight(speedRight); 
	}
	public void stop(){
		setRight(0.0);
		setLeft(0.0);
	}
	
	public void seekTargetX(double output){
		int testVal = 0;
		//horizontalPIDLeft.enable();
		//leftMotor.pidWrite(output);
		//leftMotorPrime.pidWrite(output);
	}
	
	public int getVisionInput(){
		//TODO: get the x position from network table
		int X = 0;
		return X;
		
	}
	
}
