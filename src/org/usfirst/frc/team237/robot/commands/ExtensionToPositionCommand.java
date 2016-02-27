package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtensionToPositionCommand extends Command {

	double position;
    public ExtensionToPositionCommand(double pos) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	position = pos;
    	requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armSubsystem.extensionEnable();
    	Robot.armSubsystem.setExtensionDistance(position);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.armSubsystem.onTargetExtension();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armSubsystem.extensionDisable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.armSubsystem.extensionDisable();
    }
}
