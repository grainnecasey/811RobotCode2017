package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class vision_strafe_auto extends Command {

    public vision_strafe_auto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.visionGear);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.visionGear.strafeTunePID();
    	SmartDashboard.putString("gear vision status", "initializing");
    	Robot.visionGear.gearStrafeCenter();
    	Robot.visionGear.setCamSource();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.visionGear.currentCen();
    	Robot.visionGear.setCamSource();
    	SmartDashboard.putString("gear vision status", "running");
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return false;//Robot.visionGear.visionGearController.onTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.visionGear.visionGearController.disable();
    	RobotMap.driveTrain.arcadeDrive(0, 0);
    	SmartDashboard.putString("gear vision status", "ended");
    	//RobotMap.drivePID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.visionGear.visionGearController.disable();
    	RobotMap.driveTrain.arcadeDrive(0, 0);
    	SmartDashboard.putString("gear vision status", "ended");
    	//RobotMap.drivePID.disable();
    }
}
