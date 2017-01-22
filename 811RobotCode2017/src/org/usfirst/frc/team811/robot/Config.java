package org.usfirst.frc.team811.robot;

public interface Config {

	// Drive Ports
    int FRONT_LEFT_PORT = 1;
    int REAR_LEFT_PORT = 2;
    int FRONT_RIGHT_PORT = 4;
    int REAR_RIGHT_PORT = 3;
    
    // Other Ports
    int TURRET_PORT = 1;
    int INTAKE_BALL_PORT = 1;
    int INTAKE_GEAR_PORT = 1;
    int RIGHT_SHOOTER_PORT = 1;
    int LEFT_SHOOTER_PORT = 1;
    int LOADER_RELAY_PORT = 1;
    int GEARGRABBER_PORT = 1;
	
    // Drive Controls
    int DRIVE_X_JOYSTICK_AXIS = 4;
    int DRIVE_Y_JOYSTICK_AXIS = 1;
    int DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS = 3;
    int DRIVE_STRAFING_LEFT_JOYSTICK_AXIS = 2;
    
    // Drive Config
    double DRIVE_DISTANCE_PER_PULSE = 1/9.5;
    
    // Intake Controls
    int INTAKE_BALL_BUTTON = 5;
    int INTAKE_GEAR_BUTTON = 9;
	int INTAKE_IN_BUTTON = 1; //a
	int INTAKE_OUT_BUTTON = 2; // 
	
    // Shooter/Turret Controls
    int SHOOTER_BUTTON = 6;
    int	CLIMBER_BUTTON = 1;
    int TURRET_BUTTON = 2;
    
    // Shooter Config
	double SHOOTER_FULL_SPEED_RATE = 20;
	int SHOOTER_DISTANCE_PER_PULSE = 260;
	int SHOOTER_SPEED = 1;
	double SHOOTER_END_WAIT_TIME = 50;
    
}