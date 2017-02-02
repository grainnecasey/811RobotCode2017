package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class GearGrabber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	Relay gearGrabber = RobotMap.gearGrabber;
	DigitalInput gearTopLimit = RobotMap.gearTopLimit;
	DigitalInput gearBottomLimit = RobotMap.gearBottomLimit;
	Joystick joy2 = RobotMap.joystick2;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void gearJoyControl() {
    	if ((joy2.getRawAxis(5) > .2) && !(gearTopLimit.get())) {	//operator, right joystick, up/down
    		gearGrabber.set(Relay.Value.kForward);
    	} else if ((joy2.getRawAxis(5) < -.2) && !(gearBottomLimit.get())) {
    		gearGrabber.set(Relay.Value.kReverse);
    	} else {
    		gearGrabber.set(Relay.Value.kOff);
    	}
    }
    
    public void gearUp() {
    	gearGrabber.set(Relay.Value.kForward);
    }
    
    public void gearDown() {
    	gearGrabber.set(Relay.Value.kForward);
    }
    
    public void gearStop() {
    	gearGrabber.set(Relay.Value.kOff);
    }
    
    
}

