package org.usfirst.frc.team811.robot;

import org.usfirst.frc.team811.robot.commands.*;

import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
		intake_in = new JoystickButton(RobotMap.joystick2, 1);
		intake_in.whileHeld(new intake_on());
		
		climb = new JoystickButton(RobotMap.joystick2, 3);
		climb.whileHeld(new climb_up());
		
		gear_grabber_up = new JoystickButton(RobotMap.joystick2, 2);
		gear_grabber_up.whileHeld(new gear_up());

		gear_grabber_down = new JoystickButton(RobotMap.joystick2, 0);
		gear_grabber_down.whileHeld(new gear_down());
		
		shooter = new JoystickButton(RobotMap.joystick2, 5);
		shooter.whenPressed(new shoot_shoot());
		
		
		//Smart Dashboard Buttons
		SmartDashboard.putData("agitator_off", new agitator_off());
		SmartDashboard.putData("agitator_on", new agitator_on());
		SmartDashboard.putData("climb_down", new climb_down());
		SmartDashboard.putData("climb_up", new climb_up());
		SmartDashboard.putData("gear_down", new gear_down());
		SmartDashboard.putData("gear_up", new gear_up());
		SmartDashboard.putData("intake_backspin", new intake_backspin());
		SmartDashboard.putData("intake_off", new intake_off());
		SmartDashboard.putData("intake_on", new intake_on());
		SmartDashboard.putData("shoot_shoot", new shoot_shoot());
		SmartDashboard.putData("shoot_stop", new shoot_stop());
		SmartDashboard.putData("vision_turn_auto", new vision_turn_auto());
		
		//auto
		SmartDashboard.putData("auto_base", new auto_base());
		SmartDashboard.putData("auto_gear", new auto_gear());
		SmartDashboard.putData("auto_gearhopper", new auto_gearhopper());
		SmartDashboard.putData("auto_gearready", new auto_gearready());
		SmartDashboard.putData("auto_gearshoot", new auto_gearshoot());
		SmartDashboard.putData("auto_hopper", new auto_hopper());
		SmartDashboard.putData("auto_shoot", new auto_shoot());
		
		SmartDashboard.putData("vision strafe auto", new vision_strafe_auto());

	}
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

