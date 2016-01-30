package org.usfirst.frc.team237.robot;

import org.usfirst.frc.team237.robot.subsystems.PIDDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    // For example to map the left and right motors, you could define the
    // following variables to use with your drivetrain subsystem.
    // public static int leftMotor = 1;
    // public static int rightMotor = 2;
    
	// Edited by Dan
	// we are going to define all the motors we will need
	
	// Drive Train map. 
	public class DriveMap {
		// Drive constants
		public static final int encoderSetPoint = 10000; 
		public static final int minInput = 0;
		public static final int maxInput = 320;
		public static final int setPoint = maxInput/2;
		public static final double driveMultiplier = 1.0;
		public static final double driveNegated = -1.0;
		public static final double autoDriveMax = 0.5;
		public static final double autoDriveMin = -0.5; 
		public static final double autoEncMax = 0.0; 
		public static final double autoEncMin = 0.0; 
		public static final double horizontalP = 0.5;
		public static final double horizontalI = 0.0;
		public static final double horizontalD = 0.0;
		public static final double absTolerance = 40.0;
		// Motors 
		public static final int leftTalon = 3;
		public static final int leftTalonPrime = 4;
		public static final int rightTalon = 5; 
		public static final int rightTalonPrime = 6; 
	}
	// turret motors 
	
	public class TurretMap {
		// TODO: add turret map.
	}
	public class ControlMap {
		public static final int leftStick = 0;
		public static final int rightStick = 1; 
		public static final int controller = 2; 
	}
	
	public class PneumaticsMap {
		public static final int shifterSolenoid1 = 0;
		public static final int shifterSolenoid2 = 1;
		public static final int CANAddres = 2; 
	}
	
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
