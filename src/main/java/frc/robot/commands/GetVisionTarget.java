package frc.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class GetVisionTarget extends Command
{
  private double rot;
	public GetVisionTarget()
	{
		requires(Robot.getDrive());
	}	
	
	protected void initialize(){}

	protected void execute()
	{
		if((Robot.getDrive().locateTarget()) != -1)
		{
			if((Robot.getDrive().locateTarget()) != 60.0) //ARBITRARY X VALUE
			{
				if((Robot.getDrive().locateTarget()) > 60.0)
				{
					rot = .3;
					while((Robot.getDrive().locateTarget()) != 60)
					{
						rot -= 1;
						Robot.getDrive().arcadeDrive(0, rot);
					}
				}
			}
		}
	}

	protected boolean isFinished()
	{
		return (!Robot.getFlywheel().hasCargo());
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
