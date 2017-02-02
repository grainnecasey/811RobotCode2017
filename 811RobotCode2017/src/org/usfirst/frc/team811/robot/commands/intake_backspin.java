package org.usfirst.frc.team811.robot.commands;
import org.usfirst.frc.team811.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class intake_backspin extends Command {
	
	public intake_backspin() {
        // Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
    	requires(Robot.intake);
    	setTimeout(3);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.intake.intakeBack();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return ((isTimedOut()));
    }

    // Called once after isFin 99ished returns true
    protected void end() {
    	Robot.intake.intakeStop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

