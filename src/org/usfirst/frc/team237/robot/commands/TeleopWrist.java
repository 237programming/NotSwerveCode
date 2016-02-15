package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopWrist extends Command {

    public TeleopWrist() {
    	requires(Robot.wristSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wristSubsystem.stopWrist();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double wristAngle = Robot.oi.LeftJoyStick.getY();
    	Robot.wristSubsystem.set(wristAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.wristSubsystem.stopWrist();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}