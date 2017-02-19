package org.usfirst.frc.team811.robot;

public interface Config {

	// Drive Ports
    
    
    // CANTalons
    int TURRET_PORT = 9;
    int INTAKE_BALL_PORT = 2;
    int FRONT_LEFT_PORT = 6;
    int REAR_LEFT_PORT = 8;
    int FRONT_RIGHT_PORT = 7;
    int REAR_RIGHT_PORT = 4;
    int RIGHT_SHOOTER_PORT = 1;
    int LEFT_SHOOTER_PORT = 3; //enc
    
    int CLIMBER_PORT = 5;
    
    //Other Ports
    int GEAR_GRABBER_PORT = 9;
    int LOADER_RELAY_PORT = 0;
    int GEAR_TOP_LIMIT_PORT = 2;
    int GEAR_BOTTOM_LIMIT_PORT = 1;
    int AGITATOR_PORT = 5;
	
    // Drive Controls
    int DRIVE_X_JOYSTICK_AXIS = 4;
    int DRIVE_Y_JOYSTICK_AXIS = 1;
    int DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS = 3;
    int DRIVE_STRAFING_LEFT_JOYSTICK_AXIS = 2;
    
    // Drive Config
    double DRIVE_DISTANCE_PER_PULSE = 82.6;
    
    // Intake Ball Controls
	double INTAKE_SPEED = .7; 
	int INTAKE_IN_BUTTON = 5;
	int INTAKE_OFF_BUTTON = 6;
	
	// Gear Grabber Control
	int GEAR_GRABBER_UP_BUTTON = 1;
	int GEAR_GRABBER_DOWN_BUTTON = 3;
	double GEAR_GRAB_SPEED = .7;
	
    // Shooter/Turret Controls
    int SHOOTER_BUTTON = 6;
    int	CLIMBER_BUTTON = 4;
    int TURRET_BUTTON = 2;
    
    // Shooter Config
	double SHOOTER_FULL_SPEED_RATE = 20;
	int SHOOTER_DISTANCE_PER_PULSE = 260;
	int SHOOTER_SPEED = 1;
	double SHOOTER_END_WAIT_TIME = 50;
	
	// Drive Encoder Config
	int DRIVE_ENCODER_PORT_1 = 2;
	int DRIVE_ENCODER_PORT_2 = 3;
	
	//Other Config
	double AGITATOR_SPEED = .9;
	double CLIMBER_SPEED = 1;
	
	//Turret Vision Config
	double tkP = 0.01;
    double tkI = 0.00;
    double tkD = 0.4;
    double tkF = 0.00;
    double kToleranceDegrees = 1.0f;
    int framesizeX = 260;
	int framesizeY = 195;
	int framethres = 5;
	
	//Gear Vision Config
	double gkP = 0.04;
    double gkI = 0.00;
    double gkD = 0.00;
    double gkF = 0.00;
    
    //Lidar Config
    double SHOOTER_DIST_INCHES = 20; //TODO
    
}