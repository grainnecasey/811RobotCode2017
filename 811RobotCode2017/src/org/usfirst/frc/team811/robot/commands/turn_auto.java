package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class turn_auto extends Command {

	int angle;
	//pos angle means turn counter clockwise
	
    public turn_auto(int degrees) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	angle = degrees;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

    	
		
		RobotMap.drivePID = new PIDController(1, .6, 3, RobotMap.ahrs
		, new PIDOutput() {
			public void pidWrite(double d) {
				SmartDashboard.putNumber("pid loop d", d);
				RobotMap.driveTrain.arcadeDrive(d, 0);
				SmartDashboard.putString("drive status", "in pidloop for driving");
			}
		});
		RobotMap.drivePID.setAbsoluteTolerance(1);
		RobotMap.drivePID.setSetpoint(RobotMap.ahrs.getYaw() - angle);
		RobotMap.drivePID.setOutputRange(-.5, .5);
		RobotMap.drivePID.setContinuous(true);
		RobotMap.drivePID.enable();

		SmartDashboard.putString("drive status", "drive forward auto"); 
		
	}

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	SmartDashboard.putNumber("driveget error", RobotMap.drivePID.getError());
		SmartDashboard.putNumber("drive get setpoint", RobotMap.drivePID.getSetpoint());
    	
    }
    	
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (RobotMap.drivePID.onTarget()) {
			SmartDashboard.putString("drive status", "pid on target");
			return true;
		} else if (RobotMap.drivePID.getError() == 0) {
			SmartDashboard.putString("drive status", "pid error 0");
			return true; } 
		else {
			return false;
		}
    }

    // Called once after isFinished returns true
    protected void end() {
    	
    	RobotMap.driveTrain.arcadeDrive(0, 0);
		RobotMap.drivePID.disable();
		
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	
    	RobotMap.driveTrain.arcadeDrive(0, 0);
		RobotMap.drivePID.disable();
		
    }
}
