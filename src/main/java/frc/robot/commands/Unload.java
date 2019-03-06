package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Unload extends Command
{
	private long startTime;
	private double speed;
	public Unload()
	{
		requires(Robot.getFlywheel());
	}
	
	protected void initialize(){
		startTime = System.currentTimeMillis();
		speed = 1.0;
		// checks if the click was super short, if so slow down shot
		if (System.currentTimeMillis() - Robot.getFlywheel().getStartTime() < 500)
			speed = 0.5;
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