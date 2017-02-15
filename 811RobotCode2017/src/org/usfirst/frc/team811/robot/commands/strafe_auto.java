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
public class strafe_auto extends Command {

	double distance;
	double distanceInInches;
	
    public strafe_auto(int inches) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drive);
    	distance = inches;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	//RobotMap.driveEncoder.setDistancePerPulse(DRIVE_DISTANCE_PER_PULSE);
		
    			//setTimeout(5);
    			//RobotMap.driveEncoder.reset();
    			RobotMap.drivebackright.setEncPosition(0);
    			RobotMap.drivebackright.reverseSensor(false);
    			
    			//RobotMap.driveEncoder.setReverseDirection(true);
    			//RobotMap.driveEncoder.setDistancePerPulse(1/40);
    			
    			//1 inch = 47.8 encoder ticks 
    			
    			//double encInches =  -1 * RobotMap.drivebackright.getEncPosition() / 47.8;
    			
    			RobotMap.drivePID = new PIDController(1, .6, 3, new PIDSource() {
    				
    				public double pidGet()
    				{
    					SmartDashboard.putNumber("Auto value",
    							-1 * RobotMap.drivebackright.getEncPosition() / 47.8);
    					return -1 * RobotMap.drivebackright.getEncPosition() / 47.8;
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
    			RobotMap.drivePID.setOutputRange(-.5, .5);
    			RobotMap.drivePID.setContinuous(true);
    			RobotMap.drivePID.enable();

    			SmartDashboard.putString("drive status", "drive forward auto"); 
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
