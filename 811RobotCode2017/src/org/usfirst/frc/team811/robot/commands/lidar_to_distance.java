package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class lidar_to_distance extends Command {

    public lidar_to_distance() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	requires(Robot.lidarController);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.lidarController.lidarPID.reset();
    	RobotMap.ahrs.reset();
    	Robot.lidarController.lidarTunePID();
    	SmartDashboard.putString("lidar controller", "initializing");
    	Robot.lidarController.initVari();
    	Robot.lidarController.setSetNorm();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return Robot.lidarController.lidarPID.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.lidarController.lidarPID.disable();
    	RobotMap.driveTrain.arcadeDrive(0, 0);
    	SmartDashboard.putString("lidar controller status", "ended");
    	//RobotMap.drivePID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
