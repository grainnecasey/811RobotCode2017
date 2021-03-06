package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Config;
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
public class drive_auto extends Command implements Config {

	double distance;
	double distanceInInches;
	double conversion = 53.2; 

	public drive_auto(double inches) {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.drive);
		distance = inches;
		distanceInInches =  Math.PI * inches;
		
		if (RobotMap.betaBot.get()==false) {
			conversion = 47.8;
		}
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		//RobotMap.driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
		
		//setTimeout(5);
		//RobotMap.driveEncoder.reset();
		RobotMap.drivebackright.setEncPosition(0);
		RobotMap.drivebackright.reverseSensor(false);
		
		//double p = SmartDashboard.getDouble("kP");
		//double i = SmartDashboard.getDouble("kI");
		//double d = SmartDashboard.getDouble("kD");
		
		//RobotMap.driveEncoder.setReverseDirection(true);
		//RobotMap.driveEncoder.setDistancePerPulse(1/40);
		
		//1 inch = 47.8 encoder ticks 
		
		//double encInches =  -1 * RobotMap.drivebackright.getEncPosition() / 47.8;
		
		RobotMap.drivePID = new PIDController(0.03, 0, 0, new PIDSource() {  //1, .6, 3
			
			public double pidGet()
			{
				SmartDashboard.putNumber("Auto value",
						-1 * RobotMap.drivebackright.getEncPosition() / 53.2);	//47.8 beta
				return -1 * RobotMap.drivebackright.getEncPosition() / 53.2;
			}
			@Override
			public void setPIDSourceType(PIDSourceType pidSource) {
			}

			@Override
			public PIDSourceType getPIDSourceType() {
				return PIDSourceType.kDisplacement;
			}
		}, new PIDOutput() {
			public void pidWrite(double d) {
				SmartDashboard.putNumber("pid loop d", -d);
				RobotMap.driveTrain.arcadeDrive(0, -d);
				SmartDashboard.putString("drive status", "in pidloop for driving");
			}
		});
		RobotMap.drivebackright.setEncPosition(0);
		RobotMap.drivePID.setAbsoluteTolerance(1);
		RobotMap.drivePID.setSetpoint(distance);
		RobotMap.drivePID.setOutputRange(-.7, .7);
		RobotMap.drivePID.setContinuous(true);
		RobotMap.drivePID.enable();

		SmartDashboard.putString("drive status", "drive forward auto"); 
		
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		
		SmartDashboard.putNumber("driveget error", RobotMap.drivePID.getError());
		SmartDashboard.putNumber("drive get setpoint", RobotMap.drivePID.getSetpoint());

		//Robot.drive.driveAuto(distance);
		
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
		
		//return RobotMap.drivePID.onTarget();// || isTimedOut();
		//return (RobotMap.driveEncoder.getDistance() >= distance);
		
		//return false;
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
		SmartDashboard.putString("drive status", "was interrupted");
		
	}
}
