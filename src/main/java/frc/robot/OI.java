/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.buttons.POVButton;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
  // controller used for main controls (xbox controller)
  private XboxController ctrl;
  private JoystickButton[] buttons;
  private POVButton[] dpad;

  // how many buttons in the controller
  private final int BUTTON_RANGE = 10;
  /**
   * constructor OI connects the controller and buttons to the different commands
   */
  public OI ()
  {
    ctrl = new XboxController(0);

    // indexes start at 1 for buttons
    buttons = new JoystickButton[BUTTON_RANGE+1];
    for (int i = 1; i <= BUTTON_RANGE; i ++)
      buttons[i] = new JoystickButton(ctrl, i);
    for (int i = 0; i < 4; i ++)
      dpad[i] = new POVButton(ctrl, i * 90);
    
    /**
     * BUTTON MAP KEY:
     * 1 = A
     * 2 = B
     * 3 = X
     * 4 = Y
     * 5 = left bumper
     * 6 = right bumper
     * 7 = select
     * 8 = menu
     * 9 = left stick click
     * 10 = right stick click
     */
    
    if(Robot.getMap().getSys("wheelio") != 0)
    {
      // right bumper - init the timer start, and shoot
      buttons[6].whenPressed(null);
      buttons[6].whenReleased(new Unload());
      
      // left bumper - toggle the intake
      buttons[5].toggleWhenPressed(new Intake());
    }

    if(Robot.getMap().getSys("elevator") != 0)
    {
      // A
      buttons[1].whenPressed(new ElevatorSetPosition(0));
      // B
      buttons[2].whenPressed(new ElevatorSetPosition(0));
      // X
      buttons[3].whenPressed(new ElevatorSetPosition(30));
      // Y
      buttons[4].whenPressed(new ElevatorSetPosition(100));

      // dpad up - hatch pickup
      dpad[1].whenPressed(new ElevatorSetPosition(60));
      // dpad down - hatch dropoff
      dpad[3].whenPressed(new ElevatorSetPosition(50));
    }
    if(Robot.getMap().getSys("tilt") != 0)
    {
      // A
      buttons[1].whenPressed(new TiltSetPosition(100));
      // B
      buttons[2].whenPressed(new TiltSetPosition(0));
      // X - shoot cargo to rocket
      buttons[3].whenPressed(new TiltSetPosition(100));
      // Y - shoot cargo into hab (higher!)
      buttons[4].whenPressed(new TiltSetPosition(45));

      // dpad up - hatch carry
      dpad[1].whenPressed(new TiltSetPosition(100));
      // dpad down - hatch carry
      dpad[3].whenPressed(new TiltSetPosition(100));
      // dpad left - tilt in
      dpad[2].whenPressed(new TiltSetPosition(0));
      // dpad right - tilt out
      dpad[0].whenPressed(new TiltSetPosition(100));
    }
    if(Robot.getMap().getSys("drive") != 0)
    {
      // left joystick click should toggle fast/slow speed modes
      buttons[9].whenPressed(null);
      // left joystick click should toggle fast/slow speed modes
      buttons[10].whenPressed(new TargetAlign());
    }
    // needs to enable/disable the subsystem (setup command)
    // select (middle button left)
    buttons[7].whenPressed(null);
    // needs to toggle PID control modes for elevator and tilt subsystems
    // menu (middle button right)
    buttons[8].whenPressed(null);
  }

  public double getDriveY()
  {
    return -ctrl.getY(Hand.kLeft);
  }
  public double getDriveX()
  {
    return ctrl.getX(Hand.kRight);
  }

  /**
   * This method returns the joystick tilt on the contoller
   */
  public double getTiltY()
  {
    return ctrl.getTriggerAxis(Hand.kRight) - ctrl.getTriggerAxis(Hand.kLeft);
  }

  public double getClimbY()
  {
    double speed = 0;
    if (DriverStation.getInstance().getMatchTime() > 105)
      speed = ctrl.getY(Hand.kLeft);
    return speed;
  }

  /**
   * This method returns the joystick tilt on the contoller
   */
  public double getElevatorY()
  {
    return -ctrl.getY(Hand.kRight);
  }
}
