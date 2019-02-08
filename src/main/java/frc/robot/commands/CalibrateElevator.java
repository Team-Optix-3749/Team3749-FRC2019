package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

class CalibrateElevator extends Command {
    private TalonSRX motor;
    private DigitalInput sw;
    private Elevator elevate;
    
    CalibrateElevator(Elevator e)
    {
        elevate = e;
    }

    @Override
    protected void initialize() {
        motor = elevate.getElevator();
        sw = elevate.getLimitSwitch();
    }

    @Override 
    protected void execute() 
    {
        // elevate.moveMotor();
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
