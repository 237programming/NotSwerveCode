
package org.usfirst.frc.team237.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team237.robot.commands.ExampleCommand;
import org.usfirst.frc.team237.robot.commands.TeleopDrive;
import org.usfirst.frc.team237.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team237.robot.subsystems.PIDDrive;
import org.usfirst.frc.team237.robot.subsystems.PneumaticControls;
import org.usfirst.frc.team237.robot.subsystems.SuperDrive;
import org.usfirst.frc.team237.robot.subsystems.WristSubsystem;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	//public static PIDDrive driveTrain;
	public static SuperDrive driveTrain; 
	public static PneumaticControls pControls = new PneumaticControls();
    Command autonomousCommand;
    TeleopDrive driveCommand;
    SendableChooser chooser;
    public static NetworkTable visionSystemTable;
    public static Relay relay = new Relay(0);
    public static WristSubsystem wristSubsystem;
    //p.s. andew rule$
    //private static SerialPort navXSerial = new SerialPort(57600, SerialPort.Port.kMXP);
    //public static AHRS navX ;
    boolean flag;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	driveTrain = new SuperDrive();
		oi = new OI();
		//driveTrain = new PIDDrive();
		
		driveCommand = new TeleopDrive();
		//pControls = new PneumaticControls();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
        //chooser.addObject("Default Tele", new TeleopDrive());
//        chooser.addObject("My Auto", new MyAutoCommand());
        wristSubsystem = new WristSubsystem();
        SmartDashboard.putData("Auto mode", chooser);
        SmartDashboard.putNumber("PID error", driveTrain.getError());
    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){
    	//driveTrain.visionStop();
    	//driveTrain.resetTarget();
    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
		SmartDashboard.putNumber("PID error", driveTrain.getError());
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) chooser.getSelected();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
    	
    	// schedule the autonomous command (example)
        if (autonomousCommand != null) autonomousCommand.start();
        
        driveTrain.visionStart();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        SmartDashboard.putNumber("PID error", driveTrain.getError());
        SmartDashboard.putBoolean("On Target", driveTrain.onTarget());
        driveTrain.visionPeriodic();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        driveTrain.visionStop();
        driveCommand.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
