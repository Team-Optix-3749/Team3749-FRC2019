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
  /**
   * constructor OI connects the controller and buttons to the different commands
   */
  public OI ()
  {
    ctrl = new XboxController(0);
    // Button button = new JoystickButton(ctrl, 1);
    // button.whenPressed(null);
    // button.whileHeld(null);
    // button.whenReleased(null);
    
    //JoystickButton xButton = new JoystickButton(ctrl, 3);
    // xButton.whenReleased(new SetTilt());
    
    if(Robot.getMap().getSys("wheelio") != 0)
    {
      JoystickButton leftBumper = new JoystickButton(ctrl, 5);
      JoystickButton rightBumper = new JoystickButton(ctrl, 6);

      rightBumper.whenPressed(new Unload());
      rightBumper.whenReleased(new StopWheel());
      
      leftBumper.whenPressed(new Intake());
      leftBumper.whenReleased(new StopWheel());
    }
    
    JoystickButton A = new JoystickButton(ctrl, 1);
    JoystickButton X = new JoystickButton(ctrl,3);
    JoystickButton Y = new JoystickButton(ctrl,4);
    JoystickButton B = new JoystickButton(ctrl,2);
    JoystickButton menu = new JoystickButton(ctrl,8);
    //press down left stick
    JoystickButton select = new JoystickButton(ctrl,7);
    //press down right stick
    JoystickButton right = new JoystickButton(ctrl,10);

    if(Robot.getMap().getSys("elevator") != 0)
    {
      A.whenPressed(new ElevatorSetPosition(100));
      B.whenPressed(new ElevatorSetPosition(0));
    }
    if(Robot.getMap().getSys("tilt") != 0)
    {
      A.whenPressed(new TiltSetPosition(100));
      X.whenPressed(new TiltSetPosition(0));
      Y.whenPressed(new TiltSetPosition(100));
    
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
    return ctrl.getX(Hand.kLeft);
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
    return ctrl.getY(Hand.kRight);
  }
}
