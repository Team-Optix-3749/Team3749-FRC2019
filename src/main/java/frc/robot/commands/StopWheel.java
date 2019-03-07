package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class StopWheel extends Command
{
	public StopWheel()
	{
		requires(Robot.getFlywheel());
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		Robot.getFlywheel().intake(0);
	}

	protected boolean isFinished()
	{
		return true;
	}

	protected void end() 
	{
		Robot.getFlywheel().stop();
	}	

	protected void interrupted() 
	{
		end();
	}
}
