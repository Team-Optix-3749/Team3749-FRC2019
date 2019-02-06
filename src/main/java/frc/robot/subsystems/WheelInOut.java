package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Spark;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;


public class WheelInOut extends Subsystem
{
	private Spark intakeMotor1;
	private Spark intakeMotor2;
	private DigitalInput flywheelSwitch;
	private double speed;

	public WheelInOut()
	{
		flywheelSwitch = new DigitalInput(1);
		speed = 0;
		intakeMotor1 = new Spark(4);// TalonSRX(Robot.getMap().getCAN("wheel_left"));
		intakeMotor2 = new Spark(5);// TalonSRX(Robot.getMap().getCAN("wheel_right"));
		intakeMotor2.setInverted(true);
	}

	public void setSpeed(double newSpeed) 
	{
		speed = newSpeed;
		intakeMotor1.set(newSpeed);
		intakeMotor2.set(-newSpeed);
	}

	public void intake()
	{
		setSpeed(1.0);
	}
	
	public void unload()
	{
		//if(!intakeMotor1.getInverted() && intakeMotor2.getInverted())
		//{
			//intakeMotor1.setInverted(true);
			//intakeMotor2.setInverted(false);
		//}
		setSpeed(-1.0);
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
