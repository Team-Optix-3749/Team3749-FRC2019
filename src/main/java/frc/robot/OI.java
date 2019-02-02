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
  // a default button for specific controls
  private Button button;
  private Button button2;
  private Button button3;

  /**
   * constructor OI connects the controller and buttons to the different commands
   */
  public OI ()
  {
    ctrl = new XboxController(0);
    button = new JoystickButton(ctrl, 1);
    button.whenPressed(null);
    button.whileHeld(null);
    button.whenReleased(null);
    
    button2 = new JoystickButton(ctrl, 3);
    button2.whenReleased(new SetTilt());
    
    button3 = new Button(ctrl, 9);
    button3.whileHeld(new Intake());
    
    button3 = new Button(ctrl, 10);
    button3.whileHeld(new Unload());
  }

  public double getDriveX()
  {
    return ctrl.getY(Hand.kRight);
  }
  public double getDriveY()
  {
    return ctrl.getX(Hand.kLeft);
  }
}
