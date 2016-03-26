package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WristToCommand extends Command {

	double angle;
    public WristToCommand(double a) {
    	angle = a;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wristSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.wristSubsystem.enableWrist();
    	Robot.wristSubsystem.setWristPosition(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.wristSubsystem.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	if (OI.nuclearOption.get() == true)
    	{
    		
    	} else {
    		Robot.wristSubsystem.disableWrist();
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.wristSubsystem.disableWrist();
    }
}
