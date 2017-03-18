package org.usfirst.frc.team811.robot.subsystems;


import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.NamedSendable;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Timer;
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
	int count2 = 0;
	
	Timer timer;
	double m_lastAngle = 0;
	double rotation = 0;
	
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

	//strafe command from PID controller
	public void pidWrite(double output) {
		//timer.delay(1);
		SmartDashboard.putNumber("strafe pid output", output);
		SmartDashboard.putNumber("strafe error", visionGearController.getError());
		count++;
		SmartDashboard.putNumber("count", count);
		double errVal= -(m_lastAngle-RobotMap.ahrs.getYaw());
        double P=0.002;

        rotation= -P * errVal;
		driveTrain.mecanumDrive_Cartesian(output, 0, rotation, 0);
		//Robot.drive.strafe_auto_dist(output);
	}

	AHRS ahrs = RobotMap.ahrs;

	
	public PIDController visionGearController = RobotMap.visionGearController;

	double rotateToAngleRate;
	double kTolerancePx = 10;

	/* The following PID Controller coefficients will need to be tuned */
	/* to match the dynamics of your drive system. Note that the */
	/* SmartDashboard in Test mode has support for helping you tune */
	/* controllers by displaying a form where you can enter new P, I, */
	/* and D constants and test the mechanism. */

	@Override
	protected void initDefaultCommand() {

		
		visionGearController = new PIDController(gkP, gkI, gkD, camSource,
				(PIDOutput) this);
			//SmartDashboard.putData((NamedSendable) RobotMap.turnController);
			visionGearController.setInputRange(-130, 130);
			visionGearController.setOutputRange(-.3, .3);
			visionGearController.setAbsoluteTolerance(kTolerancePx);
			visionGearController.setContinuous(true);
			visionGearController.setSetpoint(0.0);
			
			LiveWindow.addActuator("DriveSystem", "visionGearController", visionGearController);
			
			m_lastAngle = RobotMap.ahrs.getYaw();
			
			
		
	}

	public void strafeTunePID() {
		//double P = SmartDashboard.getNumber("kP");
		//double I = SmartDashboard.getNumber("kI");
		//double D = SmartDashboard.getNumber("kD");
				
		//visionGearController.setPID(P, I, D);
		
	} 


	public int[] indexOfContour() {
		defaultValue[0] = 172;
		
		height = RobotMap.gearTable.getNumberArray("height", defaultValue);	//changed to gear one
		width = RobotMap.gearTable.getNumberArray("width", defaultValue);	//changed to gear one

		int[] gearContours = new int[2];
		gearContours[0] = -1;
		gearContours[1] = -1;
		
		//width = 40
		//height = 111

		int wthresh = 10;
		int hthresh = 5;
		int j = 0;
		for (int i = 0; i < height.length; i++) {
			if ((height[i] < (111 + hthresh)) && (height[i] > (111 - hthresh))) {
				if ((width[i] < (40 + wthresh)) && (width[i] < (40 - wthresh))) {
					if (j < 2) {
						gearContours[j] = i;
						j++;
					}
					
				}
			}
		}
		
		return gearContours;
		
		// need to worry about what happens when there is no target
		// what should be returned - maybe -1
		/*boolean targetLost = height.length == 0 || width.length == 0;
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
			//SmartDashboard.putNumber("error", visionGearController.getError());
		}

		return index;
		
		*/

	}

	
	public boolean isCentered() {

		defaultValue[0] = 172;
		cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
		// area = RobotMap.gearTable.getNumberArray("area", defaultValue);
		// boolean temp = true;
		// while(temp)
		// {
		int indexOfTarget = 1;//indexOfContour();
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
		
		double rightTapePx = 138; 	//where right tape should be if centered
		double leftTapePx = 87; 	//where left tape should be if centered
		
		double cen = 180;
		defaultValue[0] = 172;
		cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
		width = RobotMap.gearTable.getNumberArray("width", defaultValue);
		
		int[] gearContours = indexOfContour();
		
		//input will be the number of pixels it has to move to whatever side
		
		try {
			defaultValue[0] = 0;
			
			
			if (gearContours.length != 0 && gearContours[1] == -1 && gearContours[0] != -1) {
				if (cenX[gearContours[0]] < 65) {
					SmartDashboard.putString("current cen status", "cen[0] < 65");
					cen = cenX[gearContours[0]] - (.5 * width[gearContours[0]]) - width[gearContours[0]];
					SmartDashboard.putNumber("centerFromCode", cen);
				} else if (cenX[gearContours[0]] < 180){
					SmartDashboard.putString("current cen status", "cen[0] < 130");
					cen = cenX[gearContours[0]] + (.5 * width[0]) + width[0];
					SmartDashboard.putNumber("centerFromCode", cen);
				} else if (cenX[gearContours[0]] < 200) {
					SmartDashboard.putString("current cen status", "cen[0] < 195");
					cen = cenX[gearContours[0]] - (.5 * width[gearContours[0]]) - width[gearContours[0]];
					SmartDashboard.putNumber("centerFromCode", cen);
				} else {
					SmartDashboard.putString("current cen status", "cen[0] < 260");
					cen = cenX[gearContours[0]] + (.5 * width[gearContours[0]]) + width[gearContours[0]];
					SmartDashboard.putNumber("centerFromCode", cen);
				}
			} else {
				if (gearContours.length != 0 && gearContours[1] != -1 && gearContours[0] != -1) {
					SmartDashboard.putString("current cen status", "cen[0] > cen[1]");
					cen = (cenX[gearContours[0]] + cenX[gearContours[1]])/2;
					SmartDashboard.putNumber("centerFromCode", cen);
				/*} else  if (cenX.length != 0 && cenX[0] > cenX[1]) {
					SmartDashboard.putString("current cen status", "cen[0] < cen[1]");
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[0];
					SmartDashboard.putNumber("centerFromCode", cen);*/
				}
				
				//cen is the px of the center between left and right tapes in picture
				
				//dif = 130 - cen;
			}
			
			if (cen > 260) {
				cen = 260;
			} else if (cen < 0) {
				cen = 0;
			}
			
			

		} catch (RuntimeException ex) {
			// stop the PID loop and stop the robot
			//turnController.disable();
			//driveTrain.mecanumDrive_Cartesian(0.0, 0.0, 0.0, 0.0);
			throw ex;  // rethrow the exception - hopefully it gets displayed
		}
		
		SmartDashboard.putNumber("current center", cen);
		count2++;
		SmartDashboard.putNumber("count 2", count2);
		return cen - 180;
		

	}
	
	public void gearStrafeCenter() {
		
		int thresh = 5; 	//threshold of pixels 
		double dif;
		
		double rightTapePx = 198; 	//where right tape should be if centered
		double leftTapePx = 76; 	//where left tape should be if centered
		
		double cen;
		
		
		//input will be the number of pixels it has to move to whatever side
		
		try {

			int indexOfTarget = 1;//indexOfContour();
			// if the index is -1 - the target was lost
			//if (indexOfTarget == -1) {
				// what should the robot do?
				// for not stop and return
			//	turnController.setSetpoint(0);
			//	return;
			//}

			// double currentRotationRate;

			defaultValue[0] = 172;
			cenX = RobotMap.gearTable.getNumberArray("centerX", defaultValue);
			height = RobotMap.gearTable.getNumberArray("height", defaultValue);
			
			
			if (cenX.length != 0 && cenX.length < 2) {
				if (cenX[0] < 130) {
					dif = rightTapePx - cenX[0];
				} else {
					dif = leftTapePx - cenX[0];
				}
			} else {
				if (cenX.length != 0 && cenX[0] > cenX[1]) {
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[1];
				} else  if (cenX.length != 0) {
					cen = Math.abs(cenX[0] - cenX[1]) / 2 + cenX[0];
				} else {
					cen = 1;
				}
				
				//cen is the px of the center between left and right tapes in picture
				
				dif = cen;
			}
			
			SmartDashboard.putNumber("gear setpoint", dif);
			
			
			//then strafe so they are both equal distance from center
			
			

		} catch (RuntimeException ex) {
			// stop the PID loop and stop the robot
			//visionGearController.disable();
			driveTrain.mecanumDrive_Cartesian(0.0, 0.0, 0.0, 0.0);
			throw ex;  // rethrow the exception - hopefully it gets displayed
		}
		
		visionGearController.setSetpoint(0);
		SmartDashboard.putNumber("gear setpoint", visionGearController.getSetpoint());
		visionGearController.enable();
		
		
	}
	
	public void setCamSource() {
		camSource.setSource(currentCen());
	}

}
