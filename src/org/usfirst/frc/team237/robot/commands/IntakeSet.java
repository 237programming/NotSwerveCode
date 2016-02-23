package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeSet extends Command {

	public enum Direction {
		intake,
		outtake,
		stop;
	}
	Direction _direction;
    public IntakeSet(Direction dir) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.wristSubsystem);
    	_direction = dir;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	if(_direction == Direction.intake) {
    		Robot.wristSubsystem.inTake();
    	}
    	else if(_direction == Direction.outtake) {
    		Robot.wristSubsystem.outTake();
    	}
    	else {
    		Robot.wristSubsystem.stopTake();
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
