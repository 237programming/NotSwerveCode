package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.NetTablesPIDSource;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.Sendable;
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
	AHRS gyro; 
	//Define the drive
	//TankDrive drive; 
	
	boolean _onTarget = false; 
	
	String DriveName = "Drive"; 
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public SuperDrive(){
		leftMotor = new CANTalon(RobotMap.DriveMap.leftTalon);
		leftMotorPrime = new CANTalon(RobotMap.DriveMap.leftTalonPrime);
		rightMotor = new CANTalon(RobotMap.DriveMap.rightTalon);
		rightMotorPrime	= new CANTalon(RobotMap.DriveMap.rightTalonPrime);
		visionXSrc = new NetTablesPIDSource(); 
		visionXSrc.setDirection(NetTablesPIDSource.direction.x);

		leftMotorPrime.changeControlMode(CANTalon.TalonControlMode.Follower);
		leftMotorPrime.set(RobotMap.DriveMap.leftTalon);
		rightMotorPrime.changeControlMode(CANTalon.TalonControlMode.Follower);
		rightMotorPrime.set(RobotMap.DriveMap.rightTalon);

		horizontalPID = new PIDController(
				RobotMap.DriveMap.horizontalP,
				RobotMap.DriveMap.horizontalI,
				RobotMap.DriveMap.horizontalD,
				visionXSrc,
				rightMotor);
		horizontalNegatedPID = new PIDController(
				RobotMap.DriveMap.horizontalP,
				RobotMap.DriveMap.horizontalI,
				RobotMap.DriveMap.horizontalD,
				visionXSrc,
				rightMotor);
		horizontalPID.setInputRange(RobotMap.DriveMap.minInput, RobotMap.DriveMap.maxInput);
		horizontalNegatedPID.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		horizontalNegatedPID.setInputRange(RobotMap.DriveMap.minInput, RobotMap.DriveMap.maxInput);
		horizontalPID.setOutputRange(RobotMap.DriveMap.autoDriveMin, RobotMap.DriveMap.autoDriveMax);
		horizontalPID.setToleranceBuffer(5);
		horizontalNegatedPID.setToleranceBuffer(5);
		horizontalPID.setAbsoluteTolerance(400.0);
		horizontalNegatedPID.setAbsoluteTolerance(400.0);

		horizontalPID.initTable(NetworkTable.getTable("PID/Horiontal PID"));
		//horizontalPID.startLiveWindowMode();
		//SmartDashboard.putNumber("Input", horizontalPID.getTable().getNumber("p", 0.0));
		// initialize control loops 
		//leftMotor.createTableListener()
		
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void setLeft(double speed) {
		speed *= RobotMap.DriveMap.driveNegated;
		//TODO: add the actual set motor speed.
		leftMotor.set(speed);
		//leftMotorPrime.set(speed);
	}
	
	public void setRight(double speed ) {
		speed *= RobotMap.DriveMap.driveNegated;
		//TODO: add the actual set motor speed.
		rightMotor.set(speed);
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
		horizontalPID.enable();
		horizontalNegatedPID.enable();
		horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
		
	}
	public void visionPeriodic(){
		if (horizontalPID.onTarget() == true) {
			System.out.println("Target Found");
			horizontalPID.disable();
			horizontalNegatedPID.disable();
			//stop();
		} else {
			
			
			//horizontalPID.enable();
			//horizontalNegatedPID.enable();
		}
		horizontalPID.setSetpoint(RobotMap.DriveMap.setPoint);
		horizontalNegatedPID.setSetpoint(RobotMap.DriveMap.setPoint);
	}
	public void visionStop(){
		horizontalPID.disable();
		horizontalNegatedPID.disable();
	}
	public double getError(){
		return horizontalPID.getError();
	}
	public boolean onTarget() {
		SmartDashboard.putBoolean("Left On Target", horizontalPID.onTarget());
		if (_onTarget == false){
			_onTarget = horizontalPID.onTarget();
		}
		return _onTarget;
	}
}

