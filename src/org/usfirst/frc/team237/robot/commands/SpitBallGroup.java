package org.usfirst.frc.team237.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class SpitBallGroup extends CommandGroup {
    
    public  SpitBallGroup() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.
    	addSequential(new IntakeSet(IntakeSet.Direction.outtake));
    	addSequential(new SpitBall());
    	addSequential(new ShootCommand());
    	addSequential(new IntakeSet(IntakeSet.Direction.stop));
    	
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
    }
}
