package org.usfirst.frc.team811.robot;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
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
    public static CANTalon intakeTalon;
    public static CANTalon agitator;
    
    public static Relay intakeBall;
    public static Relay turretLoader;
    public static Relay intakeGear;
    public static Relay climber;
    
    public static DigitalInput gearTopLimit;
    public static DigitalInput gearBottomLimit;
    public static NetworkTable visionTable;
    public static RobotDrive driveTrain;
    public static AnalogGyro driveGyro;
    public static AHRS ahrs;

    public void init() {
    	joystick1 = new Joystick(1);
        joystick2 = new Joystick(2);
        
    	drivefrontright = new CANTalon(FRONT_RIGHT_PORT);
        drivefrontleft = new CANTalon(FRONT_LEFT_PORT);
        drivebackleft = new CANTalon(REAR_LEFT_PORT);
        drivebackright = new CANTalon(REAR_RIGHT_PORT);
        driveTrain = new RobotDrive(drivefrontleft, drivebackleft, drivefrontright, drivebackright);
        ahrs = new AHRS(SPI.Port.kMXP);
        turret = new CANTalon(TURRET_PORT);
        intakeBall = new Relay(INTAKE_BALL_PORT);
        intakeGear = new Relay(INTAKE_GEAR_PORT);
        gearTopLimit = new DigitalInput(GEAR_TOP_LIMIT_PORT);
        gearBottomLimit = new DigitalInput(GEAR_BOTTOM_LIMIT_PORT);
        shootertalon1 = new CANTalon(RIGHT_SHOOTER_PORT);
        shootertalon2 = new CANTalon(LEFT_SHOOTER_PORT);
        turretLoader = new Relay(LOADER_RELAY_PORT);
        climber = new Relay(CLIMBER_BUTTON);
        
        visionTable = NetworkTable.getTable("GRIP/811Contour");
        agitator = new CANTalon(AGITATOR_PORT);
    }
    
}
