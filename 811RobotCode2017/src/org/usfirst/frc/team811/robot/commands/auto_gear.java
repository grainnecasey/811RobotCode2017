package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class auto_gear extends CommandGroup {
	    
	    public  auto_gear() {
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
	    	
	    	/* Drive up to vision
	    	 * Line up to gear and place gear
	    	 * Cross baseline
	    	 */
	    	
	    	
	    	
	    	switch (RobotMap.gear_auto_pos) {
	    	case 1: 
	    		addSequential(new drive_auto(-90)); 
		    	addSequential(new wait(2));
	    		addSequential(new turn_auto(-55));
	    		//addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto2()); 
		    	addSequential(new drive_auto(-25)); 
	    		break;
	    	case 2:
	    		addSequential(new drive_auto(-80)); 
		    	addSequential(new wait(2));
	    		addSequential(new turn_auto(0));
	    		//addSequential(new wait(2)); 
		    	addSequential(new vision_strafe_auto2()); 
		    	addSequential(new drive_auto(-13)); 
	    		break;
	    	case 3:
	    		addSequential(new drive_auto(-90)); 
		    	addSequential(new wait(2));
	    		addSequential(new turn_auto(55));
	    		//addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto2()); 
		    	addSequential(new drive_auto(-25)); 
	    		break;
	    	default:
	    		addSequential(new drive_auto(-85)); 
		    	addSequential(new wait(2));
	    		addSequential(new turn_auto(0));
	    		addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto2()); 
		    	addSequential(new drive_auto(-13)); 
	    	}
	    	
	    	
	    }

}
