package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.subsystems.SuperDrive.ControlMode;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveFor extends Command {

	double distance;
	double startLeftEnc;
	double startRightEnc;
	boolean direction; 
    public DriveFor(double d) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	distance = d;
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	startLeftEnc = Robot.driveTrain.getLeftEncCount();
    	startRightEnc = Robot.driveTrain.getRightEncCount();
    	Robot.driveTrain.changeControlMode(ControlMode.Position);
    	Robot.driveTrain.driveFor(distance);
    	if (distance < 0){
    		direction = false; 
    	} else if (distance >= 0) {
    		direction = true;
    	}
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (direction == true) {
    		if(Robot.driveTrain.getLeftEncCount() >= (startLeftEnc+distance) && Robot.driveTrain.getRightEncCount() >= (startLeftEnc+distance)) {
    			return true;
    		}
    	} else if (direction == false ){
    		if(Robot.driveTrain.getLeftEncCount() <= (startLeftEnc+distance) && Robot.driveTrain.getRightEncCount() <= (startLeftEnc+distance)) {
    			return true;
    		}
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.changeControlMode(ControlMode.PercentVBus);
    	Robot.driveTrain.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.changeControlMode(ControlMode.PercentVBus);
    	Robot.driveTrain.stop();
    	
    }
}
