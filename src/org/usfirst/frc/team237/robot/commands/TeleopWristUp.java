package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopWristUp extends Command {

    public TeleopWristUp() {
    	requires(Robot.wristSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wristSubsystem.disableWrist();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.wristSubsystem.raiseWrist();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        if (OI.wristUp.get() == false){
        	return true;
        }
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wristSubsystem.disableWrist();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}