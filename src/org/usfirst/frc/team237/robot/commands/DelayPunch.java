package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DelayPunch extends Command {
	Timer myTimer;
    public DelayPunch() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.pControls);
    	requires(Robot.shooterSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	myTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (myTimer.get() > 2.0){
    		Robot.pControls.punch();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (myTimer.get() > 2.5){
    		return true;
    	}
    	return false; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.stopShoot();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooterSubsystem.stopShoot();
    }
}
