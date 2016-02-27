package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;
import org.usfirst.frc.team237.robot.commands.AutoRotateTo.Directions;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutonomousCommandGroup extends CommandGroup {
    
    public  AutonomousCommandGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new ShootCommandGroup());
    	//addSequential(new DriveFor(16000));
    	addSequential(new EnableCompressor());
    	//addSequential(new SetIceSkate(true));
    	//addSequential(new AutoRotateTo(-90.0, Directions.BOTH));
    	//addSequential(new SetIceSkate(false));
    	//addSequential(new ArmHomeWristSet(-10));
    	//addSequential(new DriveFor(-40000));
    	//addSequential(new DriveFor(20000));
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
    	requires(Robot.driveTrain);
    	requires(Robot.armSubsystem);
    	requires(Robot.shooterSubsystem);
    	requires(Robot.wristSubsystem);
    	requires(Robot.pControls);
    }
}
