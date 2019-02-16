package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;


public class WheelInOut extends Subsystem
{
	private VictorSPX intakeMotor1;
	private VictorSPX intakeMotor2;
	private DigitalInput flywheelSwitch;
	private double speed;

	public WheelInOut()
	{
		flywheelSwitch = new DigitalInput(Robot.getMap().getDIO("intake_switch"));
		speed = 0;
		intakeMotor1 = new VictorSPX(Robot.getMap().getCAN("wheel_left"));
		intakeMotor2 = new VictorSPX(Robot.getMap().getCAN("wheel_right"));
		intakeMotor2.setInverted(true);
	}

	public void setSpeed(double newSpeed) 
	{
		speed = newSpeed;
		intakeMotor1.set(ControlMode.PercentOutput, newSpeed);
		intakeMotor2.set(ControlMode.PercentOutput, newSpeed);
	}

	public double getSpeed() {
		return speed;
	}
	public void intake()
	{
		if(intakeMotor1.getInverted() && !intakeMotor2.getInverted())
		{
			intakeMotor1.setInverted(false);
			intakeMotor2.setInverted(true);
		}
		setSpeed(1.0);
	}
	
	public void unload()
	{
		if(!intakeMotor1.getInverted() && intakeMotor2.getInverted())
		{
			intakeMotor1.setInverted(true);
			intakeMotor2.setInverted(false);
		}
		setSpeed(1.0);
	}

	public void stop()
	{
		setSpeed(0);
	}

	public boolean hasCargo() 
	{
		// reversed polarity -> limit switch is usually true, but returns false if closed
		return !flywheelSwitch.get();
	}	

	@Override
	protected void initDefaultCommand()
	{
		
	}
}
