package org.usfirst.frc.team811.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap implements Config {
    
	// Objects
	public static Joystick joystick1;
	public static Joystick joystick2;
	
	public static CANTalon drivefrontright;
    public static CANTalon drivefrontleft;
    public static CANTalon drivebackleft;
    public static CANTalon drivebackright;
    public static CANTalon shootertalon1;
    public static CANTalon shootertalon2;
    public static CANTalon turret;
    
    public static CANTalon climber;
    public static CANTalon intakeBall;
    
    public static Victor agitator;
    
    public static Victor gearIntake;
    public static CANTalon gearGrabber;
    
    public static Victor turretLoader;
    
    public static DigitalInput gearTopLimit;
    public static DigitalInput gearBottomLimit;
    public static RobotDrive driveTrain;
    public static AnalogGyro driveGyro;
    public static AHRS ahrs;
    public static Encoder driveEncoder;
    
    public static PIDController drivePID;
    public static PIDController visionTurretController;
    public static PIDController visionGearController;
    public static PIDController lidarPID;
    
    public static NetworkTable turretTable;
    public static NetworkTable gearTable;
    
    public static DriverStation ds = DriverStation.getInstance();
    
    public static DigitalInput betaBot;
    
    public static int gear_auto_pos;
    
    
    public void init() {
    	joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
        
    	drivefrontright = new CANTalon(FRONT_RIGHT_PORT);
        drivefrontleft = new CANTalon(FRONT_LEFT_PORT);
        drivebackleft = new CANTalon(REAR_LEFT_PORT);
        drivebackright = new CANTalon(REAR_RIGHT_PORT);
        drivebackright.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        drivebackright.reverseSensor(false);	//not working??
        //drivebackright.configEncoderCodesPerRev((int) 1); // possible values for codesperrev: (360, Pi * diameter of wheels(?), 180)
        driveTrain = new RobotDrive(drivefrontleft, drivebackleft, drivefrontright, drivebackright);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        ahrs = new AHRS(SerialPort.Port.kMXP);
        //turret = new CANTalon(TURRET_PORT);
        //turret.setFeedbackDevice(FeedbackDevice.AnalogPot);
        intakeBall = new CANTalon(INTAKE_BALL_PORT);
        gearGrabber = new CANTalon(GEAR_GRABBER_PORT);
        gearGrabber.setFeedbackDevice(FeedbackDevice.AnalogPot);
        gearGrabber.setForwardSoftLimit(GEAR_UP_ANGLE);
        gearGrabber.setReverseSoftLimit(GEAR_DOWN_ANGLE);
        gearGrabber.setAllowableClosedLoopErr(3);
        gearIntake = new Victor(GEAR_INTAKE_PORT);
        gearTopLimit = new DigitalInput(GEAR_TOP_LIMIT_PORT);
        gearBottomLimit = new DigitalInput(GEAR_BOTTOM_LIMIT_PORT);
        shootertalon1 = new CANTalon(RIGHT_SHOOTER_PORT);
        shootertalon1.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        shootertalon1.reverseOutput(true);
        shootertalon2 = new CANTalon(LEFT_SHOOTER_PORT);
        shootertalon2.reverseOutput(true);
        turretLoader = new Victor(LOADER_RELAY_PORT);
       // turretLoader.set(Relay.Value.kOn);
        climber = new CANTalon(CLIMBER_PORT);
        agitator = new Victor(AGITATOR_PORT);
        
        betaBot = new DigitalInput(BETA_BOT_IO_PORT);
        
        
        
        //driveEncoder = new Encoder(DRIVE_ENCODER_PORT_1, DRIVE_ENCODER_PORT_2);9 nik
        //driveEncoder.setReverseDirection(false);
        //driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
        
        gear_auto_pos = 2;
        
        turretTable = NetworkTable.getTable("GRIP/811Contour");
        gearTable = NetworkTable.getTable("GRIP/811GearContours");
        
    }
    
}
