package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.WheelInOut;

public class Intake extends Command
{
	// seconds to go from max to min speed and vice versa
	private final double adjustSec = 0.5;

	private final double minSpeed = 0.05;
	private final double maxSpeed = 0.5;

	private double percent;
	private WheelInOut fly;

	public Intake()
	{
		fly = Robot.getFlywheel();
		requires(fly);
	}	
	
	protected void initialize()
	{
		percent = fly.hasCargo() ? 0 : 100;
	}

	protected void execute()
	{
		if (fly.hasCargo())
		{
			if (percent < 100)
				// will take exactly adjustSec seconds to switch
				percent += 100 / adjustSec * 0.02;
			else
				percent = 100;
		}
		else
		{
			if (percent > 0)
				// will take exactly adjustSec seconds to switch
				percent -= 100 / adjustSec * 0.02;
			else
				percent = 0;
		}
		Robot.getFlywheel().intake(percent / 100 * (maxSpeed - minSpeed) + minSpeed);
	}

	protected boolean isFinished()
	{
		return false;
	}

	protected void end() 
	{
		Robot.getFlywheel().intake(0);
	}	

	protected void interrupted()
	{
		end();
	}
}