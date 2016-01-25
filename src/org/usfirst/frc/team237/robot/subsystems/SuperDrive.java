package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.NetTablesPIDSource;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
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
	private PIDController encoderPID; 
	private PIDController yawPID; 
	private NetTablesPIDSource visionXSrc; 
	AHRS gyro; 
	//Define the drive
	//TankDrive drive; 
	
	
	String DriveName = "Drive"; 
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	public SuperDrive(){
		
		leftMotor = new CANTalon(RobotMap.DriveMap.leftTalon);
		leftMotorPrime = new CANTalon(RobotMap.DriveMap.leftTalonPrime);
		rightMotor = new CANTalon(RobotMap.DriveMap.rightTalon);
		rightMotorPrime	= new CANTalon(RobotMap.DriveMap.rightTalonPrime);
		visionXSrc = new NetTablesPIDSource(); 
		// initialize control loops 
		horizontalPID = new PIDController(1.0,0.0,0.0,visionXSrc,leftMotor);
		//leftMotor.createTableListener()
				
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
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
}

