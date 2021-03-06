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
public class TrackTargetAuto extends Command {
	Timer myTimer;
	boolean trackingFlag;
	boolean shootFlag;
	boolean doneFlag;
	boolean lightFlag;
    public TrackTargetAuto() {
    	// Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	requires(Robot.shooterSubsystem);
    	requires(Robot.pControls);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.driveTrain.relay.set(Relay.Value.kForward);
    	myTimer = new Timer();
    	myTimer.start();
    	trackingFlag = false; 
    	shootFlag = false; 
    	doneFlag = false;
    	lightFlag = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (myTimer.get() > 1.0 && lightFlag == false){
    		Robot.driveTrain.visionStart(); 		
    		lightFlag = true;
    	}
    	else if (myTimer.get() > 2 && trackingFlag == false) {
    		System.out.println("Vision Start");
    		trackingFlag = true;
    	}
    	else if (Robot.driveTrain.noTarget == true){
    		doneFlag = true;
    		System.out.println("NO TARGET");
    		return;
    	}
    	else if (shootFlag == false && Robot.driveTrain.onTarget() && myTimer.get() > 1){
    		myTimer.reset();
    		myTimer.start();
    		shootFlag = true; 
    		Robot.driveTrain.visionStop();
    	}
    	else if (myTimer.get() > 0.25 && shootFlag == true){  // && Robot.driveTrain.onTarget()){
    		Robot.pControls.punch();
    		if(myTimer.get() > 1){
    			doneFlag = true; 
    		}
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (doneFlag == true){
    		return true; 
    	}
    	return false; 
        //return Robot.driveTrain.onTarget()/* && Robot.armSubsystem.onTargetJoint()*/;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.visionStop();
    	Robot.shooterSubsystem.stopShoot();
    	Robot.pControls.retract();
    	Robot.driveTrain.relay.set(Relay.Value.kOff);
    	myTimer.reset();
    	Robot.pControls.retract();
    	Robot.driveTrain.noTarget = false; 
//    	Robot.armSubsystem.visionStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driveTrain.visionStop();
    	Robot.driveTrain.relay.set(Relay.Value.kOff);
    	Robot.shooterSubsystem.stopShoot();
    	myTimer.reset();
    	Robot.pControls.retract();
    	Robot.driveTrain.noTarget = false;
    }
}
