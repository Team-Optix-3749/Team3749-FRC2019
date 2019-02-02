package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

class ElevatorStick extends Command {
    TalonSRX elevatorMotorInnter; 
    DigitalInput limitSwitchInner;


    ElevatorStick(TalonSRX EM, DigitalInput LS ) {
        elevatorMotorInnter = EM;
        limitSwitchInner = LS;
        requires(Robot.getDrive());
    }

    @Override
    protected void initialize() {
        
    }

    @Override 
    protected void execute() {

    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {

    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    
    @Override
    protected void interrupted() {

    }
}