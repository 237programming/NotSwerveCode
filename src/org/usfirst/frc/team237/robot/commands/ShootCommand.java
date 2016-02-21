package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.subsystems.ShooterSubsystem;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */

public class ShootCommand extends Command {

    public ShootCommand() {
    	requires(Robot.shooterSubsystem);
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Timer.delay(2.0);
    	Robot.shooterSubsystem.triggerRelease();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
//    	System.out.println("SHOOTER EXECUTE");
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (Robot.shooterSubsystem.hasBall() == false ){
    		return true; 
    	}
    	return false; 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooterSubsystem.triggerStop();
    	Robot.shooterSubsystem.stopShoot();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
