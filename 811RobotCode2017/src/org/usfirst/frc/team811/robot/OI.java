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
	JoystickButton gear_vision;
	JoystickButton shoot_manual;
	JoystickButton shoot_off;
	JoystickButton intake_off;
	JoystickButton vision;
	JoystickButton agForward;
	JoystickButton agReverse;
	
	public OI() {
		
		
		//Operator Controller (joystick 2)
		intake_in = new JoystickButton(RobotMap.joystick2, 1);
		intake_in.whenPressed(new intake_on());
		
		intake_off = new JoystickButton(RobotMap.joystick2, 2);
		intake_off.whenPressed(new intake_off());
		
		gear_vision = new JoystickButton(RobotMap.joystick1, 1);
		gear_vision.whenPressed(new vision_strafe_auto());
		
		shoot_manual = new JoystickButton(RobotMap.joystick2, 8);
		shoot_manual.whenPressed(new shoot_comp_man());
		
		vision_shoot = new JoystickButton(RobotMap.joystick2, 3);
		vision_shoot.whenPressed(new shoot_compilation());
		
		shoot_off = new JoystickButton(RobotMap.joystick2, 4);
		shoot_off.whenPressed(new shoot_comp_stop());
		
		agForward = new JoystickButton(RobotMap.joystick2, 5);
		agForward.whileHeld(new agForward());
		
		agReverse = new JoystickButton(RobotMap.joystick2, 6);
		agReverse.whileHeld(new afReverse());
		
		
		//Smart Dashboard Buttons
		SmartDashboard.putData("agitator_off", new agitator_off());
		SmartDashboard.putData("agitator_on", new agitator_on());
		SmartDashboard.putData("climb_up", new climb_joy_control());
		SmartDashboard.putData("gear_down", new gear_down());
		SmartDashboard.putData("gear_up", new gear_up());
		SmartDashboard.putData("intake_backspin", new intake_backspin());
		SmartDashboard.putData("intake_off", new intake_off());
		SmartDashboard.putData("intake_on", new intake_on());
		SmartDashboard.putData("shoot_shoot", new shoot_shoot());
		SmartDashboard.putData("shoot_stop", new shoot_stop());
		SmartDashboard.putData("vision_turn_auto", new vision_turn_auto());
		SmartDashboard.putData("shoot_load", new shoot_load());
		SmartDashboard.putData("lidar_start", new lidar_start());
		SmartDashboard.putData("lidar_stop", new lidar_stop());
		
		//auto
		SmartDashboard.putData("auto_base", new auto_base());
		SmartDashboard.putData("auto_gear", new auto_gear());
		SmartDashboard.putData("auto_gearhopper", new auto_gearhopper());
		SmartDashboard.putData("auto_gearready", new auto_gearready());
		SmartDashboard.putData("auto_gearshoot", new auto_gearshoot());
		SmartDashboard.putData("auto_hopper", new auto_hopper());
		SmartDashboard.putData("auto_shoot", new auto_shoot());
		
		SmartDashboard.putData("vision strafe auto", new vision_strafe_auto());
		
		SmartDashboard.putData("drive auto", new drive_auto(48));
		SmartDashboard.putData("turn_auto(90)", new turn_auto(90));
		SmartDashboard.putData("strafe_auto(24", new strafe_auto(12));
		
		SmartDashboard.putData("vision strafe auto 2", new vision_strafe_auto2());

	}
    
    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
}

