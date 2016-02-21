package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class FeedBallCommand extends Command {

    public FeedBallCommand() {
    	requires(Robot.shooterSubsystem);
    	requires(Robot.wristSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.shooterSubsystem.intake();
    	Robot.wristSubsystem.inTake();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (OI.intake.get() == false){
    		return true;
    	}
    	return Robot.shooterSubsystem.hasBall();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.triggerStop();
    	Robot.shooterSubsystem.stopShoot();
    	Robot.wristSubsystem.stopTake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
