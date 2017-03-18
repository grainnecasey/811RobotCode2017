package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.RobotMap;
import org.usfirst.frc.team811.robot.commands.climb_joy_control;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem implements Config {

	Joystick joy2 = RobotMap.joystick2;
	
	CANTalon climber = RobotMap.climber;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new climb_joy_control());
    }
    
    public void climbUp() {
    	climber.set(CLIMBER_SPEED);
    }
    
    public void climbOff() {
    	climber.set(0);
    }
    
    public void climbDown() {
    	climber.set(-CLIMBER_SPEED);
    }
    		
    public void climbWithJoy(){
    	if(joy2.getRawAxis(CLIMBER_AXIS) < -.2){
    		climber.set(joy2.getRawAxis(CLIMBER_AXIS)); //added this for testing to make the climber go backwards - Dan Czz
    	}else if(joy2.getRawAxis(CLIMBER_AXIS) > .2){
    		climber.set(joy2.getRawAxis(CLIMBER_AXIS));
    	}else{
    		climber.set(0);
    	}
    	
    }
}

