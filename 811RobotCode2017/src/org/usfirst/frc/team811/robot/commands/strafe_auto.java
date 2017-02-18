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
public class strafe_auto extends Command {

	double distance;
	double distanceInInches;
	double rotation;
	
	double m_lastAngle;
	
    public strafe_auto(int inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	distance = inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	m_lastAngle= RobotMap.ahrs.getYaw();
    	
    	
    	//RobotMap.driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
		
		//setTimeout(5);
		//RobotMap.driveEncoder.reset();
		RobotMap.drivebackright.setEncPosition(0);
		RobotMap.drivebackright.reverseSensor(false);
		
		//RobotMap.driveEncoder.setReverseDirection(true);
		//RobotMap.driveEncoder.setDistancePerPulse(1/40);
		
		//1 inch = 47.8 encoder ticks 
		
		//double encInches =  -1 * RobotMap.drivebackright.getEncPosition() / 47.8;
		
		
		
		
       
          //proportional rotation injected to counter error
		
		
		RobotMap.drivePID = new PIDController(.1, 0, .1, new PIDSource() {
			
			public double pidGet()
			{
				SmartDashboard.putNumber("Auto value",
						RobotMap.drivebackright.getEncPosition() / 100);
				return RobotMap.drivebackright.getEncPosition() / 100;
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
				double errVal= -(m_lastAngle-RobotMap.ahrs.getYaw());
                double P=0.02;

                rotation= P * errVal;
				RobotMap.driveTrain.mecanumDrive_Cartesian(-d, 0, 0, 0);
				SmartDashboard.putString("drive status", "in pidloop for driving");
			}
		});
		RobotMap.drivebackright.setEncPosition(0);
		RobotMap.drivePID.setAbsoluteTolerance(3);
		RobotMap.drivePID.setSetpoint(distance);
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
