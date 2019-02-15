/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class TargetAlign extends Command {
  private double heading;
  private final double TARGET_HEADING = 60;
  private double MAX_VELOCITY = 0.3;

  public TargetAlign() {
    requires(Robot.getDrive());
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (Robot.getDrive().locateTarget() != -1)
      heading = Robot.getDrive().locateTarget();

    double error = heading - TARGET_HEADING;
    double adj = error * 0.02;
    if (adj > MAX_VELOCITY)
      adj = MAX_VELOCITY;
    if (adj < -MAX_VELOCITY)
      adj = -MAX_VELOCITY;
    Robot.getDrive().arcadeDrive(0, Math.abs(error) > 20 ? -0.5 * Math.abs(error)/error : error / -40);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(Robot.getDrive().getHeading() - heading) < 5;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
