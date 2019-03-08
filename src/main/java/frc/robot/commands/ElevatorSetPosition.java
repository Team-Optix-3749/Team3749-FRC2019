package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorSetPosition extends Command {
   private int position;

   public ElevatorSetPosition(int pos) {
        requires(Robot.getElevator());
        position = pos;
   }
    @Override 
    protected void execute() {
        Robot.getElevator().setPosition(position);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
