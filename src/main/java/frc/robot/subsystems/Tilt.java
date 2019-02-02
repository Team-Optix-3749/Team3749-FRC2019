package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.EmptyPIDOut;
//import frc.robot.commands.TiltStick;
import frc.robot.commands.TiltStick;

/**
 * class Tilt
 */
public class Tilt extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private TalonSRX motor;
  private double position;
  
  public Tilt ()
  {
    motor = new TalonSRX(40);
    motor.config_kP(0, 0.001);
    motor.config_kI(0, 0);
    motor.config_kD(0, 0);
  }
  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new TiltStick());
  }
  public void move(double pos)
  {
    position+=pos;
    update();
  }
  public void setPosition(double pos)
  {
    position=pos;
    update();
  }
  public int getPosition()
  {
    return motor.getSelectedSensorPosition(0);
  }
  private void update()
  {
    motor.setSelectedSensorPosition(position, 0, 50);
  }
  //public void update()
  //{

  //}
  /*
  long position;
  motor.set(ControlMode.PercentOutput, -0.1);
  motor.set(ControlMode.Position, position);
  motor.setSelectedSensorPosition(0, 0, 50);
  motor.getSelectedSensorPosition(0)
  
  moveUp()
  moveDown()
  setPosition()
  update()
  getPosition()
  // 
  */
}
