package org.usfirst.frc.team811.robot.subsystems;

import org.usfirst.frc.team811.robot.Config;
import org.usfirst.frc.team811.robot.RobotMap;

import com.ctre.CANTalon;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class VisionTurret extends Subsystem implements Config, PIDOutput{
    
	private double[] cenX;

	private double[] area;
	private double[] height;
	private double[] width;
	private double[] cenY;
	private double[] defaultValue = new double[1];
	
	CameraSource camSource = new CameraSource();
	AHRS ahrs = RobotMap.ahrs;
	public PIDController visionTurretController = RobotMap.visionTurretController;
	CANTalon turret = RobotMap.turret;
	
	
	double rotateToAngleRate;
	double kTolerancePx = 2;
	int count = 0;

	// the command from the PID controller
	public void pidWrite(double output) {
		SmartDashboard.putNumber("turret pid loop output", output);
		SmartDashboard.putNumber("error", visionTurretController.getError());
		count++;
		SmartDashboard.putNumber("count", count);
		//turret.set(-output);
		RobotMap.driveTrain.mecanumDrive_Cartesian(0, 0, output, ahrs.getYaw());
	}


	/* The following PID Controller coefficients will need to be tuned */
	/* to match the dynamics of your drive system. Note that the */
	/* SmartDashboard in Test mode has support for helping you tune */
	/* controllers by displaying a form where you can enter new P, I, */
	/* and D constants and test the mechanism. */

	@Override
	protected void initDefaultCommand() {

		visionTurretController = new PIDController(tkP, tkI, tkD, tkF, ahrs,
			(PIDOutput) this);
		//SmartDashboard.putData((NamedSendable) RobotMap.visionTurretController);
		visionTurretController.setInputRange(-180.0f, 180.0f);
		visionTurretController.setOutputRange(-.7, .7);
		visionTurretController.setAbsoluteTolerance(kToleranceDegrees);
		visionTurretController.setContinuous(true);
		visionTurretController.setSetpoint(0.0);
		
		//LiveWindow.addActuator("DriveSystem", "RotateController", visionTurretController);
		SmartDashboard.putString("vision turret status", "init pid");
		
	}
	
	public void tunePID() {
		double P = SmartDashboard.getNumber("kP");
		double I = SmartDashboard.getNumber("kI");
		double D = SmartDashboard.getNumber("kD");
		double F = SmartDashboard.getNumber("kF");
		
		visionTurretController.setPID(P, I, D, F);
		SmartDashboard.putString("vision turret status", "tune pid");
		
	}

	public int indexOfContour() {
		height = RobotMap.turretTable.getNumberArray("height", defaultValue);	//changed to gear one
		width = RobotMap.turretTable.getNumberArray("width", defaultValue);	//changed to gear one

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
			//SmartDashboard.putNumber("error", visionTurretController.getError());
		}

		return index;

	}

	
	public void gyroTurn() {
		// ensure that if something bad happens, everything stops
		// can use a try catch to stop the robot
		double dif = 0;
		try {

			int indexOfTarget = indexOfContour();
			// if the index is -1 - the target was lost
			if (indexOfTarget == -1) {
				// what should the robot do?
				// for not stop and return
				visionTurretController.setSetpoint(0);
				SmartDashboard.putNumber("turret setpoint", 0);
				return;
			}

			// double currentRotationRate;

			defaultValue[0] = 0;
			cenX = RobotMap.turretTable.getNumberArray("centerX", defaultValue);

			double d = SmartDashboard.getDouble("distance");
			double i = (.274728886 * d + 42.40897141);	//42/45 * d; // inches displayed in
														// screen

			double r =Math.atan((1 / (2d))					//Math.atan(((i / 2) * d)	260/50
					* (1 - (cenX[indexOfTarget] / 130)));
			
			
			// angle needed to move in radians
			double x = -1 * Math.toDegrees(r); // angle needed to move in
			dif= ahrs.getYaw() +r;
												// degrees
			
			// with a constant field of view (fov)
			double degreesPerPixel = 60.0/((double)framesizeX);
			double pixelsFromCenter = 130 - cenX[indexOfTarget];
			double errorInDegrees = degreesPerPixel * pixelsFromCenter;
			dif = ahrs.getYaw() - errorInDegrees;

			SmartDashboard.putNumber("turret setpoint", dif);
			//double dif = x;

			

		} catch (RuntimeException ex) {
			// stop the PID loop and stop the robot
			visionTurretController.disable();
			turret.set(0.0);
			throw ex;  // rethrow the exception - hopefully it gets displayed
		}
		
		SmartDashboard.putString("vision turret status", "setting setpoint");
		visionTurretController.setSetpoint(dif);
		visionTurretController.enable();
	}

	
	public void positionX() {
		defaultValue[0] = 0;
		cenX = RobotMap.turretTable.getNumberArray("centerX", defaultValue);
		area = RobotMap.turretTable.getNumberArray("area", defaultValue);
		boolean temp = true;
		int turn = 0; // 0 = center, 1 = right, 2 = left
		// while(temp)
		// {

		if (cenX[indexOfContour()] < 130 - framethres) {
			SmartDashboard.putString("Position X", "Left");
			turret.set(0.45);
			// turn = 1;
		} else if (cenX[indexOfContour()] > 130 + framethres) {
			SmartDashboard.putString("Position X", "Right");
			turret.set(-0.45);
			// turn = 2;
		} else {
			SmartDashboard.putString("Position X", "Center");
			// if (turn == 1) {
			// driveTrain.arcadeDrive(0, -.5);
			// } else if (turn == 2) {
			// driveTrain.arcadeDrive(0, -.57);
			// }
			turret.set(0);
			temp = false;
			
		}

	}

	public boolean isCentered() {

		// defaultValue[0] = 0;
		cenX = RobotMap.turretTable.getNumberArray("centerX", defaultValue);
		// area = RobotMap.visionTable.getNumberArray("area", defaultValue);
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



}

