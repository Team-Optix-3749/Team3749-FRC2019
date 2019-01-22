/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;

import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DriveStick;

/**
 * Add your docs here.
 */
public class DriveBase extends Subsystem {
  private TalonSRX leftSide, rightSide;
  private AHRS gyro;

  private boolean isStraight = false;
  private double gyroAdjust = 0;

  public DriveBase ()
  {
    leftSide = new TalonSRX(10);
    /*
      have two motor controllers following the TalonSRX
      a VictorSPX is cheaper and has less features, so just having it follow
      is good enough
      */
    BaseMotorController left2 = new VictorSPX(11);
    BaseMotorController left3 = new VictorSPX(12);
    left2.follow(leftSide);
    left3.follow(leftSide);

    // same thing on the other side
    rightSide = new TalonSRX(13);
    BaseMotorController right2 = new VictorSPX(14);
    BaseMotorController right3 = new VictorSPX(15);
    right2.follow(rightSide);
    right3.follow(rightSide);

    gyro = new AHRS(SPI.Port.kMXP);
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
    // brutally untested algorithm!

    // if user wants robot to go straight
    if(rot == 0) {
      // if it wasn't already going straight
      if(isStraight == false) {
        // set angle to 0 as a reference pt
        // for future changes
        gyro.reset();
        isStraight = true;
      }
      
      // anything non-zero is an error (go straight!)
      double gyroError = gyro.getAngle(); 
      // based on error, change adjust constant proportionally
      gyroAdjust += -0.1 * gyroError;
    } else {
      // doesn't want to go straight
      isStraight = false; 
    }
    // offset rotational constant to actually move properly
    rot += gyroAdjust * fwd;
    
    // check w/ debugging that gyro is working
    System.out.println(gyro.getAngle());
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
    // use the calculated values in actual output
    tankDrive (L, R);
  }

  public void tankDrive (double left, double right)
  {
    leftSide.set(ControlMode.PercentOutput, left);
    rightSide.set(ControlMode.PercentOutput, right);
  }
}
