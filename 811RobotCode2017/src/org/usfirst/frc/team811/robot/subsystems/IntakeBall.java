package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;
import org.usfirst.frc.team811.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Joystick;
	import edu.wpi.first.wpilibj.command.Subsystem;
	import edu.wpi.first.wpilibj.*;


	public class IntakeBall extends Subsystem implements Config {
		
		Joystick joy2 = RobotMap.joystick2;
		
		SpeedController intakeTalon = RobotMap.intakeball;
		//DigitalInput intakeLimit = RobotMap.intakeLimit;
	    
	    // Put methods for controlling this subsystem
	    // here. Call these from Commands.

	    public void initDefaultCommand() {
	        // Set the default command for a subsystem here.
	        //setDefaultCommand(new MySpecialCommand());
	    }
	    
	    public void intakeJoyControl() {
	    	
	    	if (joy2.getRawButton(INTAKE_IN_BUTTON)) {
	    		//if (!intakeLimit.get()) {
	    		if (!IntakeBall.get()) {
	    			intakeTalon.set(INTAKE_SPEED);} 
	    		else {
	    			if (Robot.shooter.isFullSpeed()) {
	    				intakeTalon.set(INTAKE_SPEED);
	    			}
	    		}
	    	} 
	    		else if (joy2.getRawButton(INTAKE_OUT_BUTTON)) {
	    		//if (intakeLimit.get() && !Robot.shooter.isFullSpeed()) {
	    		if (!IntakeBall.get() && !Robot.shooter.isFullSpeed()) {
	    			intakeTalon.set(-INTAKE_SPEED);
	    		}
	    	} 
	    }

	    //Methods .isFullSpeed and .get need to have the Shooter.java subsystem
	    public void intake() {
	    	intakeTalon.set(INTAKE_SPEED);
	    }
	    
	    public void stopIntake() {
	    	intakeTalon.set(0);
	    }
	    
	    public void intakeBack() {
	    	intakeTalon.set(-INTAKE_SPEED);
	    }
	}


