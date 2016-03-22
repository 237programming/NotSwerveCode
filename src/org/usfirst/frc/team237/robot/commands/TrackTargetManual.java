package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.RobotMap;

import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TrackTargetManual extends Command {
	Timer myTimer;
	boolean trackingFlag;
    public TrackTargetManual() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
//    	requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.relay.set(Relay.Value.kForward);
    	myTimer = new Timer();
    	myTimer.start();
    	trackingFlag = false; 

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (myTimer.get() > 1 && trackingFlag == false) {
    		Robot.driveTrain.visionStart();
    		System.out.println("Vision Start");
    		trackingFlag = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (OI.target.get() == false) {
    		return true; 
    	}
    	
        return Robot.driveTrain.onTarget()/* && Robot.armSubsystem.onTargetJoint()*/;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.visionStop();
    	Robot.driveTrain.relay.set(Relay.Value.kOff);
    	
//    	Robot.armSubsystem.visionStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.visionStop();
    	Robot.driveTrain.relay.set(Relay.Value.kOff);
    }
}
