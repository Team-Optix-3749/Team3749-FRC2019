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
import frc.robot.commands.DriveStick;

/**
 * class Tilt
 */
public class Tilt extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private SpeedControllerGroup leftSide, rightSide;
  // expensive gyro from Kauai Labs, the purple board on top of the roboRIO
  // https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/
  private AHRS gyro;

  
  // if the robot is trying going straight currently
  private boolean isStraight = false;
  // PID to control driving alignment (no veering!)
  // PID is a closed-loop control algorithm that uses sensor input to determine motor output
  private PIDController drivePID;

  public Tilt ()
  {
    WPI_TalonSRX leftF = new WPI_TalonSRX(10);
    /*
      have two motor controllers following the TalonSRX
      a VictorSPX is cheaper and has less features, so just having it follow
      is good enough
      */
    WPI_VictorSPX leftM = new WPI_VictorSPX(21);
    leftSide = new SpeedControllerGroup(leftF, leftM);

    // same thing on the other side
    WPI_TalonSRX rightF = new WPI_TalonSRX(11);
    WPI_VictorSPX rightM = new WPI_VictorSPX(20);
    rightSide = new SpeedControllerGroup(rightF, rightM);
    
    // gyro based on SPI (faster than other input)
    gyro = new AHRS(SPI.Port.kMXP);

    // pid constants
    double kp = 0.1, ki = 0.05, kd = 0.1;
    // use PID controller to calculate PID efficiently but don't give it to motor controller
    // instead, just use .get() for driving adjustments
    // consider just using PIDSubsystem
    drivePID = new PIDController(kp, ki, kd, gyro, new EmptyPIDOut());
    drivePID.setInputRange(-3, 3);
    drivePID.setOutputRange(-0.3, 0.3);
    drivePID.setSetpoint(0);
  }
  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new TiltStick());
  }
}
