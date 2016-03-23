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
		public static final int centerScreenX = 192;
		public static final int centerScreenY = 100;
		public static final int setPoint = maxInput/2;
		public static final int encFullRotation = 25000;
		public static final double encCountPerInch = encFullRotation / 37;
		public static final double driveMultiplier = 1.0;
		public static final double driveNegated = -1.0;
		public static final double autoDriveMax = 0.2;
		public static final double autoDriveMin = -0.2; 
		public static final double autoEncMax = 0.0; 
		public static final double autoEncMin = 0.0; 
		public static final double horizontalP = 1.0;
		public static final double horizontalI = 0.0;
		public static final double horizontalD = 0.0;
		public static final double quickTurnP = 0.5;
		public static final double quickTurnI = 0.2;
		public static final double quickTurnD = 0.0;
		public static final double driveP = 0.5;
		public static final double driveI = 0.2;
		public static final double driveD = 0.0;
		public static final double absTolerance = 2.0;
		public static final double adjacentLength = 9.25;
		public static final double feetPerPixel = 0.031877;
		public static final double pixelPerFoot = 31.37;
		public static final double pitchFudgeFactor = 2.0; 
		// Motors 
		public static final int leftTalon = 3;
		public static final int leftTalonPrime = 4;
		public static final int rightTalon = 5; 
		public static final int rightTalonPrime = 6;
		public static final int intake = 1;
		public static final int rotateWrist = 8;
		public static final String cameraName = "cam0";
	}
	
	public class ControlMap {
		public static final int leftStick = 0;
		public static final int rightStick = 1; 
		public static final int board1 = 2;
		public static final int board2 = 3;;
	}
	public class PneumaticsMap {
		public static final int CANAddress = 2;
		public static final int shifterSolenoid1 = 0;
		public static final int shifterSolenoid2 = 1;
		public static final int iceSkateSolenoid1 = 2;
		public static final int iceSkateSolenoid2 = 3;
		public static final int trigger1 = 6;
		public static final int trigger2 = 7;
	}
	public class ArmMap{
		public static final double wristPositiveSpeed = 0.6;
		public static final double wristNegativeSpeed = -0.8;
		public static final double wristHome = 0.0;
		public static final double wristTransverseDefense = 0.0;
		public static final double ballIntake = 0.0;
		public static final double drawBridge = 0.0;
		public static final double portCullis = 0.0;
		public static final int jointTalon = 7;
		public static final int extensionTalon = 9; 
		public static final int shooterLeftTalon = 10; 
		public static final int shooterRightTalon = 11; 
		public static final int shooterVictor = 0;
		public static final double shooterFullSpeed = 1.0;
		public static final double shooterVictorSpeed = 1.0;
		public static final double intakeMultiplayer = -0.5;
		public static final double manualAngle = 1.0;
		public static final double manualExtension = 1.0;
		public static final double armRotationP = 1;
		public static final double armRotationI = 0;
		public static final double armRotationD = 0;
		public static final int ballCheckChannel = 0;
		public static final int slaveExtension = 12; //TBD
		public static final double currentLimit = 0.0;
	}
	
	public class AutoMap {
		public static final double armHome = 0;
		public static final double wristHome = -48.0;
		public static final double armDefense = -3.25;
		public static final double wristDefense = -10.0;
		public static final double armHang = 0;
		public static final double wristHang = 0;
		public static final double wristIntake = -19;
		
	}
    // If you are using multiple modules, make sure to define both the port
    // number and the module. For example you with a rangefinder:
    // public static int rangefinderPort = 1;
    // public static int rangefinderModule = 1;
}
