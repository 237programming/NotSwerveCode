package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ShooterSubsystem extends Subsystem {

	ShooterSubsystem shooterSubsystem;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	public CANTalon shooterLeftTalon;
	public CANTalon shooterRightTalon;
	public VictorSP shooterVictor;
	public AnalogInput lightSensor;
	
	public ShooterSubsystem()
	{
		shooterLeftTalon = new CANTalon(RobotMap.ArmMap.shooterLeftTalon);
		shooterRightTalon = new CANTalon(RobotMap.ArmMap.shooterRightTalon);
		shooterVictor = new VictorSP(RobotMap.ArmMap.shooterVictor);
		lightSensor = new AnalogInput(RobotMap.ArmMap.lightSensorChannel);
	}

	public void setLeft(double speed) {
		shooterLeftTalon.set(speed);
	}
	public void setRight(double speed) {
		shooterRightTalon.set(speed);
	}
	
	public boolean hasBall()
	{
		if(lightSensor.getVoltage() > 2.0) {
			return true;
		}
		return false;
	}
	
	public void shoot() {
		setLeft(RobotMap.ArmMap.shooterFullSpeed);
		setRight(RobotMap.ArmMap.shooterFullSpeed);
	}
	public void triggerHold(){
		shooterVictor.set(RobotMap.ArmMap.shooterVictorSpeed * RobotMap.ArmMap.intakeMultiplayer);
	}
	public void triggerRelease(){
		shooterVictor.set(RobotMap.ArmMap.shooterVictorSpeed);
	}
	public void stopShoot(){
		stopLeft();
		stopRight();
	}
	public void intake() {
		setLeft(RobotMap.ArmMap.shooterFullSpeed * RobotMap.ArmMap.intakeMultiplayer);
		setRight(RobotMap.ArmMap.shooterFullSpeed * RobotMap.ArmMap.intakeMultiplayer);
		shooterVictor.set(RobotMap.ArmMap.shooterVictorSpeed * RobotMap.ArmMap.intakeMultiplayer);
	}
	
	public void stopLeft() {
		shooterLeftTalon.set(0);
	}
	public void stopRight() {
		shooterRightTalon.set(0);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
	public void post(){
		SmartDashboard.putNumber("Light Sensor (Voltage)", lightSensor.getVoltage());
		SmartDashboard.putBoolean("Has Ball", hasBall());
	}
}

