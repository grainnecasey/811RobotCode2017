package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.RobotMap;


import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem implements Config{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Victor agitator = RobotMap.agitator;
	Joystick joy2 = RobotMap.joystick2;
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        
    }
    
    public void agOn() {
    	agitator.set(AGITATOR_SPEED);
    }
    
    public void agOff() {
    	agitator.set(0);
    }
    
    public void agForward() {
    	agitator.set(AGITATOR_SPEED);
    	
    }
    
    public void agReverse() {
    	agitator.set(-AGITATOR_SPEED);
    }
}

