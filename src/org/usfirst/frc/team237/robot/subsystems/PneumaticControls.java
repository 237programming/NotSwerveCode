package org.usfirst.frc.team237.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 *
 */
public class PneumaticControls extends Subsystem {
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	Compressor compressor;
	
	static DoubleSolenoid shifter = new DoubleSolenoid(RobotMap.PneumaticsMap.CANAddress, RobotMap.PneumaticsMap.shifterSolenoid1, RobotMap.PneumaticsMap.shifterSolenoid2);
	static DoubleSolenoid iceSkate = new DoubleSolenoid(RobotMap.PneumaticsMap.CANAddress, RobotMap.PneumaticsMap.iceSkateSolenoid1, RobotMap.PneumaticsMap.iceSkateSolenoid2);
	
	public PneumaticControls() {
		compressor = new Compressor();
		//shifter = new DoubleSolenoid(RobotMap.PneumaticsMap.shifterSolenoid1, RobotMap.PneumaticsMap.shifterSolenoid2);
//		shifter.set(false);
//		iceSkate.set(false);
		shifter.set(DoubleSolenoid.Value.kReverse);
		iceSkate.set(DoubleSolenoid.Value.kReverse);
		compressor.setClosedLoopControl(true);
		compressor.start();
	}
	
	public void shiftHigh() {
//		shifter.set(true);
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	public void shiftLow() {
//		shifter.set(false);
		shifter.set(DoubleSolenoid.Value.kReverse);
	}
	public void toggle() {
		if(shifter.get() == /*true*/DoubleSolenoid.Value.kForward) {
			this.shiftLow();
		}
		else if(shifter.get() == /*false*/DoubleSolenoid.Value.kReverse) {
			this.shiftHigh();
		} else {
			//:^)
			this.shiftHigh();
		}
	}
	public void iceSkateOn(){
//		iceSkate.set(true);
		iceSkate.set(DoubleSolenoid.Value.kForward);
	}
	public void iceSkateOff(){
//		iceSkate.set(false);
		iceSkate.set(DoubleSolenoid.Value.kReverse);
	}
	public void iceSkateToggle(){
		if(iceSkate.get() == /*true*/ DoubleSolenoid.Value.kForward){
			this.iceSkateOff();
		}
		else if(iceSkate.get() == /*false*/ DoubleSolenoid.Value.kReverse){
			this.iceSkateOn();
		}
	}
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

