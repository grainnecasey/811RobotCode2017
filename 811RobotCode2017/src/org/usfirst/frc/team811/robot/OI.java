package org.usfirst.frc.team811.robot;

import org.usfirst.frc.team811.robot.commands.climb_down;
import org.usfirst.frc.team811.robot.commands.climb_up;
import org.usfirst.frc.team811.robot.commands.intake_on;
import org.usfirst.frc.team811.robot.commands.intake_off;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */


public class OI  implements Config{
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);
    
    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.
    
    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:
    
    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());
    
    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());
	
	JoystickButton intake_in;
	JoystickButton intake_off;
	JoystickButton climb;
	JoystickButton vision_shoot;
	JoystickButton gear_pick_up_up;
	JoystickButton gear_pick_up_down;
	
	public OI() {
		
		//Operator Controller (joystick 2)
		intake_in = new JoystickButton(RobotMap.joystick2, 1);
		intake_in.whenPressed(new intake_on());
		
		intake_off = new JoystickButton(RobotMap.joystick2, 2);
		intake_off.whenPressed(new intake_off());
		
		climb = new JoystickButton(RobotMap.joystick2, 3);
		climb.whileHeld(new climb_up());
		
		
	}
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

