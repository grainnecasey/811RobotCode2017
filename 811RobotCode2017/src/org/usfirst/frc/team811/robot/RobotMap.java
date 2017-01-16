package org.usfirst.frc.team811.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.interfaces.Gyro;

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
	
	public static SpeedController drivefrontright;
    public static SpeedController drivefrontleft;
    public static SpeedController drivebackleft;
    public static SpeedController drivebackright;
    public static SpeedController intakeball;
    public static SpeedController intakegear;
    public static SpeedController highshooter;
    public static SpeedController climber;
    public static SpeedController turret;
    public static Encoder driveEncoder;
    public static RobotDrive driveTrain;
    public static AnalogGyro driveGyro;
    public static AHRS ahrs;

    public void init() {
    	joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
    	drivefrontright = new Talon(FRONT_RIGHT_PORT);
        drivefrontleft = new Talon(FRONT_LEFT_PORT);
        drivebackleft = new Talon(REAR_LEFT_PORT);
        drivebackright = new Talon(REAR_RIGHT_PORT);
        driveTrain = new RobotDrive(drivefrontleft, drivebackleft, drivefrontright, drivebackright);
        driveEncoder = new Encoder(DRIVE_ENCODER_PORT_1, DRIVE_ENCODER_PORT_2);
        driveEncoder.setReverseDirection(false);
        driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
        ahrs = new AHRS(SPI.Port.kMXP);
        climber = new Talon(CLIMBER_BUTTON);
        turret = new Talon(TURRET_BUTTON);
        intakeball = new Talon(INTAKE_BALL_BUTTON);
        intakegear =new Talon(INTAKE_GEAR_BUTTON);
        highshooter = new Talon(HIGHSHOOTER_BUTTON);
    }
    
}
