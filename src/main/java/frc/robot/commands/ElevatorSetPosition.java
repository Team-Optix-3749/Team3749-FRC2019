package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class ElevatorSetPosition extends Command {
   private int position;

   ElevatorSetPosition(int pos) {
        requires(Robot.getElevator());
        position = pos;
   }

    @Override
    protected void initialize() {
        
    }

    @Override 
    protected void execute() {
        Robot.getElevator().setPosition(position);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {

    }
}