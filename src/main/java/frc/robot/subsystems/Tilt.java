package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.TiltStick;

/**
 * class Tilt
 */
public class Tilt extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private TalonSRX motor;

  // setpoint for motor in encoder units (closed loop control)
  private double position;
  // setpoint for motor percent output (open loop control)
  private double motorOut;

  // whether to used pid closed loop position control
  private boolean pidEnabled;

  // range of encoder raw values -> 0 to ENCODER_IN are the limits
  private final double ENCODER_IN = 176000;
  // preferred range of encoder values (for degrees, percent, etc) -> 0 to ENCODER_OUT
  private final double ENCODER_OUT = 100;

  public Tilt ()
  {
    motor = new TalonSRX(Robot.getMap().getCAN("tilt"));

    // PID constants (from/to encoder is reversed since it's multiplied by encoder error)
    motor.config_kP(0, 0.005);
    motor.config_kI(0, 0);
    motor.config_kD(0, 0);

    // positive input is negative sensor readings
    // need to flip sensor phase
    motor.setSensorPhase(true);
    motor.setInverted(true);

    motor.configClosedloopRamp(1);

    position = 0;
    motorOut = 0;
    pidEnabled = true;

    reset();
  }
  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new TiltStick());
  }
  public void setVelocity(double pos)
  {
    if (! pidEnabled)
      motorOut = pos * 0.06;
    else
      position += toEncoder(pos);
    update();
  }
  
  public void setPosition(double pos)
  {
    position = toEncoder(pos);
    update();
  }
  public double getPosition()
  {
    return fromEncoder(motor.getSelectedSensorPosition());
  }
  public double getSetpoint ()
  {
    return fromEncoder(position);
  }
  public void reset()
  {
    motor.setSelectedSensorPosition(0);
    setPosition(0);
  }
  private void update()
  {
    if (pidEnabled)
    {
      if (position > ENCODER_IN * 1.1)
        position = ENCODER_IN * 1.1;
      if (position < toEncoder(-10))
        position = toEncoder(-10);
      
      motor.set(ControlMode.Position, position);
      
      if (Robot.getMap().getSys("tilt") == 2)
      {
        System.out.println("Tilt error: " + motor.getClosedLoopError());
        System.out.println("Tilt motor output" + motor.getMotorOutputPercent());
      }
    }
    else
      rawMove(motorOut);
  }
  public void rawMove(double speed) {
    motor.set(ControlMode.PercentOutput, speed);
  }

  public boolean usingPid()
  {
    return pidEnabled;
  }
  public void setPidEnabled(boolean en)
  {
    pidEnabled = en;
  }

  private double fromEncoder (double in)
  {
    // become bigger
    return in * ENCODER_OUT / ENCODER_IN;
  }
  private double toEncoder (double in)
  {
    // become smaller
    return in * ENCODER_IN / ENCODER_OUT;
  }
}
