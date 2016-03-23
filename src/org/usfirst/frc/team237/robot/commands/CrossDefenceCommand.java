package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class CrossDefenceCommand extends Command {
	
	double _pitchStart; 
	double _pitchCurrent;
	int _count; 
	boolean _finishedCrossFlag;
	double _encCount; 
    public CrossDefenceCommand() {
        // Use requires() here to declare subsystem dependencies
         requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	_pitchStart = Robot.driveTrain.getRobotPitch();
    	Robot.driveTrain.set(-1.0, -1.0);
    	_finishedCrossFlag = false; 
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	_pitchCurrent = Robot.driveTrain.getRobotPitch();
    	if (_finishedCrossFlag == false && _pitchCurrent < _pitchStart-15){
    		_encCount = Robot.driveTrain.getLeftEncCount();
    		_finishedCrossFlag = true; 
    		System.out.println("Defence Crossed");
    	}
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (Robot.driveTrain.getLeftEncCount() > _encCount+25000 && _finishedCrossFlag == true) {
        	return true; 
        }
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.stop();
    }
}
