package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.RobotMap;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.*;


public class Shooter extends Subsystem implements Config {
	
	Joystick joy2 = RobotMap.joystick2;
	
	CANTalon shooterTalon1 = RobotMap.shootertalon1;
	CANTalon shooterTalon2 = RobotMap.shootertalon2;
	NetworkTable visionTable = RobotMap.turretTable;
	

	
	boolean shooting = false;
	double shootingTime = 0;
	double shootingEndTime = 0;
	
    
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    }
    
    public void shootWithJoy() {
    		shoot();
    }
    
    public boolean isFullSpeed() {
    	
    	shooterTalon1.configEncoderCodesPerRev(SHOOTER_DISTANCE_PER_PULSE);
    	//1 revolution = about 260 
    	
    	return shooterTalon1.getEncVelocity() + shooterTalon2.getEncVelocity() >= (SHOOTER_FULL_SPEED_RATE * 2);
    	
    	//return (shootingTime + SHOOTER_WAIT_TIME > System.currentTimeMillis()) && !(shootingEndTime + SHOOTER_END_WAIT_TIME > System.currentTimeMillis());
    }
    
    public void shoot() {
    	
    	shootingTime = System.currentTimeMillis();
    	shooterTalon1.set(SHOOTER_SPEED);
    	shooterTalon2.set(SHOOTER_SPEED);
    	
    	//while (shootingTime + 3000 > System.currentTimeMillis()) {
    		
    	//}
    	//RobotMap.intakeTalon.set(INTAKE_SPEED);

    	
    }
    
    public void distanceShoot(double d) {
    	
    	//get distance from camera
    	
    	//insert equation thing
    	double smartDashDistance = SmartDashboard.getNumber("shooter distance", 1);
    	double smartDashSpeed = SmartDashboard.getNumber("shooter speed", 1);
    	
    	double distance = smartDashDistance;
    	double lateralVelocity = smartDashSpeed; //meters per second
    	
    	//lateral velocity = 12.38394617 * distance - 17.05999456
    	
    	double time = Math.pow(distance/lateralVelocity, 2);
    	
    	double speed;
    	
    	double degree = Math.toRadians(42); //angle in radians
    	//TODO maybe 48
    	double sinDeg = Math.sin(degree);
    	
    	double radius = .0762; 	//.046736
    	
    	speed = ((2.4638 + (4.9 * time))/((distance/lateralVelocity) * sinDeg * 2 * radius * Math.PI)) * 60;
    	SmartDashboard.putNumber("speed", speed);
    	
    	double rps = speed/60;
    	SmartDashboard.putNumber("rps", rps);
    	
    	double speedScale = rps/-1900;
    	SmartDashboard.putNumber("speedScale", speedScale);
    	
    	shooterTalon1.set(speedScale);
    	shooterTalon2.set(speedScale);
    	//shooterTalon2.set(-speedScale);
    	
    	//if (!intakeLimit.get()) {
		//	shootingEndTime = System.currentTimeMillis();
		//}
    	
    	if (shot()) {
    		shooterTalon1.set(0);
    		shooterTalon2.set(0);
    		//shooterTalon2.set(0);
    	}
    	
    }
    
    public void stopShooter() {
    	shooterTalon1.set(0);
    	shooterTalon2.set(0);
    	//shooterTalon2.set(0);
    }
    
    
    public boolean shot() {
    	return shootingEndTime + SHOOTER_END_WAIT_TIME > System.currentTimeMillis();
    }
}