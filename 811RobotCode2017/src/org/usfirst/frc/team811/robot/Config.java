package org.usfirst.frc.team811.robot;

public interface Config {

	// Joysticks
	int JOY_PORT_1 = 1;
    int JOY_PORT_2 = 2;
	
	// Drive Ports
    int FRONT_LEFT_PORT = 1;
    int REAR_LEFT_PORT = 2;
    int FRONT_RIGHT_PORT = 4;
    int REAR_RIGHT_PORT = 3;
    int GYRO_CHANNEL = 1;
    int DRIVE_ENCODER_PORT_1 = 1;
    int DRIVE_ENCODER_PORT_2 = 2;
	
    // Drive Controls
    int DRIVE_X_JOYSTICK_AXIS = 4;
    int DRIVE_Y_JOYSTICK_AXIS = 1;
    int DRIVE_STRAFING_RIGHT_JOYSTICK_AXIS = 3;
    int DRIVE_STRAFING_LEFT_JOYSTICK_AXIS = 2;
    int FIELD_CENTRIC_BUTTON = 8;
    int ROBOT_CENTRIC_BUTTON = 7;
    int SLOW_BUTTON = 6;
    int ENCODER_RESET_BUTTON = 3;
    int GYRO_RESET_BUTTON = 4;
    
    //i dunno where to put this
    int INTAKE_BALL_BUTTON = 5;
    int INTAKE_GEAR_BUTTON = 9;
    int HIGHSHOOTER_BUTTON = 6;
    int	CLIMBER_BUTTON = 1;
    int TURRET_BUTTON = 2;
    
    // Variables
    double DRIVE_DISTANCE_PER_PULSE = 1/9.5;
}
