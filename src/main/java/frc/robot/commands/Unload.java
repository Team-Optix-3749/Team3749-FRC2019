package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Unload extends Command
{
	public Unload()
	{
		requires(Robot.getFlywheel());
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		if(Robot.getFlywheel().hasCargo())
			Robot.getFlywheel().unload();
	}

	protected boolean isFinished()
	{
		if(!Robot.getFlywheel().hasCargo())
			return true;
	}

	protected void end() 
	{
		Robot.getFlywheel().setSpeed(0);
	}	

	protected void interrupted() 
	{

	}
}
