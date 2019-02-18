package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Intake extends Command
{
	private long endTime;
	public Intake()
	{
		requires(Robot.getFlywheel());
		endTime = -1;
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		/*
		if(!Robot.getFlywheel().hasCargo())
		{
			Robot.getFlywheel().intake();
			Robot.getFlywheel().printTest();
		}
		else
			Robot.getFlywheel().stop();
		*/
		Robot.getFlywheel().intake(0.5);

		if (Robot.getFlywheel().hasCargo())
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
