package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearGrabber extends Subsystem implements Config{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	Victor gearGrabber = RobotMap.gearGrabber;
	DigitalInput gearTopLimit = RobotMap.gearTopLimit;
	DigitalInput gearBottomLimit = RobotMap.gearBottomLimit;
	Joystick joy2 = RobotMap.joystick2;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void gearJoyControl() {
    	if ((joy2.getRawAxis(5) > .2) && !(gearTopLimit.get())) {	//operator, right joystick, up/down
    		gearGrabber.set(GEAR_GRAB_SPEED);
    	} else if ((joy2.getRawAxis(5) < -.2) && !(gearBottomLimit.get())) {
    		gearGrabber.set(-GEAR_GRAB_SPEED);
    	} else {
    		gearGrabber.set(0);
    	}
    }
    
    public void gearUp() {
    	gearGrabber.set(GEAR_GRAB_SPEED);
    }
    
    public void gearDown() {
    	gearGrabber.set(-GEAR_GRAB_SPEED);
    }
    
    public void gearStop() {
    	gearGrabber.set(0);
    }
    
    
}

