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
		public static final int maxInput = 360;
		public static final int centerScreenX = 200;
		public static final int centerScreenY = 100;
		public static final int setPoint = maxInput/2;
		public static final double driveMultiplier = 1.0;
		public static final double driveNegated = -1.0;
		public static final double autoDriveMax = 0.5;
		public static final double autoDriveMin = -0.5; 
		public static final double autoEncMax = 0.0; 
		public static final double autoEncMin = 0.0; 
		public static final double horizontalP = 0.5;
		public static final double horizontalI = 0.2;
		public static final double horizontalD = 0.0;
		public static final double driveP = 0.5;
		public static final double driveI = 0.2;
		public static final double driveD = 0.0;
		public static final double absTolerance = 0.1;
		public static final double adjacentLength = 9.25;
		public static final double feetPerPixel = 0.0305997;
		public static final double pixelPerFoot = 32.68;
		public static final double pitchFudgeFactor = 0.1; 
		// Motors 
		public static final int leftTalon = 3;
		public static final int leftTalonPrime = 4;
		public static final int rightTalon = 5; 
		public static final int rightTalonPrime = 6;
		//Values TBD
		public static final int intake = 1;
		public static final int rotateWrist = 7;
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
		public static final int iceSkateSolenoid = 1;
	}
	public class ArmMap{
		public static final double wristPositiveSpeed = 0.3;
		public static final double wristNegativeSpeed = -0.3;
		public static final double wristHome = 0.0;
		public static final double wristTransverseDefense = 0.0;
		public static final double ballIntake = 0.0;
		public static final double drawBridge = 0.0;
		public static final double portCullis = 0.0;
		public static final int jointTalon = 8;
		public static final int extensionTalon = 9; 
		public static final int shooterLeftTalon = 10; 
		public static final int shooterRightTalon = 11; 
		public static final int shooterVictor = 0;
		public static final double shooterFullSpeed = 1.0;
		public static final double intakeMultiplayer = 0.3;
		public static final double manualAngle = 0.5;
		public static final double manualExtension = 0.5;
		public static final double armRotationP = 1;
		public static final double armRotationI = 0;
		public static final double armRotationD = 0;
	}
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
