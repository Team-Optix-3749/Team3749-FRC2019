package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Elevator;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

class CalibrateElevator extends Command {
    private TalonSRX motor;
    private DigitalInput sw;
    private Elevator elevate;
    private double enc_constant;
    
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
        elevate.moveMotor(90);
    }

    @Override
    protected boolean isFinished() {
        return sw.get();
    }

    @Override
    protected void end() 
    {
        enc_constant = e.getMotorEncoderValue()/100;
        System.out.println(enc_constant);
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
