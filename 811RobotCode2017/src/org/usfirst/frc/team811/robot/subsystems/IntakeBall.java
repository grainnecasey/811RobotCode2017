package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;
import org.usfirst.frc.team811.robot.subsystems.Shooter;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Joystick;
	import edu.wpi.first.wpilibj.command.Subsystem;
	import edu.wpi.first.wpilibj.*;


	public class IntakeBall extends Subsystem implements Config {
		
		Joystick joy2 = RobotMap.joystick2;
		CANTalon intakeTalon = RobotMap.intakeTalon;
		Relay intakeBall = RobotMap.intakeBall;

	    public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    }
	    
	    public void intakeJoyControl() {
	    	
	    	if (joy2.getRawButton(INTAKE_IN_BUTTON)) {
	    		intakeBall.set(Relay.Value.kOn);
	    	} 
	    		else if (joy2.getRawButton(INTAKE_OFF_BUTTON)) {
	    		intakeBall.set(Relay.Value.kOff);
	    	} 
	    	
	    }
	    
	    public void intake() 
	    {
	    	intakeTalon.set(INTAKE_SPEED);
	    }
	    
	    public void intakeStop()
	    {
	    	intakeTalon.set(0);	    	
	    }
	    public void intakeBack()
	    {
	    	intakeTalon.set(-INTAKE_SPEED);
	    }
	    
	}


