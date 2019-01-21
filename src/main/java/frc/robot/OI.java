/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.*;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI
{
  // controller used for main controls (xbox controller)
  private XboxController ctrl;
  // a default button for specific controls
  private Button button;

  /**
   * constructor OI connects the controller and buttons to the different commands
   */
  public OI ()
  {
    ctrl = new XboxController(0);

    button = new JoystickButton(ctrl, 0);
    button.whenPressed(null);
    button.whileHeld(null);
    button.whenReleased(null);
  }

  /**
   * method getCtrl gets the xbox controller used to control the robot
   * @return
   */
  public XboxController getCtrl()
  {
    return ctrl;
  }
  /**
   * method getStick gets the joystick used to control the robot
   * @return
   */
  public double getCtrlAxis(int port)
  {
    return ctrl.getRawAxis(port);
  }
  /**
   * method getStick gets the joystick used to control the robot
   * @return
   */
  public boolean getCtrlButton(int port)
  {
    return ctrl.getRawButton(port);
  }
}
