package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleopArmDown extends Command {

    public TeleopArmDown() {
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armSubsystem.jointDisable();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.armSubsystem.angleArmDown();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	//if (Robot.powerBlock.getCurrent(7) > RobotMap.ArmMap.currentLimit){
        //	return true;
        //}
        if (OI.armDown.get() == false){
        	return true;
        }
    	return false;
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
