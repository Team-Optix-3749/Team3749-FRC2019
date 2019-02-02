package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;


public class WheelInOut extends Subsystem
{
	private TalonSRX intakeMotor1;
	private TalonSRX intakeMotor2;
	private DigitalInput flywheelSwitch;
	private double speed;

	public WheelInOut()
	{
		flywheelSwitch = new DigitalInput(1);
		speed = 0;
		intakeMotor1 = new TalonSRX(1);
		intakeMotor2 = new TalonSRX(2);
		intakeMotor2.setInverted(true);
	}

	public void setSpeed(double newSpeed) 
	{
		speed = newSpeed;
		intakeMotor1.set(ControlMode.PercentOutput, newSpeed);
		intakeMotor2.set(ControlMode.PercentOutput, newSpeed);
	}

	public void intake()
	{
		if(intakeMotor1.getInverted())
		{
			intakeMotor1.setInverted(false);
			intakeMotor2.setInverted(true);
		}
		
		setSpeed(.3);
	}
	
	public void unload()
	{
		if(!intakeMotor1.getInverted())
		{
			intakeMotor1.setInverted(false);
			intakeMotor2.setInverted(true);
		}
		
		setSpeed(.3);
	}

	public void stop()
	{
		setSpeed(0);
	}

	public boolean hasCargo() {
		return flywheelSwitch.get();
	}	

	@Override
	 protected void initDefaultCommand() {

	}
}
