package frc.robot.commands;

import frc.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

public class Intake extends Command

{
	public Intake()
	{
		requires(Robot.getFlywheel());
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		if(!Robot.getFlywheel().hasCargo())
			Robot.getFlywheel().intake();
	}

	protected boolean isFinished()
	{
		if(Robot.getFlywheel().hasCargo())
			return true;
	}

	protected void end() 
	{
		Robot.getFlywheel().setSpeed(0);
	}	

	protected void interrupted() 
	{
        	end();
	}
}
