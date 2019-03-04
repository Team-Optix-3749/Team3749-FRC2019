package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TiltSetPosition extends Command {
   private int position;

   public TiltSetPosition(int pos) {
      requires(Robot.getTilt());
      position = pos;
   }

    @Override
    protected void initialize() {
      Robot.getElevator().setBottom(20);
    }

    @Override 
    protected void execute() {
      if (Robot.getElevator().getPosition() > 15)
        Robot.getTilt().setPosition(position);
    }

    @Override
    protected boolean isFinished() {
      return Math.abs(Robot.getTilt().getPosition() - position) < 10;
    }

    @Override
    protected void end() {
      Robot.getElevator().setBottom(0);
    }
    
    @Override
    protected void interrupted() {

    }
}
