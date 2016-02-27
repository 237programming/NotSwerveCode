package org.usfirst.frc.team237.robot.commands;

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
    	addSequential(new JointToAngleCommand(18.7));
    	addParallel(new ExtensionToPositionCommand(567.97));
    	addSequential(new JointToAngleCommand(36.5));
    	addSequential(new WristToCommand(25.0));
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
