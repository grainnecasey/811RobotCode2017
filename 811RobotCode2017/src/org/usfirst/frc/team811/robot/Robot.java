
package org.usfirst.frc.team811.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team811.robot.commands.*;
import org.usfirst.frc.team811.robot.subsystems.Agitator;
import org.usfirst.frc.team811.robot.subsystems.Climber;
import org.usfirst.frc.team811.robot.subsystems.Drive;
import org.usfirst.frc.team811.robot.subsystems.GearGrabber;
import org.usfirst.frc.team811.robot.subsystems.IntakeBall;
import org.usfirst.frc.team811.robot.subsystems.Lidar;
import org.usfirst.frc.team811.robot.subsystems.LidarController;
import org.usfirst.frc.team811.robot.subsystems.Shooter;
import org.usfirst.frc.team811.robot.subsystems.VisionGear;
import org.usfirst.frc.team811.robot.subsystems.VisionTurret;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot implements Config {

	public static Drive drive;
	
	public static OI oi;
	public static RobotMap robotMap;
	
	public static Shooter shooter;
	public static IntakeBall intake;
	public static Climber climber;
	public static Agitator agitator;
	public static GearGrabber geargrabber;
	public static VisionTurret visionTurret;
	public static VisionGear visionGear;
	public static LidarController lidarController;
	
    Command autonomousCommand;
    Command variableCommand;
    SendableChooser<Command> autoChooser;
    SendableChooser<Command> variableChooser;
    

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	/*
    	System.out.println("robotInit()");
    	robotMap = new RobotMap();
    	
		System.out.println("robotMap.init();");
    	*/
    	robotMap = new RobotMap();
    	robotMap.init();
    	
		shooter = new Shooter();
		drive = new Drive();
		intake = new IntakeBall();
		climber = new Climber();
		agitator = new Agitator();
		geargrabber = new GearGrabber();
		visionTurret = new VisionTurret();
		visionGear = new VisionGear();
		lidarController = new LidarController();
		
        autoChooser = new SendableChooser<Command>();
        autoChooser.addDefault("base line", new auto_base());
        autoChooser.addObject("just gear", new auto_gear());
        autoChooser.addObject("gear hopper", new auto_gearhopper());
        autoChooser.addObject("gear shoot", new auto_gearshoot());
        autoChooser.addObject("gear ready", new auto_gearready());
        autoChooser.addObject("hopper", new auto_hopper());
        autoChooser.addObject("shoot", new auto_shoot());
        autoChooser.addObject("do nothing", new auto_nothing());
        
        
        
        
        variableChooser = new SendableChooser<Command>();
        variableChooser.addDefault("gear middle", new vari_gear_middle() );
        variableChooser.addObject("Gear Right", new vari_gear_right());
        //variableChooser.addObject("Gear Middle", new vari_gear_middle());
        variableChooser.addObject("Gear Left", new vari_gear_left());

        SmartDashboard.putBoolean("beta bot?", RobotMap.betaBot.get());
        
        
        SmartDashboard.putData("Auto Mode Chooser", autoChooser);
        SmartDashboard.putData("Auto Variable Chooser", variableChooser);
        SmartDashboard.putNumber("manual center", 145);
        
        SmartDashboard.putNumber("gear_auto_pos", 2);

       // SmartDashboard.putNumber("gear choice", 2);
        oi = new OI();
       
        
        RobotMap.drivebackright.setEncPosition(0);
        RobotMap.drivebackright.configEncoderCodesPerRev((int) 1);

    }
	
	/**
     * This function is called once each time the robot enters Disabled mode.
     * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
     */
    public void disabledInit(){

    }
	
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
    public void autonomousInit() {
        autonomousCommand = (Command) autoChooser.getSelected();
        variableCommand = (Command) variableChooser.getSelected();
       //autonomousCommand.start();
        
		/* String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		switch(autoSelected) {
		case "My Auto":
			autonomousCommand = new MyAutoCommand();
			break;
		case "Default Auto":
		default:
			autonomousCommand = new ExampleCommand();
			break;
		} */
        
        RobotMap.gear_auto_pos = (int) SmartDashboard.getNumber("gear_auto_pos");
    	
    	// schedule the autonomous command (example)
        //if (variableCommand != null) variableCommand.start();
        if (autonomousCommand != null) autonomousCommand.start();
        RobotMap.drivebackright.setEncPosition(0);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
        Scheduler.getInstance().run();
    }

    public void teleopInit() {
		// This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to 
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) autonomousCommand.cancel();
        RobotMap.drivebackright.setEncPosition(0);
        
        //SmartDashboard.putNumber("gear_auto_pos", 2);
        RobotMap.gear_auto_pos = (int) SmartDashboard.getNumber("gear_auto_pos");
        //RobotMap.gearGrabber.setAnalogPosition(0);

    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        
//        SmartDashboard.putBoolean("top gear limit", RobotMap.gearTopLimit.get());
//        SmartDashboard.putBoolean("bottom gear limit", RobotMap.gearBottomLimit.get());
        SmartDashboard.putNumber("gyro yaw value", RobotMap.ahrs.getYaw());
        SmartDashboard.putNumber("drive enc val", RobotMap.drivebackright.getEncPosition());
        SmartDashboard.putNumber("drive enc corrected dist", RobotMap.drivebackright.getEncPosition() / DRIVE_DISTANCE_PER_PULSE);
        SmartDashboard.putNumber("gear pot Position val", RobotMap.gearGrabber.getPosition());
        SmartDashboard.putBoolean("gear is alive", RobotMap.gearGrabber.isAlive());
        SmartDashboard.putNumber("gear pot get", RobotMap.gearGrabber.get());
        //RobotMap.gear_auto_pos = (int)SmartDashboard.getNumber("gear_auto_pos_real");


    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
        LiveWindow.run();
    }
}
