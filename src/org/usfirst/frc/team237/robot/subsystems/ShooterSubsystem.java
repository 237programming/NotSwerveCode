package org.usfirst.frc.team237.robot.subsystems;

import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

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
	
	public ShooterSubsystem()
	{
		shooterLeftTalon = new CANTalon(RobotMap.ArmMap.shooterLeftTalon);
		shooterRightTalon = new CANTalon(RobotMap.ArmMap.shooterRightTalon);
		shooterVictor = new VictorSP(RobotMap.ArmMap.shooterVictor);
	}

	public void setLeft(double speed) {
		shooterLeftTalon.set(speed);
	}
	public void setRight(double speed) {
		shooterRightTalon.set(speed);
	}
	
	public void shoot() {
		setLeft(RobotMap.ArmMap.shooterFullSpeed);
		setRight(RobotMap.ArmMap.shooterFullSpeed);
		shooterVictor.set(RobotMap.ArmMap.shooterFullSpeed);
	}
	
	public void stopShoot(){
		shooterVictor.set(0);
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
}

