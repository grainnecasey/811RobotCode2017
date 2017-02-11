package org.usfirst.frc.team811.robot;

import com.ctre.CANTalon;
import com.ctre.CANTalon.FeedbackDevice;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.networktables.NetworkTable;

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
    public static CANTalon agitator;
    public static CANTalon climber;
    
    public static Relay intakeBall;
    public static Relay turretLoader;
    public static Relay gearGrabber;
    
    public static DigitalInput gearTopLimit;
    public static DigitalInput gearBottomLimit;
    public static RobotDrive driveTrain;
    public static AnalogGyro driveGyro;
    public static AHRS ahrs;
    public static Encoder driveEncoder;
    
    public static PIDController drivePID;
    public static PIDController visionTurretController;
    public static PIDController visionGearController;
    
    
    public static NetworkTable turretTable;
    public static NetworkTable gearTable;
    
    public void init() {
    	joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
        
    	drivefrontright = new CANTalon(FRONT_RIGHT_PORT);
    	drivefrontright.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        drivefrontleft = new CANTalon(FRONT_LEFT_PORT);
        drivebackleft = new CANTalon(REAR_LEFT_PORT);
        drivebackright = new CANTalon(REAR_RIGHT_PORT);
        driveTrain = new RobotDrive(drivefrontleft, drivebackleft, drivefrontright, drivebackright);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kFrontRight, true);
        driveTrain.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);
        ahrs = new AHRS(SPI.Port.kMXP);
        turret = new CANTalon(TURRET_PORT);
        turret.setFeedbackDevice(FeedbackDevice.AnalogPot);
        //intakeBall = new Relay(INTAKEBALL_PORT);
        //gearGrabber = new Relay(GEAR_GRABBER_PORT);
        gearTopLimit = new DigitalInput(GEAR_TOP_LIMIT_PORT);
        gearBottomLimit = new DigitalInput(GEAR_BOTTOM_LIMIT_PORT);
        shootertalon1 = new CANTalon(RIGHT_SHOOTER_PORT);
        shootertalon1.setFeedbackDevice(FeedbackDevice.AnalogEncoder);
        shootertalon2 = new CANTalon(LEFT_SHOOTER_PORT);
        turretLoader = new Relay(LOADER_RELAY_PORT);
        turretLoader.set(Relay.Value.kOn);
        climber = new CANTalon(CLIMBER_PORT);
        
        
        agitator = new CANTalon(AGITATOR_PORT);
        
        //driveEncoder = new Encoder(DRIVE_ENCODER_PORT_1, DRIVE_ENCODER_PORT_2);
        //driveEncoder.setReverseDirection(false);
        //driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
        
        turretTable = NetworkTable.getTable("GRIP/811Contour");
        gearTable = NetworkTable.getTable("GRIP/811GearContours");
        
    }
    
}
