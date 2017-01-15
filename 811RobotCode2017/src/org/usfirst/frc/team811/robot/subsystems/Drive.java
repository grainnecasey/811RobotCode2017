package org.usfirst.frc.team811.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;

import org.usfirst.frc.team811.robot.*;
import org.usfirst.frc.team811.robot.commands.drive_w_joystick;

import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class Drive extends Subsystem implements Config {
    
    SpeedController frontright = RobotMap.drivefrontright;
    SpeedController frontleft = RobotMap.drivefrontleft;
    SpeedController backleft = RobotMap.drivebackleft;
    SpeedController backright = RobotMap.drivebackright;
    Joystick joystick1 = RobotMap.joystick1;
    RobotDrive driveTrain = RobotMap.driveTrain;
    Encoder driveEncoder = RobotMap.driveEncoder;
    AHRS ahrs = RobotMap.ahrs;
    double speedScale = 1;
    double inputY;
    double inputX;
    double inputS;

	public void driveWithJoy {
	    	
	    	boolean strafe = false;
	    	double correction = 0;
	    	
	    	if (Math.abs(joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS)) <= 0.1 && Math.abs(joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS)) <= 0.1) {
	    		strafe = true;
	    		correction = 0;
	    	}
	    	if ((Math.abs(joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS)) >=0.3 || Math.abs(joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS)) >= 0.3) && strafe ) {
	    		//gyro1.getAngle();
	    		ahrs.reset();
	    		strafe = false;
	    	}
	    	 
	    	if (Math.abs(joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS)) >= 0.3 || Math.abs(joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS)) >= 0.3) {
	    		correction = 0; // gyro1.getAngle();
	    	}
	    	
	    	if (joystick1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS) > 0.3 || joystick1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS) < -0.3) {
	    		inputY = joystick1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS);
	    	} else {
	    		inputY = 0.0;
	    	}
	    	if (joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS) > 0.3) {
	    		inputS = joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS);
	    	} else if (joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS) > 0.3) {
	    		inputS = joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS) * -1;
	    	} else {	
	    		inputS = 0.0;
	    	}
	    	if (joystick1.getRawAxis(DRIVE_X_JOYSTICK_AXIS) > 0.3 || joystick1.getRawAxis(DRIVE_X_JOYSTICK_AXIS) < -0.3 ) {
	    		inputX = joystick1.getRawAxis(DRIVE_X_JOYSTICK_AXIS);
	    	} else {
	    		inputX = 0.0;
	    	}
	    	
	    	if (joystick1.getRawButton(6)) {
	    		speedScale = .5;
	    	} else {
	    		speedScale = 1;
	    	}
	    	
	    	driveTrain.mecanumDrive_Cartesian(inputS*speedScale, inputY*speedScale, inputX*speedScale + correction * -1, 0);
	    	
	}
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new drive_w_joystick());
    }
    
    
}

