package org.usfirst.frc.team811.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.usfirst.frc.team811.robot.*;
import org.usfirst.frc.team811.robot.commands.drive_w_joystick;

/**
 *
 */
public class Drive extends Subsystem {
    
    SpeedController frontright = RobotMap.drivefrontright;
    SpeedController frontleft = RobotMap.drivefrontleft;
    SpeedController backleft = RobotMap.drivebackleft;
    SpeedController backright = RobotMap.drivebackright;
    Joystick joystick1 = RobotMap.joystick1;
    RobotDrive driveTrain = RobotMap.driveTrain;
    Encoder driveEncoder = RobotMap.driveEncoder;

	public void driveWithJoy {
	    	
	    	boolean strafe = false;
	    	
	    	
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new drive_w_joystick());
    }
    
    
}

