package org.usfirst.frc.team811.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

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
    	
    	addSequential(new drive_auto(10));
    	//addSequential(new vision_gear()); // TODO
    	addSequential(new drive_auto(-5));
    	//addSequential(new strafe_auto(5)); // TODO
    	//addSequential(new vision_shoot()); // TODO 
    	addSequential(new shoot_shoot());
    }
}