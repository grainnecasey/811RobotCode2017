package org.usfirst.frc.team811.robot.commands;

import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class vision_strafe_auto2 extends Command {

	double distance;
	double distanceInInches;
	double rotation;
	
	double m_lastAngle;
	
    public vision_strafe_auto2() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_lastAngle= RobotMap.ahrs.getYaw();
    	
    	
    	//RobotMap.driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
		
		//setTimeout(5);
		//RobotMap.driveEncoder.reset();
		RobotMap.drivebackright.setEncPosition(0);
		RobotMap.drivebackright.reverseSensor(false);
		
		double dif = (130 - Robot.visionGear.currentCen()) / -.264;
		SmartDashboard.putNumber("vision strafe dif", dif);
		
		//RobotMap.driveEncoder.setReverseDirection(true);
		//RobotMap.driveEncoder.setDistancePerPulse(1/40);
		
		//1 inch = 47.8 encoder ticks 
		
		//double encInches =  -1 * RobotMap.drivebackright.getEncPosition() / 47.8;
		
		//-236 + .264x
		
		double P = SmartDashboard.getNumber("kP"); // 0.03
		double I = SmartDashboard.getNumber("kI"); // 0.0
		double D = SmartDashboard.getNumber("kD"); // 0.0
		double F = SmartDashboard.getNumber("kF");
       
          //proportional rotation injected to counter error
		
		
		RobotMap.drivePID = new PIDController(P, I, D, new PIDSource() {
			
			public double pidGet()
			{
				SmartDashboard.putNumber("pixels traveled (by encoder)",
						-1 * (RobotMap.drivebackright.getEncPosition()));
				return -1 *(RobotMap.drivebackright.getEncPosition());
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
				SmartDashboard.putNumber("pid loop d", d);
				double errVal= -(m_lastAngle-RobotMap.ahrs.getYaw());
                double P=0.002;

                rotation= -P * errVal;
				RobotMap.driveTrain.mecanumDrive_Cartesian(d, 0, rotation, 0);
				SmartDashboard.putString("drive status", "in pidloop for driving");
			}
		});
		RobotMap.drivebackright.setEncPosition(0);
		RobotMap.drivePID.setAbsoluteTolerance(10);
		RobotMap.drivePID.setSetpoint(dif);
		RobotMap.drivePID.setOutputRange(-.5, .5);
		RobotMap.drivePID.setInputRange(-10000, 10000);
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
        return RobotMap.drivePID.onTarget();
        
    }

    // Called once after isFinished returns true
    protected void end() {
    	RobotMap.driveTrain.mecanumDrive_Cartesian(0, 0, 0, 0);
		RobotMap.drivePID.disable();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	RobotMap.driveTrain.mecanumDrive_Cartesian(0, 0, 0, 0);
		RobotMap.drivePID.disable();
    }
}
