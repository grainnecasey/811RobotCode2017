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
		
		CANTalon intakeBall = RobotMap.intakeBall;

	    public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    }
	    
	    public void intakeJoyControl() {  		
	    	
	    }
	    
	    public void intake() 
	    {
	    	intakeBall.set(INTAKE_SPEED);
	    }
	    
	    public void intakeStop()
	    {
    		intakeBall.set(0);
	    }
	    public void intakeBack()
	    {
    		intakeBall.set(-INTAKE_SPEED); 
	    }
	    
	}


