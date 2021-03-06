package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.OI;
import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class TrackShootCommandGroup extends CommandGroup {
    
    public  TrackShootCommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	
    		
    	addSequential(new ArmToWristTo(-18.0,0.0));
    	addSequential(new SpoolUpShooter(1.0));
    	addSequential(new TrackTargetManual());
    	//addSequential(new ShootCommand());
    	
        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	requires(Robot.shooterSubsystem);
    	requires(Robot.wristSubsystem);
    	requires(Robot.armSubsystem);
    }
}
