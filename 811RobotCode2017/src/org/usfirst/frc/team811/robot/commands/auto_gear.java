package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	    	
	    	//int temp_gear_auto_pos = (int) SmartDashboard.getNumber("gear_auto_pos");
	    	
	    	switch (RobotMap.gear_auto_pos) {
	    	case 1: 
	    		addSequential(new drive_auto(-80)); //-65
		    	//addSequential(new wait(2));
	    		addSequential(new turn_auto(-66)); //-60
	    		//addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto()); 
		    	addSequential(new drive_auto(-67)); //-63
	    		break;
	    	case 2:
	    		addSequential(new drive_auto(-85)); //-50
		    	//addSequential(new wait(2));
	    		//addSequential(new turn_auto(0));
	    		//addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto()); 
		    	//addSequential(new drive_auto(-33)); // -33
	    		break;
	    	case 3:
	    		addSequential(new drive_auto(-70)); //97
		    	//addSequential(new wait(2));
	    		addSequential(new turn_auto(55));//50
	    		//addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto()); 
		    	addSequential(new drive_auto(-70)); //47
	    		break;
	    	default:
	    		addSequential(new drive_auto(-55)); 
		    	//addSequential(new wait(2));
	    		//addSequential(new turn_auto(0));
	    		//addSequential(new wait(2)); 
		    	//addSequential(new vision_strafe_auto()); 
		    	addSequential(new drive_auto(-25)); 
	    	}
	    	
	    	
	    }

}
