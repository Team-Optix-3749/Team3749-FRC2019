/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.networktables.*;
//import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.EmptyPIDOut;
import frc.robot.commands.DriveStick;

/**
 * class DriveBase controls the driving mechanism for the robot (6 wheel/6 motor drive! kit bot)
 */
public class DriveBase extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private TalonSRX leftSide, rightSide;
  // expensive gyro from Kauai Labs, the purple board on top of the roboRIO
  // https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/
  private AHRS gyro;

  // if the robot is trying going straight currently
  private boolean isStraight = false;
  private double setpoint;
  private double adjust;

  public DriveBase ()
  {
    leftSide = new TalonSRX(Robot.getMap().getCAN("drive_lf"));
    VictorSPX leftM = new VictorSPX(Robot.getMap().getCAN("drive_lm"));
    VictorSPX leftB = new VictorSPX(Robot.getMap().getCAN("drive_lb"));
    leftM.follow(leftSide);
    leftB.follow(leftSide);

    // same thing on the other side
    rightSide = new TalonSRX(Robot.getMap().getCAN("drive_rf"));
    VictorSPX rightM = new VictorSPX(Robot.getMap().getCAN("drive_rm"));
    VictorSPX rightB = new VictorSPX(Robot.getMap().getCAN("drive_rb"));
    rightM.follow(rightSide);
    rightB.follow(rightSide);

    leftSide.configOpenloopRamp(0.75);
    rightSide.configOpenloopRamp(0.75);

    // gyro based on SPI (faster than other input)
    gyro = new AHRS(SPI.Port.kMXP);
    gyro.reset();

    setpoint = 0;
    adjust = 0;
  }

  @Override
  public void initDefaultCommand()
  {
    setDefaultCommand(new DriveStick());
  }
  /**
   * method arcadeDrive converts a forward amount and left/right rotation amount to
   * left and right motor outputs for drive system
   * NOTE: hardcoded because we cannot use DifferentialDrive and WPI_ classes since 
   * that doesn't allow for full TalonSRX/VictorSPX features, including .follow()
   * 
   * @param fwd amount forward for robot to drive (-1 to 1)
   * @param rot amount left/right for robot to drive (-1 to 1), left is negative
   */
  public void arcadeDrive (double fwd, double rot)
  {
    // if user is trying to go forward, it might not be 100% accurate
    if (Math.abs(rot) < 0.1)
    {
      rot = 0;
      System.out.println("Driving straight");
    }
    // if user wants robot to go straight
    if(rot == 0) {
      // if it wasn't already going straight
      if(isStraight == false) {
        // trying to maintain the current angle heading
        setpoint = gyro.getAngle();
        isStraight = true;
      }
      adjust = 1 * (gyro.getAngle() - setpoint);
      if (adjust > 0.3)
        adjust = 0.3;
      if (adjust < -0.3)
        adjust = -0.3;
    }
    
    if (Robot.getMap().getSys("drive") == 2)
      System.out.println("Forward power = " + fwd + ", adjust = " + adjust * fwd);
    // offset rotational constant to actually move properly
    // rot += adjust;

    double[] pwr = arcadeToTank(fwd, rot);

    tankDrive(pwr[0], pwr[1]);
  }

  private double[] arcadeToTank(double fwd, double rot)
  {
    // left and right output to be calculated
    double L, R;
    // gets bigger of either fwd or rot
    double max = Math.abs(fwd);
    if (Math.abs(rot) > max)
      max = Math.abs(rot);
    // calc sum and difference btwn
    double sum = fwd + rot;
    double dif = fwd - rot;

    // case by case convert fwd and rot input to left and right motor output
    if (fwd >= 0)
    {
      if (rot >= 0)
      {
        L = max;
        R = dif;
      }
      else
      {
        L = sum;
        R = max;
      }
    }
    else
    {
      if (rot >= 0)
      {
        L = sum;
        R = -max;
      }
      else
      {
        L = -max;
        R = dif;
      }
    }

    double[] power = {L, R};
    return power;
  }

  public double getHeading()
  {
    return gyro.getAngle();
  }

  public void tankDrive (double left, double right)
  {
    leftSide.set(ControlMode.PercentOutput, left);
    rightSide.set(ControlMode.PercentOutput, right);
  }
  public double locateCargo()
  {
    double[] defaultValue = new double[0];
    double[] xPos = NetworkTableInstance.getDefault().getTable("GRIP")
        .getSubTable("greenBlob").getEntry("x").getDoubleArray(defaultValue);
    double[] sizes = NetworkTableInstance.getDefault().getTable("GRIP")
    .getSubTable("greenBlob").getEntry("size").getDoubleArray(defaultValue);
    if(xPos.length == 0) {
      // didn't locate anything, send failed flag
      return -1;
    }
    double biggest = 0;
    int i = 0;
    for (int j = 0; j < sizes.length; j ++)
    {
      if (sizes[j] > biggest)
      {
        biggest = sizes[j];
        i = j;
      }
    }

    // return the position of the biggest blob found
    return xPos[i];
  }
  
  public double locateLeftTape()
  {
    double[] defaultValue = new double[0];
    double[] x1 = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("x1").getDoubleArray(defaultValue);
    double[] x2 = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("x2").getDoubleArray(defaultValue);
    double[] angles = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("angle").getDoubleArray(defaultValue);
    if(x1.length == 0) {
      // didn't locate anything, send failed flag
      return -1;
    }
    return 0;
  }
  public double locateMiddleTape()
  {
    double[] defaultValue = new double[0];
    double[] x1 = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("x1").getDoubleArray(defaultValue);
    double[] x2 = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("x2").getDoubleArray(defaultValue);
    double[] angles = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("angle").getDoubleArray(defaultValue);
    if(x1.length == 0) {
      // didn't locate anything, send failed flag
      return -1;
    }
    for (int i = 0; i < angles.length; i ++)
      if (angles[i] > 360) angles[i] -= 180;
  
    return 0;
  }
  public double locateRightTape()
  {
    double[] defaultValue = new double[0];
    double[] x1 = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("x1").getDoubleArray(defaultValue);
    double[] x2 = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("x2").getDoubleArray(defaultValue);
    double[] angles = NetworkTableInstance.getDefault().getTable("GRIP")
      .getSubTable("tapeLines").getEntry("angle").getDoubleArray(defaultValue);
    if(x1.length == 0) {
      // didn't locate anything, send failed flag
      return -1;
    }
    return 0;
  }
}
