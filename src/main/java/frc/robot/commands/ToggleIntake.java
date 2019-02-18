package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToggleIntake extends Command
{
	private long endTime;
	public ToggleIntake()
	{
		requires(Robot.getFlywheel());
	}	
	
	protected void initialize()
	{
		endTime = -1;
		
		// already intake
		if (Robot.getFlywheel().getSpeed() > 0)
		{
			endTime = -2;
		}
	}

	protected void execute()
	{
		if (endTime == -2)
		{
			Robot.getFlywheel().setSpeed(0);
		}
		else
		{
			// slow down once it hits the limit switch
			if (endTime == -1)
				Robot.getFlywheel().intake(0.5);
			else
				Robot.getFlywheel().intake(0.5 - 0.3 * (System.currentTimeMillis() - endTime) / 500);
			
			if (Robot.getFlywheel().hasCargo())
				endTime = System.currentTimeMillis();
		}
		
	}

	protected boolean isFinished()
	{
		if (endTime == -2) // toggle to stop will exit immediately
			return true;
		else
			if (endTime == -1)
				return false;
			else
				return System.currentTimeMillis() - endTime > 500;
	}

	protected void end() 
	{
		Robot.getFlywheel().setSpeed(0.1);
		endTime = -1;
	}

	protected void interrupted() 
	{
		end();
	}
}
