package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem implements Config{
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	Victor agitator = RobotMap.agitator;
	

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void agOn() {
    	agitator.set(AGITATOR_SPEED);
    }
    
    public void agOff() {
    	agitator.set(0);
    }
}

