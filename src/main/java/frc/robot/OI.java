/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
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
    {
      buttons[i] = new JoystickButton(ctrl, i);
    }
    
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
      // 6 = right bumper
      buttons[6].whenPressed(new Unload());
      buttons[6].whenReleased(new StopWheel());
      
      // 5 = left bumper
      buttons[5].toggleWhenPressed(new Intake());
    }

    if(Robot.getMap().getSys("elevator") != 0)
    {
      // 1 = A
      buttons[1].whenPressed(new ElevatorSetPosition(100));
      // 2 = B
      buttons[2].whenPressed(new ElevatorSetPosition(0));
    }
    if(Robot.getMap().getSys("tilt") != 0)
    {
      // 1 = A
      buttons[1].whenPressed(new TiltSetPosition(100));
      // 3 = X
      buttons[3].whenPressed(new TiltSetPosition(0));
      // 4 = Y
      buttons[4].whenPressed(new TiltSetPosition(100));
    
    }
    if(Robot.getMap().getSys("drive") != 0)
    {
    }
  }

  public void resetController ()
  {

  }

  public void teleopInit()
  {
    
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
  /**
   * This method returns the joystick tilt on the contoller
   */
  public double getElevatorY()
  {
    return -ctrl.getY(Hand.kRight);
  }
}
