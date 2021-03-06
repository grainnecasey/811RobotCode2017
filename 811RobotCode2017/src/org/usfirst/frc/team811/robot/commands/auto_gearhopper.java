package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class auto_gearhopper extends CommandGroup {
	    
	    public  auto_gearhopper() {
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
	    	
	    	/* Go to gear and line up and place
	    	 * Cross baseline
	    	 * Activate hopper
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
		    	addSequential(new turn_auto(-45));
		    	addSequential(new strafe_auto(-50));
	    		break;
	    	case 2:
	    		addSequential(new turn_auto(0));
	    		//addSequential(new wait(2)); 
		    	addSequential(new vision_strafe_auto()); 
		    	addSequential(new drive_auto(-13)); 
		    	addSequential(new wait(2)); 
	    		break;
	    	case 3:
	    		addSequential(new turn_auto(-45));
	    		//addSequential(new wait(2)); 
		    	addSequential(new vision_strafe_auto()); 
		    	addSequential(new drive_auto(-13)); 
		    	addSequential(new wait(2)); 
		    	addSequential(new drive_auto(13));
		    	addSequential(new turn_auto(45));
		    	addSequential(new strafe_auto(50));
	    		break;
	    	default:
	    		addSequential(new turn_auto(0));
	    		//addSequential(new wait(2)); 
		    	addSequential(new vision_strafe_auto()); 
		    	addSequential(new drive_auto(-13)); 
		    	addSequential(new wait(2)); 
	    	}
	    	
	    	
	    	
	    }

}
