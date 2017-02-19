package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class vision_turn_auto extends Command {

    public vision_turn_auto() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.visionTurret);
    	requires(Robot.drive);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	SmartDashboard.putString("vision turret status", "initizing command");
    	Robot.visionTurret.tunePID();
    	Robot.visionTurret.gyroTurn();
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.visionTurret.indexOfContour();
    	SmartDashboard.putString("vision turret status", "exec command");
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return Robot.visionTurret.isOnTarget();
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	Robot.visionTurret.visionTurretController.disable();
    	SmartDashboard.putString("vision turret status", "PID disabled");
    	//RobotMap.driveTrain.mecanumDrive_Cartesian(0, 0, 0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.visionTurret.visionTurretController.disable();
    	SmartDashboard.putString("vision turret status", "PID disabled");
    	//RobotMap.driveTrain.mecanumDrive_Cartesian(0, 0, 0, 0);
    }
}
