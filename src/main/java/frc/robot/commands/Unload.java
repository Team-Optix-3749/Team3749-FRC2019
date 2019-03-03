package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Unload extends Command
{
	private long startTime;
	private double speed;
	public Unload(double sp)
	{
		requires(Robot.getFlywheel());
		speed = sp;
	}	
	public Unload()
	{
		this(1.0);
	}	
	
	protected void initialize(){
		startTime = System.currentTimeMillis();
	}

	protected void execute()
	{
		Robot.getFlywheel().unload(speed);
	}

	protected boolean isFinished()
	{
		// will shoot for 1 full second
		return System.currentTimeMillis() - startTime > 1000;
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