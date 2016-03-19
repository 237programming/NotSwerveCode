package org.usfirst.frc.team237.robot.commands;

import org.usfirst.frc.team237.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoDefenceShoot extends CommandGroup {
    
    public  AutoDefenceShoot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential( new DriveFor(10000));
    	addParallel( new AutoRotateTo(15,AutoRotateTo.Directions.BOTH));
    	addSequential( new ArmToWristTo(18.5,-3.0));
    	addSequential( new SpoolUpShooter(0.5));
    	addSequential( new AutonomousTrack());
    	addSequential(new SpoolUpShooter(1.0));
    	addSequential( new ShootCommand());
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
    	requires(Robot.wristSubsystem);
    	
    }
}