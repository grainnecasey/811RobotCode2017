package org.usfirst.frc.team811.robot;

import org.usfirst.frc.team811.robot.commands.*;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


public class OI  implements Config{
    
	// Button init
	JoystickButton intake_in;
	JoystickButton intake_out;
	JoystickButton climb;
	JoystickButton vision_shoot;
	JoystickButton gear_grabber_up;
	JoystickButton gear_grabber_down;
	JoystickButton shooter;
	
	public OI() {
		
		//Operator Controller (joystick 2)
		intake_in = new JoystickButton(RobotMap.joystick2, INTAKE_IN_BUTTON);
		intake_in.whileHeld(new intake_on());
		
		climb = new JoystickButton(RobotMap.joystick2, 3);
		climb.whileHeld(new climb_up());
		
		gear_grabber_up = new JoystickButton(RobotMap.joystick2, GEAR_GRABBER_UP_BUTTON);
		gear_grabber_up.whileHeld(new gear_up());

		gear_grabber_down = new JoystickButton(RobotMap.joystick2, GEAR_GRABBER_DOWN_BUTTON);
		gear_grabber_down.whileHeld(new gear_down());
		
		shooter = new JoystickButton(RobotMap.joystick2, SHOOTER_BUTTON);
		shooter.whenPressed(new shoot_shoot());
	}
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

