package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HangPositionCommand extends CommandGroup {
    
    public  HangPositionCommand() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	//addSequential(new JointToAngleCommand(-18.7));
    	//System.out.println("Nuclear option: TRUE");
    	addSequential(new ExtensionToPositionCommand(567.97));
    	//System.out.println("Nuclear option: FALSE");
    	addSequential(new WristToCommand(-27.0));
    	addSequential(new JointToAngleCommand(-42.0));
    	
    	//addSequential();
    	
    	
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
    	requires(Robot.armSubsystem);
    	requires(Robot.wristSubsystem);
        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    }
}
