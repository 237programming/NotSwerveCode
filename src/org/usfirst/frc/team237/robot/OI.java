package org.usfirst.frc.team237.robot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import org.usfirst.frc.team237.robot.commands.ExampleCommand;
import org.usfirst.frc.team237.robot.commands.ShifterCommand;
import org.usfirst.frc.team237.robot.commands.TrackTarget;

/*
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	public static Joystick LeftJoyStick = new Joystick(RobotMap.ControlMap.leftStick);
	public static Joystick RightJoyStick = new Joystick(RobotMap.ControlMap.rightStick);
	
	public static Button shiftButton = new JoystickButton(LeftJoyStick, 1);
	public static Button enableVisionTrack = new JoystickButton(LeftJoyStick, 2);
	public static Button disableVisionTrack = new JoystickButton(LeftJoyStick, 3);
	
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
		enableVisionTrack.whenPressed(new TrackTarget());
	}
}

