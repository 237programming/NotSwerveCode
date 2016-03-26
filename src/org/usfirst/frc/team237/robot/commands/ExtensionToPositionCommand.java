package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtensionToPositionCommand extends Command {

	double position;
    public ExtensionToPositionCommand(double pos) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	position = pos;
    	requires(Robot.armSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.armSubsystem.extensionDisable();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.nuclearOption.get() == true){
    		if (position < Robot.armSubsystem.extensionTalon.getPosition()){
    			Robot.armSubsystem.extendArm();
    		} else if (position > Robot.armSubsystem.extensionTalon.getPosition()){
    			Robot.armSubsystem.retractArm();
    		}
    	} else {
    		Robot.armSubsystem.stopExtension();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
       if (position < Robot.armSubsystem.extensionTalon.getPosition()+ 5 &&position > Robot.armSubsystem.extensionTalon.getPosition()- 5  ){ 
    	  return true;
       } else if (OI.nuclearOption.get() == false) {
    	   return true; 
       }
       return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("done moving");
    	Robot.armSubsystem.stopExtension();
    	//Robot.armSubsystem.extensionDisable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.armSubsystem.stopExtension();
    	//Robot.armSubsystem.extensionDisable();
    }
}
