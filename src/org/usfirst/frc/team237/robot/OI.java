package org.usfirst.frc.team237.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team237.robot.commands.ArmHome;
import org.usfirst.frc.team237.robot.commands.ArmHomeWristSet;
import org.usfirst.frc.team237.robot.commands.ArmToWristTo;
import org.usfirst.frc.team237.robot.commands.DisableArmExtention;
import org.usfirst.frc.team237.robot.commands.ExampleCommand;
import org.usfirst.frc.team237.robot.commands.ExtensionToPositionCommand;
import org.usfirst.frc.team237.robot.commands.FeedBallCommand;
import org.usfirst.frc.team237.robot.commands.HangCommand;
import org.usfirst.frc.team237.robot.commands.HangPositionCommand;
import org.usfirst.frc.team237.robot.commands.HoldArmJointCommand;
import org.usfirst.frc.team237.robot.commands.HoldWristCommand;
import org.usfirst.frc.team237.robot.commands.IceSkateCommand;
import org.usfirst.frc.team237.robot.commands.LightCommand;
import org.usfirst.frc.team237.robot.commands.LowBarPositionCommandGroup;
import org.usfirst.frc.team237.robot.commands.ShifterCommand;
import org.usfirst.frc.team237.robot.commands.ShootCommand;
import org.usfirst.frc.team237.robot.commands.TrackShootCommandGroup;
import org.usfirst.frc.team237.robot.commands.SpitBallGroup;
import org.usfirst.frc.team237.robot.commands.TeleopArmDown;
import org.usfirst.frc.team237.robot.commands.TeleopArmRetract;
import org.usfirst.frc.team237.robot.commands.TeleopArmUp;
import org.usfirst.frc.team237.robot.commands.TeleopWristDown;
import org.usfirst.frc.team237.robot.commands.TeleopWristUp;
import org.usfirst.frc.team237.robot.commands.TeleopArmExtend;
import org.usfirst.frc.team237.robot.commands.TrackTargetAuto;
import org.usfirst.frc.team237.robot.commands.TrackTargetManual;
import org.usfirst.frc.team237.robot.commands.WristGoToShoot;
import org.usfirst.frc.team237.robot.commands.WristIntakeCommand;
import org.usfirst.frc.team237.robot.commands.WristOuttakeCommand;
import org.usfirst.frc.team237.robot.commands.WristToCommand;

/*
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick LeftJoyStick = new Joystick(RobotMap.ControlMap.leftStick);
	public static Joystick RightJoyStick = new Joystick(RobotMap.ControlMap.rightStick);
	public static Joystick Joystick2 = new Joystick(RobotMap.ControlMap.board1);
	public static Joystick Joystick3 = new Joystick(RobotMap.ControlMap.board2);
	
	public static Button shift              = new JoystickButton(RightJoyStick, 1);
	public static Button iceSkate           = new JoystickButton(LeftJoyStick, 1);
	public static Button spitBall           = new JoystickButton(Joystick2, 12);
	public static Button shootBall			= new JoystickButton(Joystick2, 9);
	public static Button intake             = new JoystickButton(Joystick2, 11);
	public static Button armExtend          = new JoystickButton(Joystick3, 3);
	public static Button armRetract         = new JoystickButton(Joystick3, 4);
	public static Button armUp              = new JoystickButton(Joystick2, 4);
	public static Button armDown            = new JoystickButton(Joystick2, 3);
	public static Button wristUp            = new JoystickButton(Joystick2, 2);
	public static Button wristDown          = new JoystickButton(Joystick2, 1);
	public static Button autoTarget         = new JoystickButton(Joystick2, 5);
	public static Button transverseDefence 	= new JoystickButton(Joystick3, 5);
	public static Button intakePos 			= new JoystickButton(Joystick2, 7);
	public static Button lowBarPos 			= new JoystickButton(Joystick2, 8);
	public static Button nuclearOption 		= new JoystickButton(Joystick3, 2);
	public static Button target             = new JoystickButton(Joystick2, 5);
	public static Button finalHangBtn		= new JoystickButton(Joystick3, 7);
	public static Button lightToggle        = new JoystickButton(Joystick3, 1);
	
//	public static Button enableLight        = new JoystickButton(LeftJoyStick, 11);
//	public static Button enableVisionTrack  = new JoystickButton(RightJoyStick, 2);
//	public static Button disableVisionTrack = new JoystickButton(RightJoyStick, 3);
//	public static Button armUp              = new JoystickButton(RightJoyStick, 2);
//	public static Button armDown            = new JoystickButton(RightJoyStick, 3);
//	public static Button armExtend          = new JoystickButton(RightJoyStick, 4);
//	public static Button armRetract         = new JoystickButton(RightJoyStick, 5);
//	public static Button wristUp            = new JoystickButton(RightJoyStick, 6);
//	public static Button wristDown          = new JoystickButton(RightJoyStick, 7);
//	public static Button outTake            = new JoystickButton(LeftJoyStick, 3);
//	public static Button inTake             = new JoystickButton(LeftJoyStick, 2);
//	public static Button toggleSkates       = new JoystickButton(RightJoyStick, 11);
//	public static Button enableHold         = new JoystickButton(RightJoyStick,8);
//	public static Button enableArmHold      = new JoystickButton(RightJoyStick,9);	
//	public static Button shooterButton      = new JoystickButton(RightJoyStick, 1);
//	public static Button shooterIntake      = new JoystickButton(LeftJoyStick, 1);
//	public static Button shiftButton        = new JoystickButton(LeftJoyStick, 2);
	
	
	
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
	public OI() {
		shift.whenPressed(new ShifterCommand());
		//autoTarget.whenPressed(new TrackTarget());
		armUp.whenPressed(new TeleopArmUp());
		armDown.whenPressed(new TeleopArmDown());
		armExtend.whenPressed(new TeleopArmExtend());
		armRetract.whenPressed(new TeleopArmRetract());
		wristUp.whenPressed(new TeleopWristUp());
		wristDown.whenPressed(new TeleopWristDown());
		iceSkate.whenPressed(new IceSkateCommand());
		spitBall.whenPressed(new SpitBallGroup());
		intake.whenPressed(new FeedBallCommand());
		//nuclearOption.whenPressed(new DisableArmExtention());
		
		lightToggle.whenPressed(new HangPositionCommand());
		finalHangBtn.whenPressed(new HangCommand());
		target.whenPressed(new TrackShootCommandGroup());
		shootBall.whenPressed(new LightCommand());
		lowBarPos.whenPressed(new ArmHomeWristSet(RobotMap.AutoMap.wristHome));
		intakePos.whenPressed(new ArmHomeWristSet(RobotMap.AutoMap.wristIntake));
		transverseDefence.whenPressed(new ArmToWristTo(RobotMap.AutoMap.armDefense,RobotMap.AutoMap.wristDefense));
	}
}