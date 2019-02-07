package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

class CalibrateElevator extends Command {
    TalonSRX motor;
    DigitalInput switch;
    private Elevatormechanism elevate;
    
    CalibrateElevator(Elevator e)
    {
        elevate = e;
    }

    @Override
    protected void initialize() {
        motor = Elevator.getElevator();
        switch = Elevator.getLimitSwitch();
    }

    @Override 
    protected void execute() 
    {
        
    }

    @Override
    protected boolean isFinished() {
        
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
