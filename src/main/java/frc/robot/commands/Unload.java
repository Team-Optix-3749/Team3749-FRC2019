package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Unload extends Command
{
	private long endTime;
	public Unload()
	{
		requires(Robot.getFlywheel());
		endTime = -1;
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		Robot.getFlywheel().unload();

		if (!Robot.getFlywheel().hasCargo())
			endTime = System.currentTimeMillis();
	}

	protected boolean isFinished()
	{
		if (endTime == -1)
		{
			return false;
		}
		else
		{
			return System.currentTimeMillis() - endTime > 500;
		}
	}

	protected void end() 
	{
		Robot.getFlywheel().setSpeed(0);
		endTime = -1;
	}	

	protected void interrupted() 
	{
		end();
	}
}
