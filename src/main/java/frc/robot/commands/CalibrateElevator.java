package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

class CalibrateElevator extends Command {
    @Override
    protected void initialize() {
    }

    @Override 
    protected void execute() 
    {
        Robot.getElevator().rawMove(0.1);
    }

    @Override
    protected boolean isFinished() {
        return Robot.getElevator().atTop();
    }

    @Override
    protected void end() 
    {
        System.out.println(Robot.getElevator().getPosition());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    
    @Override
    protected void interrupted() {

    }
}
