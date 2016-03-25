package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.subsystems.SuperDrive.ControlMode;
import org.usfirst.frc.team237.robot.subsystems.SuperDrive.QRDrive;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class AutoRotateTo extends Command {

	double angle;
	public enum Directions {
		RIGHT,
		LEFT,
		BOTH
	}
	private Directions direction;
    public AutoRotateTo(double r) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	angle = r;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.changeControlMode(ControlMode.PercentVBus);
    	Robot.driveTrain.rotateNoControl(angle);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.driveTrain.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	//Robot.driveTrain.visionStop();
    	Robot.driveTrain.stop();
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	//Robot.driveTrain.visionStop();
    	Robot.driveTrain.stop();
    }
}
