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
  private SpeedControllerGroup leftSide, rightSide;
  private DifferentialDrive drive;
  // expensive gyro from Kauai Labs, the purple board on top of the roboRIO
  // https://pdocs.kauailabs.com/navx-mxp/software/roborio-libraries/java/
  private AHRS gyro;

  // if the robot is trying going straight currently
  private boolean isStraight = false;
  private double setpoint;
  private double adjust;

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
      // rot = 0;
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
      System.out.println(adjust);
    }
    
    if (Robot.getMap().getSys("drive") == 2)
      System.out.println("Forward power = " + fwd + ", adjust = " + adjust * fwd);
    // offset rotational constant to actually move properly
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
  public double locateTarget()
  {
    double[] defaultValue = new double[0];
    double[] xPos = NetworkTableInstance.getDefault().getTable("GRIP")
        .getSubTable("greenBlob").getEntry("x").getDoubleArray(defaultValue);
    double[] sizes = NetworkTableInstance.getDefault().getTable("GRIP")
    .getSubTable("greenBlob").getEntry("size").getDoubleArray(defaultValue);
    if(xPos.length == 0) {
      // didn't locate anything, send error flag
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

    //adding listener code
    // NetworkTableInstance inst = NetworkTableInstance.getDefault();
    // NetworkTable table = inst.getTable("GRIP").getSubTable("greenBlob");
    // NetworkTableEntry xEntry = table.getEntry("x");
    // inst.startClientTeam(3749);

    // //what is value???????

    // //src = https://wpilib.screenstepslive.com/s/currentCS/m/75361/l/843364-listening-for-value-changes
    // table.addEntryListener("x", (table,key,entry,value,flags) -> {
    //   System.out.println("X changed value: " + value.getValue());
    // }, EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);
  }
}
