package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.ElevatorStick;

/**
 * class Elevator
 * @author Ishita V
 */
public class Elevator extends Subsystem
{
  // range of encoder raw values -> 0 to ENCODER_IN are the limits
  private final double ENCODER_IN = 1100000;
  // preferred range of encoder values (for degrees, percent, etc) -> 0 to ENCODER_OUT
  private final double ENCODER_OUT = 100;

  // arbitrary feed forward constant (to counter gravity)
  private final double kAF = 0.08;

  // leading motor controllers, have built-in closed loop control
  private TalonSRX motor;

  // setpoint for motor in encoder units (closed loop control)
  private double position;
  // setpoint for motor in percent motor output (open loop control)
  private double motorOut;

  // how low the elevator can go (changing based on position of tilt mechanism)
  private double BOTTOM_LIMIT;

  private boolean pidEnabled;
  
  public Elevator ()
  {
    motor = new TalonSRX(Robot.getMap().getCAN("elevator"));

    // PID constants (from/to encoder is reversed since it's multiplied by encoder error)
    motor.config_kP(0, 0.003);
    motor.config_kI(0, 0);
    motor.config_kD(0, 0.00003);
    motor.config_kF(0, 0);

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
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorStick());
  }

  public void setVelocity(double pos)
  {
    if (pidEnabled)
    {
      position += toEncoder(pos);
    }
    else
    {
      motorOut = kAF + pos * 0.4;
    }
    update();
  }
  
  public void setPosition(double pos)
  {
    position = toEncoder(pos);
    update();
  }
  public void setBottom (double pos)
  {
    BOTTOM_LIMIT = pos;
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
      if (position > ENCODER_IN)
        position = ENCODER_IN;
      if (position < toEncoder(0))
        position = toEncoder(0);
      
      // constrain on bottom (position can potentially go all the way to 0 still)
      motor.set(ControlMode.Position, position < BOTTOM_LIMIT ? BOTTOM_LIMIT : position, DemandType.ArbitraryFeedForward, kAF);

      if (Robot.getMap().getSys("elevator") == 2)
      {
        System.out.println("Elevator error: " + motor.getClosedLoopError());
        System.out.println("Elevator motor out: " + motor.getMotorOutputPercent());
      }
    }
    else
    {
      rawMove(motorOut);
    }
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
