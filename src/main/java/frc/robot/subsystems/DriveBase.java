/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.networktables.*;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.commands.DriveStick;

/**
 * class DriveBase controls the driving mechanism for the robot (6 wheel/6 motor drive! kit bot)
 */
public class DriveBase extends Subsystem
{
  // leading motor controllers, have built-in closed loop control
  private SpeedControllerGroup leftSide, rightSide;
  private DifferentialDrive drive;

  // expensive gyro from Kauai Labs, the purple board on top of the roboRIO
  // https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/
  private AHRS gyro;

  // if the robot is trying going straight currently
  private boolean isStraight = false;
  private double setpoint;
  private double adjust;

  private boolean fastMode;

  public DriveBase ()
  {
    WPI_TalonSRX leftF = new WPI_TalonSRX(Robot.getMap().getCAN("drive_lf"));
    WPI_VictorSPX leftM = new WPI_VictorSPX(Robot.getMap().getCAN("drive_lm"));
    WPI_VictorSPX leftB = new WPI_VictorSPX(Robot.getMap().getCAN("drive_lb"));
    leftSide = new SpeedControllerGroup(leftF, leftM, leftB);

    // same thing on the other side
    WPI_TalonSRX rightF = new WPI_TalonSRX(Robot.getMap().getCAN("drive_rf"));
    WPI_VictorSPX rightM = new WPI_VictorSPX(Robot.getMap().getCAN("drive_rm"));
    WPI_VictorSPX rightB = new WPI_VictorSPX(Robot.getMap().getCAN("drive_rb"));
    rightSide = new SpeedControllerGroup(rightF, rightM, rightB);

    drive = new DifferentialDrive(leftSide, rightSide);

    // gyro based on SPI (faster than other input)
    gyro = new AHRS(SPI.Port.kMXP);
    gyro.reset();

    setpoint = 0;
    adjust = 0;

    fastMode = true;
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
    // if user wants robot to go straight
    if(rot == 0) {
      // if it wasn't already going straight
      if(isStraight == false) {
        // trying to maintain the current angle heading
        setpoint = gyro.getAngle();
        isStraight = true;
      }
      // essentially a kI to compensate for mechanical errors (should balance out other parts)
      adjust += 0.01 * (setpoint - gyro.getAngle());
      if (adjust > 0.3)
        adjust = 0.3;
      if (adjust < -0.3)
        adjust = -0.3;
    }
    
    if (Robot.getMap().getSys("drive") == 2)
      System.out.println("Forward power = " + fwd + ", adjust = " + adjust * fwd);
    // offset rotational constant to actually move properly
    // WILL APPLY WHEN NEEDED
    // rot += adjust;

    drive.arcadeDrive(fwd, rot, false);
  }

  public double getHeading()
  {
    return gyro.getAngle();
  }

  public void tankDrive (double left, double right)
  {
    drive.tankDrive(left, right, false);
  }

  public void setFastMode(boolean fast)
  {
    fastMode = fast;
  }

  public boolean isFastMode()
  {
    return fastMode;
  }

  public double locateCargo()
  {
    double[] defaultValue = new double[0];
    double[] xPos = NetworkTableInstance.getDefault().getTable("GRIP")
        .getSubTable("cargoBlobs").getEntry("x").getDoubleArray(defaultValue);
    double[] sizes = NetworkTableInstance.getDefault().getTable("GRIP")
    .getSubTable("cargoBlobs").getEntry("size").getDoubleArray(defaultValue);
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
  
  private double locateLeftTape(double[] x1, double[] x2, double[] angles, double center)
  {
    if(x1.length == 0) {
      // didn't locate anything, send failed flag
      return -1;
    }
    int midLine = -1;
    for (int i = 0; i < x1.length; i ++)
    {
      if (angles[i] > 90 && angles[i] < 135)
      {
        if (midLine == -1)
          midLine = i;
        else 
          if (Math.abs(x1[i] + x2[i] - center * 2) < Math.abs(x1[midLine] + x2[midLine] - center * 2))
            midLine = i;
      }
    }
    return (x1[midLine] + x2[midLine])/2;
  }
  
  private double locateRightTape(double[] x1, double[] x2, double[] angles, double center)
  {
    if(x1.length == 0) {
      // didn't locate anything, send failed flag
      return -1;
    }
    int midLine = -1;
    for (int i = 0; i < x1.length; i ++)
    {
      if (angles[i] > 90 && angles[i] < 135)
      {
        if (midLine == -1)
          midLine = i;
        else 
          if (Math.abs(x1[i] + x2[i] - center * 2) < Math.abs(x1[midLine] + x2[midLine] - center * 2))
            midLine = i;
      }
    }
    return (x1[midLine] + x2[midLine])/2;
  }

  public double locateTape(boolean hatch)
  {
    double centerPos = 60;

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
    
    double left = locateLeftTape(x1, x2, angles, centerPos);
    double right = locateRightTape(x1, x2, angles, centerPos);

    if (hatch)
    {
      // -1 or left tape position (align to this for hatch)
      return left;
    }
    else
    {
      // couldn't find one of them :(
      if (left == -1 || right == -1)
      {
        return -1;
      }
      else
      {
        // found one, either right order
        if (left < right)
          return (left + right) / 2;
        else
          // wrong order, realign based on which it's closer to
          if (Math.abs(left - centerPos) < Math.abs(right - centerPos))
            return (left - right)/2 + left; // "left" tape was closer
          else
            return right - (left - right)/2; // right tape was closer
      }
    }
  }
}
