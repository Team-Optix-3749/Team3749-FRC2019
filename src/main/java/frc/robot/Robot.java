/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;

import frc.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot
{
  // all 5 subsystems
  private static DriveBase drive;
  private static WheelInOut flywheel;
  private static Tilt tilt;
  private static Elevator elevator;
  private static Climb climb;
  
  // operator interface
  private static OI oi;
  // map of ports and other info
  private static RobotMap map;

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit()
  {
    map = new RobotMap();

    // starts and sets up the camera with display settings
    initCamera();

    if (map.getSys("drive") != 0)
      drive = new DriveBase();
    if (map.getSys("wheelio") != 0)
      flywheel = new WheelInOut();
    if (map.getSys("tilt") != 0)
      tilt = new Tilt();
    if (map.getSys("elevator") != 0)
      elevator = new Elevator();
    if (map.getSys("climb") != 0)
      climb = new Climb();
    // must be at end (after subsystems and RobotMap)
    oi = new OI();
  }

  public void initCamera()
  {
    // start running camera from roboRIO
    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    camera.setBrightness(8);
    // camera.setExposureManual(5);
    camera.setExposureAuto();
  }
  /**
   * This method gets the Tilt subsystem
   * @return tilt
   */
  public static Tilt getTilt()
  {
    return tilt;
  }

  /**
   * a simple getter method for the DriveBase subsystem
   * @return drive base
   */
  public static DriveBase getDrive()
  {
    return drive;
  }
   /**
   * a simple getter method for the Flywheel subsystem
   * @return flywheel
   */
   public static WheelInOut getFlywheel()
   {
      return flywheel;
   }
   
   /**
    * a simple getter method for the Elevator subsystem 
    * @return Elevator
    */
  public static Elevator getElevator() {
    return elevator;
  }
  /**
   * a simple getter method for the Climb subsystem 
   * @return Climb
   */
  public static Climb getClimb() {
    return climb;
  }
  /**
  
   * a simple getter method for the operator interface (for controls)
   * @return drive base
   */
  public static OI getOI()
  {
    return oi;
  }
  /**

   * a simple getter method for the robot map
   * @return drive base
   */
  public static RobotMap getMap()
  {
    return map;
  }
  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic()
  {
  }
  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit()
  {
  }
  @Override
  public void disabledPeriodic()
  {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit()
  {
    
  }
  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic()
  {
    Scheduler.getInstance().run();
  }
  @Override
  public void teleopInit()
  {
    
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic()
  {
    Scheduler.getInstance().run();
  }
  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic()
  {
  }
}
