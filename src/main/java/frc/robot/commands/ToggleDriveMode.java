/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ToggleDriveMode extends Command {
  // set fast driving mode to opposite (!) of what it originally was
  @Override
  protected void execute() {
    Robot.getDrive().setFastMode(!Robot.getDrive().isFastMode());
  }

  // immediately end command (only flip toggle once)
  @Override
  protected boolean isFinished() {
    return true;
  }
}
