package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class gear_up extends Command {

    public gear_up() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.geargrabber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.geargrabber.gearUp();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !RobotMap.gearTopLimit.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.geargrabber.gearStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.geargrabber.gearStop();
    }
}
