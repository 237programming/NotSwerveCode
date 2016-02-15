package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopArmExtension extends Command {

    public TeleopArmExtension() {
    	requires(Robot.armSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armSubsystem.stopExtension();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double extension = Robot.oi.LeftJoyStick.getY();
    	Robot.armSubsystem.setExtensionSpeed(extension);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armSubsystem.stopExtension();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
