package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristGoToShoot extends Command {

    public WristGoToShoot() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wristSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wristSubsystem.enableWrist();
    	Robot.wristSubsystem.setWristPosition(-1.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.wristSubsystem.onTarget()== true) {
    		return true; 
    	}
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wristSubsystem.hold();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
