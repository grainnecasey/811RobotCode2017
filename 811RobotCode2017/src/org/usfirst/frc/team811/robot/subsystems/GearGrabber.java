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

	Relay gearIntake = RobotMap.intakeGear;
	DigitalInput gearTopLimit = RobotMap.gearTopLimit;
	DigitalInput gearBottomLimit = RobotMap.gearBottomLimit;
	Joystick joy2 = RobotMap.joystick2;

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void gearJoyControl() {
    	if ((joy2.getRawAxis(5) > .2) && !(gearTopLimit.get())) {	//operator, right joystick, up/down
    		gearIntake.set(Relay.Value.kForward);
    	} else if ((joy2.getRawAxis(5) < -.2) && !(gearBottomLimit.get())) {
    		gearIntake.set(Relay.Value.kReverse);
    	} else {
    		gearIntake.set(Relay.Value.kOff);
    	}
    }
    
    public void gearUp() {
    	gearIntake.set(Relay.Value.kForward);
    }
    
    public void gearDown() {
    	gearIntake.set(Relay.Value.kForward);
    }
    
    public void gearStop() {
    	gearIntake.set(Relay.Value.kOff);
    }
    
    
}

