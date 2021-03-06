package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ArmHome extends Command {

    public ArmHome() {
    	requires(Robot.armSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armSubsystem.jointDisable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.armSubsystem.angleArmUp();;
    	//Robot.armSubsystem.setExtensionDistance(0);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.armSubsystem.isShoulderAtZero();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armSubsystem.setShoulderEncZero();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
