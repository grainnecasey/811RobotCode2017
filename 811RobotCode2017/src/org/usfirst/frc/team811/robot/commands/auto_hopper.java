package org.usfirst.frc.team811.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class auto_hopper extends CommandGroup {
	    
	    public  auto_hopper() {
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
	    	
	    	/* Cross baseline
	    	 * Activate hopper
	    	 */
	    	
	    	addSequential(new drive_auto(10)); // TODO actually move to the hopper
	    	//addSequential(new strafe_auto(1)); // TODO
	    }

}
