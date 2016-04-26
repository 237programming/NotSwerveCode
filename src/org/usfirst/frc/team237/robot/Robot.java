
package org.usfirst.frc.team237.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

import org.usfirst.frc.team237.robot.commands.AutoDefenceShootCenter;
import org.usfirst.frc.team237.robot.commands.AutoDefenceShootLeft;
import org.usfirst.frc.team237.robot.commands.AutoDefenceShootRight;
import org.usfirst.frc.team237.robot.commands.AutonomousCommandGroup;
import org.usfirst.frc.team237.robot.commands.ExampleCommand;
import org.usfirst.frc.team237.robot.commands.TeleopArmUp;
import org.usfirst.frc.team237.robot.commands.TeleopArmExtend;
import org.usfirst.frc.team237.robot.commands.TeleopDrive;
import org.usfirst.frc.team237.robot.commands.TeleopWristUp;
import org.usfirst.frc.team237.robot.subsystems.ArmSubsystem;
import org.usfirst.frc.team237.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team237.robot.subsystems.PIDDrive;
import org.usfirst.frc.team237.robot.subsystems.PneumaticControls;
import org.usfirst.frc.team237.robot.subsystems.ShooterSubsystem;
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

	public static CameraServer camServer;
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	//public static PIDDrive driveTrain;
	public static SuperDrive driveTrain; 
	public static PneumaticControls pControls;
    Command autonomousCommand;
    TeleopDrive driveCommand;
    //TeleopArmUp armCommand;
    //TeleopArmExtend armExtensionCommand;
    TeleopWristUp wristCommand; 
    SendableChooser chooser;
    AutonomousCommandGroup spyCommand;
    AutoDefenceShootCenter autoCenter; 
    AutoDefenceShootLeft autoLeft; 
    AutoDefenceShootRight autoRight;
    public static NetworkTable visionSystemTable;
    public static WristSubsystem wristSubsystem;
    public static ShooterSubsystem shooterSubsystem;
	public static ArmSubsystem armSubsystem;
	public AHRS gyro;
	public String currentAuto;
	//public static PowerDistributionPanel powerBlock;
    //andew rule$
    //private static SerialPort navXSerial = new SerialPort(57600, SerialPort.Port.kMXP);
    //public static AHRS navX ;
    boolean flag;
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	gyro = new AHRS(SerialPort.Port.kMXP);
    	pControls = new PneumaticControls();
    	armSubsystem = new ArmSubsystem(gyro);
    	driveTrain = new SuperDrive(gyro);
    	wristSubsystem = new WristSubsystem();
    	shooterSubsystem = new ShooterSubsystem();
		oi = new OI();
		//driveTrain = new PIDDrive();
		//powerBlock = new PowerDistributionPanel();
		driveCommand = new TeleopDrive();
		camServer = CameraServer.getInstance();
		camServer.startAutomaticCapture(RobotMap.DriveMap.cameraName);
		autoLeft = new AutoDefenceShootLeft();
		autoRight = new AutoDefenceShootRight();
	    spyCommand = new AutonomousCommandGroup(); 
	    autoCenter = new AutoDefenceShootCenter();
		//pControls = new PneumaticControls();
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", new ExampleCommand());
        //chooser.addObject("Default Tele", new TeleopDrive());
//        chooser.addObject("My Auto", new MyAutoCommand());
        gyro.reset();
        SmartDashboard.putData("Auto mode", chooser);
        driveTrain.setAHRS(gyro);
        armSubsystem.setAHRS(gyro);
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
		driveTrain.post();
        wristSubsystem.post();
        armSubsystem.post();
        shooterSubsystem.post();
        //driveTrain.relay.set(Relay.Value.kForward);
        String autoSelected = SmartDashboard.getString("DB/String 0","Center");
		//System.out.println(autoSelected); 
        autoSelected = autoSelected.toLowerCase();
		switch(autoSelected) {
		case "left":
			SmartDashboard.putString("DB/String 5", "Left");
			//autoLeft.start();
			break;
		case "right":
			//autoRight.start();
			SmartDashboard.putString("DB/String 5", "Right");
			break;
		case "center" : 
			//autoCenter.start();
			SmartDashboard.putString("DB/String 5", "Center");
			break;
		case "spy" :
			SmartDashboard.putString("DB/String 5", "Spy");
			//spyCommand.start();
			break;
		default:
			SmartDashboard.putString("DB/String 5", "");
			//autoCenter.start();
			break;
		}
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
        gyro.zeroYaw();
      
        String autoSelected = SmartDashboard.getString("DB/String 0","Center");
		//System.out.println(autoSelected); 
        autoSelected = autoSelected.toLowerCase();
		switch(autoSelected) {
		case "left":
			SmartDashboard.putString("DB/String 5", "Left");
			autoLeft.start();
			break;
		case "right":
			autoRight.start();
			SmartDashboard.putString("DB/String 5", "Right");
			break;
		case "center" : 
			autoCenter.start();
			SmartDashboard.putString("DB/String 5", "Center");
			break;
		case "spy" :
			SmartDashboard.putString("DB/String 5", "Spy");
			spyCommand.start();
			break;
		default:
			SmartDashboard.putString("DB/String 5", "default t Center");
			autoCenter.start();
			break;
		}
    	
    	// schedule the autonomous command (example)
        //if (autonomousCommand != null) autonomousCommand.start();
        //gyro.zeroYaw();
        Robot.pControls.iceSkateOff();
        //autoDefenceCommand.start();
        //autoCommand.start();
        //driveTrain.visionStart();
        
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
        driveTrain.post();
        wristSubsystem.post();
        armSubsystem.post();
        shooterSubsystem.post();
        SmartDashboard.putBoolean("On Target", driveTrain.onTarget());
        //driveTrain.visionPeriodic();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        if (spyCommand != null && spyCommand.isRunning()) spyCommand.cancel();
        if (autoLeft != null && autoLeft.isRunning()) autoLeft.cancel();
        if (autoRight != null && autoRight.isRunning()) autoRight.cancel();
        if (autoCenter != null && autoCenter.isRunning()) autoCenter.cancel();
        
        pControls.iceSkateOff();
        //armCommand.start();
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        if (driveTrain.isTargeting == false){
        	double left = Robot.oi.LeftJoyStick.getY();
    		double right = Robot.oi.RightJoyStick.getY();
    		Robot.driveTrain.set(left, right);
        }
        driveTrain.post();
        wristSubsystem.post();
        armSubsystem.post();
        shooterSubsystem.post();
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
