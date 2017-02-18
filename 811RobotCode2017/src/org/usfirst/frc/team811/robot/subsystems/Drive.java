package org.usfirst.frc.team811.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;

import org.usfirst.frc.team811.robot.*;
import org.usfirst.frc.team811.robot.commands.drive_w_joystick;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class Drive extends Subsystem implements Config {
    
    CANTalon frontright = RobotMap.drivefrontright;
    CANTalon frontleft = RobotMap.drivefrontleft;
    CANTalon backleft = RobotMap.drivebackleft;
    CANTalon backright = RobotMap.drivebackright;
    Joystick joystick1 = RobotMap.joystick1;
    RobotDrive driveTrain = RobotMap.driveTrain;
    AHRS ahrs = RobotMap.ahrs;
    double speedScale = 1;
    double inputY;
    double inputX;
    double inputS;
    boolean strafing = false;
    
    boolean m_trackAngle = false;
    double m_lastAngle;

	public void driveWithJoy() {
	    	
	    	boolean strafe = false;
	    	double correction = 0;
	    	int strafeCount = 0;
	    	
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
	    		correction = RobotMap.ahrs.getYaw(); // gyro1.getAngle();
	    	}
	    	
	    	if (joystick1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS) > 0.3 || joystick1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS) < -0.3) {
	    		inputY = -joystick1.getRawAxis(DRIVE_Y_JOYSTICK_AXIS);
	    	} else {
	    		inputY = 0.0;
	    	}
	    	if (joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS) > 0.3) {
	    		inputS = joystick1.getRawAxis(DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS);
	    		strafing = true;
	    		strafeCount++;
	    		
	    	} else if (joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS) > 0.3) {
	    		inputS = joystick1.getRawAxis(DRIVE_STRAFING_LEFT_JOYSTICK_AXIS) * -1;
	    		strafing = true;
	    		strafeCount++;
	    	} else {	
	    		inputS = 0.0;
	    		strafing = false;
	    		strafeCount = 0;
	    	}
	    	if (joystick1.getRawAxis(DRIVE_X_JOYSTICK_AXIS) > 0.3 || joystick1.getRawAxis(DRIVE_X_JOYSTICK_AXIS) < -0.3 ) {
	    		inputX = -joystick1.getRawAxis(DRIVE_X_JOYSTICK_AXIS);
	    	} else {
	    		inputX = 0.0;
	    	}
	    	
	    	if (joystick1.getRawButton(6)) {
	    		speedScale = .5;
	    	} else {
	    		speedScale = 1;
	    	}
	    	
	    	if (strafeCount == 1) {
	    		
	                 m_lastAngle=ahrs.getYaw();

	         } 
	    	
	    	
	         //Robot.driveTrain.mecanumDrive(x,y,rotation,gyroAngle);
	    	double errVal=m_lastAngle-ahrs.getYaw();
	    	double P=0.02;

			if (inputX == 0 && strafing) {
				
				inputX= P * errVal;  //proportional rotation injected to counter error
			
	    	
	    }

	    	//System.out.println(inputS + "  " + inputY + "  " + inputX);
	    	driveTrain.mecanumDrive_Cartesian(inputS*speedScale, -1 *inputY*speedScale, -1 * inputX*speedScale, 0);
	    	m_trackAngle = false;
	    	
	}
	
	public void strafeAuto(double strafeVal) {
		
		double rotation = 0;
		
            //User does not want rotation, If this just happened, note current angle 
            
            double m_lastAngle= ahrs.getYaw();
            

            //by here we are sure m_trackAngle is true and m_lastAngle
            //is our target angle, either just set above or on some previous
            //pass.

            // programatically inject rotation if
            // there is error.  Use P part of PID only
            double errVal=m_lastAngle-ahrs.getYaw();
            double P=0.02;

            rotation= P * errVal;  //proportional rotation injected to counter error
        
        

        driveTrain.mecanumDrive_Cartesian(strafeVal,0,rotation,ahrs.getYaw());

		
	}

    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new drive_w_joystick());
    }
    
    
}

