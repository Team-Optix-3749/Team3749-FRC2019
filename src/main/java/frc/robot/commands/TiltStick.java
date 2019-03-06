/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TiltStick extends Command {
  public TiltStick() {
    requires(Robot.getTilt());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    double speed = Robot.getOI().getTiltY() * 2;
    if (Robot.getTilt().getPosition() > 15 && Robot.getTilt().getPosition() < 85)
    {
      Robot.getElevator().setBottom(20);
      if (Robot.getElevator().getPosition() > 15)
        Robot.getTilt().setVelocity(speed);
    }
    else
    {
      Robot.getElevator().setBottom(0);
      Robot.getTilt().setVelocity(speed);
    }

    if (Robot.getMap().getSys("tilt") == 2)
    {
      System.out.println("Tilt Position: " + Robot.getTilt().getPosition());
      System.out.println("Tilt Setpoint: " + Robot.getTilt().getSetpoint());
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
}
