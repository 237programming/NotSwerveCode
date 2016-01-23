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
	DoubleSolenoid shifter;
	
	public void PneumaticControls() {
		compressor = new Compressor();
		shifter = new DoubleSolenoid(RobotMap.PneumaticsMap.shifterSolenoid1, RobotMap.PneumaticsMap.shifterSolenoid2);
		compressor.setClosedLoopControl(true);
		compressor.start();
	}
	
	public void shiftHigh() {
		shifter.set(DoubleSolenoid.Value.kForward);
	}
	public void shiftLow() {
		shifter.set(DoubleSolenoid.Value.kOff);
	}
	public void toggle() {
		if(shifter.get() == DoubleSolenoid.Value.kForward) {
			this.shiftLow();
		}
		else if(shifter.get() == DoubleSolenoid.Value.kOff) {
			this.shiftHigh();
		} else {
			//:^)
		}
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
}

