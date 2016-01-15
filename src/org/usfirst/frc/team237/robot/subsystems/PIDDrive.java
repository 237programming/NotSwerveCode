package org.usfirst.frc.team237.robot.subsystems;
import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

public class PIDDrive extends PIDSubsystem{

	CANTalon speedCtrls[];
	double JoystickInput;
	String DriveName = "Drive"; 
	
	public PIDDrive () {
		super("PIDDrive", 1.0, 0.0, 0.0);
		setAbsoluteTolerance(0.2);
		getPIDController().setContinuous(false);
		
		speedCtrls[0] = new CANTalon(0);
		speedCtrls[1] = new CANTalon(0);
		
		
		
	}

	@Override
	protected double returnPIDInput() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected void usePIDOutput(double output) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
		LiveWindow.addActuator(DriveName, "PIDSubsystem", getPIDController());
		
	}	
}
