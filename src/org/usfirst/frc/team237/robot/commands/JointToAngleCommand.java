package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class JointToAngleCommand extends Command {

	double angle;
    public JointToAngleCommand(double a) {
    	angle = a;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.armSubsystem);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armSubsystem.jointEnable();
    	Robot.armSubsystem.setAngle(angle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        //if (Robot.powerBlock.getCurrent(7) > RobotMap.ArmMap.currentLimit){
        //	return true;
        //}
    	return Robot.armSubsystem.onTargetJoint(); 
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.armSubsystem.jointDisable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.armSubsystem.jointDisable();
    }
}
