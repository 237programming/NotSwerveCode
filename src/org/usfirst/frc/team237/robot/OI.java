package org.usfirst.frc.team237.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team237.robot.commands.ExampleCommand;
import org.usfirst.frc.team237.robot.commands.IceSkateCommand;
import org.usfirst.frc.team237.robot.commands.LightCommand;
import org.usfirst.frc.team237.robot.commands.ShifterCommand;
import org.usfirst.frc.team237.robot.commands.ShootCommand;
import org.usfirst.frc.team237.robot.commands.TeleopArmDown;
import org.usfirst.frc.team237.robot.commands.TeleopArmRetract;
import org.usfirst.frc.team237.robot.commands.TeleopArmUp;
import org.usfirst.frc.team237.robot.commands.TeleopWristDown;
import org.usfirst.frc.team237.robot.commands.TeleopWristUp;
import org.usfirst.frc.team237.robot.commands.TeleopArmExtend;
import org.usfirst.frc.team237.robot.commands.TrackTarget;
import org.usfirst.frc.team237.robot.commands.WristIntakeCommand;
import org.usfirst.frc.team237.robot.commands.WristOuttakeCommand;

/*
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick LeftJoyStick = new Joystick(RobotMap.ControlMap.leftStick);
	public static Joystick RightJoyStick = new Joystick(RobotMap.ControlMap.rightStick);
	public static Button shiftButton        = new JoystickButton(LeftJoyStick, 1);
	public static Button enableLight        = new JoystickButton(RightJoyStick , 1);
	public static Button enableVisionTrack  = new JoystickButton(RightJoyStick, 2);
	public static Button disableVisionTrack = new JoystickButton(RightJoyStick, 3);
	public static Button armUp              = new JoystickButton(RightJoyStick, 2);
	public static Button armDown            = new JoystickButton(RightJoyStick, 3);
	public static Button armExtend          = new JoystickButton(RightJoyStick, 4);
	public static Button armRetract         = new JoystickButton(RightJoyStick, 5);
	public static Button wristUp            = new JoystickButton(LeftJoyStick, 7);
	public static Button wristDown          = new JoystickButton(LeftJoyStick, 6);
	public static Button outTake            = new JoystickButton(LeftJoyStick, 3);
	public static Button inTake             = new JoystickButton(LeftJoyStick, 2);
	public static Button toggleSkates       = new JoystickButton(LeftJoyStick, 9);
	public static Button victorShooter      = new JoystickButton(LeftJoyStick, 4);
	
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
		
		shiftButton.whenPressed(new ShifterCommand());
		enableLight.whenPressed(new LightCommand());
		//enableVisionTrack.whenPressed(new TrackTarget());
		armUp.whenPressed(new TeleopArmUp());
		armDown.whenPressed(new TeleopArmDown());
		armExtend.whenPressed(new TeleopArmExtend());
		armRetract.whenPressed(new TeleopArmRetract());
		wristUp.whenPressed(new TeleopWristUp());
		wristDown.whenPressed(new TeleopWristDown());
		outTake.whenPressed(new WristOuttakeCommand());
		inTake.whenPressed(new WristIntakeCommand());
		toggleSkates.whenPressed(new IceSkateCommand());
		victorShooter.whenPressed(new ShootCommand());
	}
}