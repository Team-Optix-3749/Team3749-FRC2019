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
		// usually do intake
		Robot.getFlywheel().intake(0.5);

		// first time it gets cargo
		if (Robot.getFlywheel().hasCargo() && endTime != -1)
			endTime = System.currentTimeMillis();
		
		// go for 0.5 more seconds, then slow intake
		if (System.currentTimeMillis() - endTime > 500)
			Robot.getFlywheel().intake(0.1);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void end() 
	{
		Robot.getFlywheel().intake(0);
		endTime = -1;
	}	

	protected void interrupted()
	{
		end();
	}
}