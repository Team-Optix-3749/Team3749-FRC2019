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
      // move the soft limit up so that the robot motors don't hit chasses
      // build error, can't turn tilt at bottom elevator position
      Robot.getElevator().setBottom(20);
    }

    @Override 
    protected void execute() {
      // wait until high enough to change position
      if (Robot.getElevator().getPosition() > 15)
        Robot.getTilt().setPosition(position);
    }

    @Override
    protected boolean isFinished() {
      // once it's close enough, it's done
      return Math.abs(Robot.getTilt().getPosition() - position) < 10;
    }

    @Override
    protected void end() {
      // move back down
      Robot.getElevator().setBottom(0);
    }
    
    @Override
    protected void interrupted() {
      end();
    }
}
