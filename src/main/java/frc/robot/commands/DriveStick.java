/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class DriveStick extends Command
{
  public DriveStick()
  {
    requires(Robot.getDrive());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute()
  {
    if (Robot.getDrive().isFastMode())
      Robot.getDrive().arcadeDrive(Robot.getOI().getDriveY() * 1.0, Robot.getOI().getDriveX() * 0.4);
    else
      Robot.getDrive().arcadeDrive(Robot.getOI().getDriveY() * 0.5, Robot.getOI().getDriveX() * 0.2);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished()
  {
    return false;
  }
}
