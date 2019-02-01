package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.EmptyPIDOut;
import frc.robot.commands.TiltStick;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * class Tilt
 */
public class Tilt extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private TalonSRX motor;
  private int position;
  
  public Tilt ()
  {
    motor = new TalonSRX(40);
    motor.config_kp(0.001);
  }
  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new TiltStick());
  }
  public void moveUp(int pos)
  {
    position+=pos;
    update();
  }
  public void moveDown(int pos)
  {
    position-=pos;
    update();
  }
  public void setPosition(int pos)
  {
    position=pos;
    update();
  }
  public int getPosition()
  {
    return motor.getSelectedSensorPosition(0,0,0);
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
