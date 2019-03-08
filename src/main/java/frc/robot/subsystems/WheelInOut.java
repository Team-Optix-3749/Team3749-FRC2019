package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;

/**
 * class WheelInOut represents the grabber wheel intake and shooting subsystem for picking up and dropping off cargo
 */
public class WheelInOut extends Subsystem
{
	// left intake wheel (victor spx, no encoder)
	private VictorSPX intakeMotor1;
	// right intake wheel (victor spx, no encoder)
	private VictorSPX intakeMotor2;
	// limit switich for detecting when the cargo is held
	private DigitalInput flywheelSwitch;

	// when it started loading up power
	private long startTime;

	/** 
	 * construction WheelInOut sets up motors and limit switch
	 */
	public WheelInOut()
	{
		flywheelSwitch = new DigitalInput(Robot.getMap().getDIO("intake_switch"));
		intakeMotor1 = new VictorSPX(Robot.getMap().getCAN("wheel_left"));
		intakeMotor2 = new VictorSPX(Robot.getMap().getCAN("wheel_right"));
		// invert one motor by default (invert values rather than giving positive or negative input)
		intakeMotor2.setInverted(true);
	}

	/**
	 * sets start time for holding/"powering up" shooter
	 * completely software build up to have more freedom
	 */
	public void updateStart()
	{
		startTime = System.currentTimeMillis();
	}
	
	/**
	 * get delta time form current time and starting "powerup" time
	 * access when the powering up of shooter started
	 * @return change in time (how long it's been powering up for)
	 */
	public long getHoldingTime()
	{
		// delta time in milliseconds
		return System.currentTimeMillis() - startTime;
	}

	// updates the speeds (based on inverted flywheels will determine direction)
	// expects positive input, invert rather than make negative
	private void setSpeed(double newSpeed) 
	{
		// if wrong input then
		if (newSpeed < 0)
			throw new IllegalArgumentException("Needs positive or 0 input. Negative input not allowed.");
		// set left and right inputs, one will be inverted
		intakeMotor1.set(ControlMode.PercentOutput, newSpeed);
		intakeMotor2.set(ControlMode.PercentOutput, newSpeed);
	}

	public void intake(double in)
	{
		if(intakeMotor1.getInverted() && !intakeMotor2.getInverted())
		{
			intakeMotor1.setInverted(false);
			intakeMotor2.setInverted(true);
		}
		setSpeed(in);
	}
	
	public void unload(double in)
	{
		if(!intakeMotor1.getInverted() && intakeMotor2.getInverted())
		{
			intakeMotor1.setInverted(true);
			intakeMotor2.setInverted(false);
		}
		setSpeed(in);
	}

	public void stop()
	{
		setSpeed(0);
	}

	public boolean hasCargo() 
	{
		// reversed polarity -> limit switch is usually true, but returns false if closed
		return flywheelSwitch.get();
	}	

	@Override
	protected void initDefaultCommand()
	{
		// no default command operation
		// could do set speed 0 but that is redundant and wastes resources
	}
}
