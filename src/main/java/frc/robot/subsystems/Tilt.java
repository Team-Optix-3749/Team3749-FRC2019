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
  private final double ENCODER_CONSTANT;
  private DigitalInput switchie;
  
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
    position+= (pos / 90) * ENCODER_CONSTANT;
    update();
  }
  
  public void setPosition(double pos)
  {
    position= (pos / 90) * ENCODER_COSTANT;
    update();
  }
  public int getPosition()
  {
    return motor.getSelectedSensorPosition(0);
  }
  private void update()
  {
    motor.setSelectedSensorPosition((int)position, 0, 20);
  }
  
  public static boolean isSwitchieClosed() {
    return switchie.get(); 
  }
  
  public static TalonSRX getmotor() {
    return motor; 
  }
}
