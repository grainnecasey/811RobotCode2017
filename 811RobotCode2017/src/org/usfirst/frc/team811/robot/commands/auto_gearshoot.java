package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.DriverStation;

/**
 *
 */
public class auto_gearshoot extends CommandGroup {
    
    public  auto_gearshoot() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	
    	/* Drives up to gear station
    	 * Lines up to drop gear and drops it off
    	 * Drive back a bit
    	 * Strafe to shooting position
    	 * Lineup to shoot
    	 * Shoot
    	 */
    	
    	addSequential(new drive_auto(-80)); 
    	addSequential(new wait(2));
    	
    	switch (RobotMap.gear_auto_pos) {
    	case 1: 
    		addSequential(new turn_auto(45));
    		
	    	addSequential(new vision_strafe_auto()); 
	    	addSequential(new drive_auto(-13));
	    	addSequential(new wait(2)); 
	    	addSequential(new drive_auto(13));
	    	addSequential(new strafe_auto(130));
    		addSequential(new drive_auto(64));
    		addSequential(new shoot_compilation());
    		break;
    	case 2:
    		addSequential(new turn_auto(0));
    		//addSequential(new wait(2)); 
	    	addSequential(new vision_strafe_auto()); 
	    	addSequential(new drive_auto(-13)); 
	    	addSequential(new wait(2)); 
	    	addSequential(new drive_auto(-24));
    		addSequential(new strafe_auto(85));
    		//maybe rotate a bit to see
    		addSequential(new shoot_compilation());
    		break;
    	case 3:
    		addSequential(new turn_auto(-45));
    		//addSequential(new wait(2)); 
	    	addSequential(new vision_strafe_auto()); 
	    	addSequential(new drive_auto(-13)); 
	    	addSequential(new wait(2)); 
	    	addSequential(new drive_auto(13));
	    	addSequential(new turn_auto(45));
	    	addSequential(new lidar_to_distance()); //TODO
    		break;
    	default:
    		addSequential(new turn_auto(0));
    		//addSequential(new wait(2)); 
	    	addSequential(new vision_strafe_auto()); 
	    	addSequential(new drive_auto(-13)); 
	    	addSequential(new wait(2)); 
	    	addSequential(new drive_auto(-76));
    		addSequential(new turn_auto(90)); //rotate 90 degrees
    		addSequential(new shoot_compilation());
    	}
    	
    }
}