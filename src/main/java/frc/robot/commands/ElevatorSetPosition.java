package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;

public class ElevatorSetPosition extends Command {
   XboxController xbox;
   Elevator elevator;

   ElevatorSetPosition(XboxController xc, Elevator e) {
    xbox = xc;
    elevator = e;
   }

    @Override
    protected void initialize() {
    
    }

    @Override 
    protected void execute() {
        
        if(xbox.getAButton()) {
            elevator.setPosition(100); //enter a number greater than the max it will autocorrect
        }

        if(xbox.getBButton()) {
            elevator.setPosition(0); //enter a number below the minimum so it wil autocorrect
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    void calibrate() {
        //find maxPos and minPos
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    
    @Override
    protected void interrupted() {

    }
}