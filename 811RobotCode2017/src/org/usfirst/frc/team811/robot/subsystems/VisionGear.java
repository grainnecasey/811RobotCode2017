package org.usfirst.frc.team811.robot.subsystems;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
//import javafx.scene.image.Image;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

//import org.opencv.core.*;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.videoio.VideoCapture;
import javax.imageio.ImageIO;




//import org.usfirst.frc.team811.robot.commands.imagetrack;
import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.Robot;
import org.usfirst.frc.team811.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class VisionGear extends Subsystem implements Config, PIDOutput{
    

	private double[] cenX;

	private double[] area;
	private double[] height;
	private double[] width;
	private double[] cenY;
	private double[] defaultValue = new double[1];
	
	CameraSource camSource = new CameraSource();

	RobotDrive driveTrain = RobotMap.driveTrain;
	int count = 0;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//strafe command from PID controller
	public void pidWrite(double output) {
		SmartDashboard.putNumber("strafe pid output", -output);
		//driveTrain.mecanumDrive_Cartesian(-output, 0.0, 0.0, ahrs.getYaw());
		driveTrain.arcadeDrive(-output, 0);
	}

	AHRS ahrs = RobotMap.ahrs;

	
	public PIDController visionGearController = RobotMap.visionGearController;

	double rotateToAngleRate;
	double kTolerancePx = 2;

	/* The following PID Controller coefficients will need to be tuned */
	/* to match the dynamics of your drive system. Note that the */
	/* SmartDashboard in Test mode has support for helping you tune */
	/* controllers by displaying a form where you can enter new P, I, */
	/* and D constants and test the mechanism. */

	@Override
	protected void initDefaultCommand() {

		
		visionGearController = new PIDController(kP, kI, kD, kF, camSource,
				(PIDOutput) this);
			//SmartDashboard.putData((NamedSendable) RobotMap.turnController);
			visionGearController.setInputRange(0, 260);
			visionGearController.setOutputRange(-1, 1);
			visionGearController.setAbsoluteTolerance(kTolerancePx);
			visionGearController.setContinuous(true);
			visionGearController.setSetpoint(0.0);
			
			LiveWindow.addActuator("DriveSystem", "visionGearController", visionGearController);
		
	}

	public void strafeTunePID() {
		double P = SmartDashboard.getNumber("kP");
		double I = SmartDashboard.getNumber("kI");
		double D = SmartDashboard.getNumber("kD");
		double F = SmartDashboard.getNumber("kF");
		
		visionGearController.setPID(P, I, D, F);
		
	} 


	public int indexOfContour() {
		height = RobotMap.gearTable.getNumberArray("height", defaultValue);	//changed to gear one
		width = RobotMap.gearTable.getNumberArray("width", defaultValue);	//changed to gear one

		// need to worry about what happens when there is no target
		// what should be returned - maybe -1
		boolean targetLost = height.length == 0 || width.length == 0;
		if (targetLost) {
			SmartDashboard.putString("target Status", "no contours");
			return -1;
		}

		final double heightLimit = 4; // picked something high to start
		final double widthLimit = 20; 
		
		// is the target too small?
		targetLost = true;  // assume it is to start
		for (int i = 0; i < height.length - 1; i++) {
			if (height[i] > heightLimit && width[i] > widthLimit) {
				targetLost = false;
				SmartDashboard.putString("target Status", "target too small");
				break;
			}
		}
		if (targetLost) {
			return -1;
		}
		
		
		int index = -1;

		// 132 inches away from boiler: h = 9; w - 23; a = 140ish
		double errorH = 9; // average of heights at every 1/2 meter within
							// range
		double errorW = 30; // ^^ same with widths

		
		// finds which contour is most like the U shape should be
		for (int i = 0; i < height.length - 1; i++) {
			
			// what happens if the you pick up something that is small, 
			// if the height is 5 if will still pass this test but 5 is pretty small
			double eH = 9 - height[i];
			double eW = 30 - width[i];
			if (Math.abs(eH) < errorH && Math.abs(eW) < errorW) {
				errorH = Math.abs(eH);
				errorW = Math.abs(eW);
				index = i;
			}
		}

		if (index != -1) {
			SmartDashboard.putString("target Status", "target found");
			SmartDashboard.putNumber("height", height[index]);
			SmartDashboard.putNumber("width", width[index]);
			SmartDashboard.putNumber("error", visionGearController.getError());
		}

		return index;
		
		

	}

	public void positionX() {
		defaultValue[0] = 0;
		cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
		area = RobotMap.gearTable.getNumberArray("area", defaultValue);
		boolean temp = true;
		int turn = 0; // 0 = center, 1 = right, 2 = left
		// while(temp)
		// {

		if (cenX[indexOfContour()] < 130 - framethres) {
			SmartDashboard.putString("Position X", "Left");
			driveTrain.arcadeDrive(0, 0.45);
			// turn = 1;
		} else if (cenX[indexOfContour()] > 130 + framethres) {
			SmartDashboard.putString("Position X", "Right");
			driveTrain.arcadeDrive(0, -0.45);
			// turn = 2;
		} else {
			SmartDashboard.putString("Position X", "Center");
			// if (turn == 1) {
			// driveTrain.arcadeDrive(0, -.5);
			// } else if (turn == 2) {
			// driveTrain.arcadeDrive(0, -.57);
			// }
			driveTrain.arcadeDrive(0, 0);
			temp = false;
			
		}

	}

	public boolean isCentered() {

		// defaultValue[0] = 0;
		cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
		// area = RobotMap.gearTable.getNumberArray("area", defaultValue);
		// boolean temp = true;
		// while(temp)
		// {
		int indexOfTarget = indexOfContour();
		// if the index is -1 - the target was lost
		if (indexOfTarget == -1) {
			SmartDashboard.putBoolean("is centered", false);
			return false;
		}

		
		double centerOfTarget = cenX[indexOfTarget];
		double centerOfCamera = framesizeX / 2;
		double error = centerOfCamera - centerOfTarget;

		boolean isCentered = Math.abs(error) <= framethres;
		//SmartDashboard.putNumber("get error", centerOfTarget);
		SmartDashboard.putBoolean("is centered", isCentered);

		return isCentered;
	}

	
public double currentCen() {
		
		int thresh = 5; 	//threshold of pixels 
		double dif;
		
		double rightTapePx = 198; 	//where right tape should be if centered
		double leftTapePx = 76; 	//where left tape should be if centered
		
		double cen = 130;
		
		
		//input will be the number of pixels it has to move to whatever side
		
		try {
			defaultValue[0] = 0;
			cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
			
			if (cenX.length < 2 && cenX.length != 0) {
				if (cenX[0] < 130) {
					dif = rightTapePx - cenX[0];
				} else {
					dif = leftTapePx - cenX[0];
				}
			} else {
				if (cenX[0] > cenX[1] && cenX.length != 0) {
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[1];
				} else {
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[0];
				}
				
				//cen is the px of the center between left and right tapes in picture
				
				//dif = 130 - cen;
			}
			
			

		} catch (RuntimeException ex) {
			// stop the PID loop and stop the robot
			//turnController.disable();
			//driveTrain.mecanumDrive_Cartesian(0.0, 0.0, 0.0, 0.0);
			throw ex;  // rethrow the exception - hopefully it gets displayed
		}
		
		return cen;

	}
	
	public void gearStrafeCenter() {
		
		int thresh = 5; 	//threshold of pixels 
		double dif;
		
		double rightTapePx = 198; 	//where right tape should be if centered
		double leftTapePx = 76; 	//where left tape should be if centered
		
		double cen;
		
		
		//input will be the number of pixels it has to move to whatever side
		
		try {

			int indexOfTarget = indexOfContour();
			// if the index is -1 - the target was lost
			//if (indexOfTarget == -1) {
				// what should the robot do?
				// for not stop and return
			//	turnController.setSetpoint(0);
			//	return;
			//}

			// double currentRotationRate;

			defaultValue[0] = 0;
			cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
			height = RobotMap.gearTable.getNumberArray("height", defaultValue);
			
			
			if (cenX.length < 2 && cenX.length != 0) {
				if (cenX[0] < 130) {
					dif = rightTapePx - cenX[0];
				} else {
					dif = leftTapePx - cenX[0];
				}
			} else {
				if (cenX[0] > cenX[1] && cenX.length != 0) {
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[1];
				} else {
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[0];
				}
				
				//cen is the px of the center between left and right tapes in picture
				
				dif = 130 - cen;
			}
			
			//SmartDashboard.putNumber("gear setpoint", dif);
			
			
			//then strafe so they are both equal distance from center
			
			

		} catch (RuntimeException ex) {
			// stop the PID loop and stop the robot
			visionGearController.disable();
			driveTrain.mecanumDrive_Cartesian(0.0, 0.0, 0.0, 0.0);
			throw ex;  // rethrow the exception - hopefully it gets displayed
		}
		
		visionGearController.setSetpoint(130);
		SmartDashboard.putNumber("gear setpoint", visionGearController.getSetpoint());
		visionGearController.enable();
		
		
	}
	
	public void setCamSource() {
		camSource.setSource(currentCen());
	}

}
