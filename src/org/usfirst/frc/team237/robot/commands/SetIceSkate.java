package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SetIceSkate extends Command {

	Timer myTimer; 
	boolean iceSkate;
	boolean flag; 
    public SetIceSkate(boolean ic) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	iceSkate = ic;
    	requires(Robot.pControls);
    	myTimer = new Timer();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	flag = false; 
    	if(iceSkate) Robot.pControls.iceSkateOn();
    	else if(!iceSkate) Robot.pControls.iceSkateOff();
    	myTimer.start();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (myTimer.get()> 1.0){
    		flag = true; 
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return flag;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	flag = true;
    }
}
