package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.RobotMap;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class LidarController extends Subsystem implements Config, PIDOutput{

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	RobotDrive driveTrain = RobotMap.driveTrain;
	public PIDController lidarPID = RobotMap.lidarPID;
	public Lidar lidar = new Lidar();
	
	double lidarTolerance = 3; //TODO
	double m_lastAngle = 0;
	double rotation = 0;
	
	int count = 0;
	
	
	//have distance in inches if possible

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	lidar.start();
    	lidarPID = new PIDController(gkP, gkI, gkD, lidar,
				(PIDOutput) this);
			//SmartDashboard.putData((NamedSendable) RobotMap.turnController);
			lidarPID.setInputRange(-200, 200);
			lidarPID.setOutputRange(-.3, .3);
			lidarPID.setAbsoluteTolerance(lidarTolerance);
			lidarPID.setContinuous(true);
			lidarPID.setSetpoint(0.0);
			
			LiveWindow.addActuator("DriveSystem", "visionGearController", lidarPID);

    }
    
    public void initVari() {
    	m_lastAngle = RobotMap.ahrs.getYaw();
    	count = 0;
    	rotation = 0;
    }

	@Override
	public void pidWrite(double output) {
		//timer.delay(1);
				SmartDashboard.putNumber("strafe pid output", output);
				SmartDashboard.putNumber("strafe error", lidarPID.getError());
				count++;
				SmartDashboard.putNumber("count", count);
				double errVal= -(m_lastAngle-RobotMap.ahrs.getYaw());
		        double P=0.002;

		        rotation= -P * errVal;
				driveTrain.mecanumDrive_Cartesian(output, 0, rotation, 0);
				//Robot.drive.strafe_auto_dist(output);
		
	}
	
	public void setSetpoint(double set) {
		lidarPID.setSetpoint(set);
	}
	
	public void setSetNorm() {
		lidarPID.setSetpoint(36); //TODO
	}
	
	public void lidarTunePID() {
		double P = SmartDashboard.getNumber("kP");
		double I = SmartDashboard.getNumber("kI");
		double D = SmartDashboard.getNumber("kD");
				
		lidarPID.setPID(P, I, D);
		
	} 
	
}

