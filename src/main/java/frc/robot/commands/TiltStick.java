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

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() 
  {
    Robot.getTilt().setVelocity(Robot.getOI().getTiltY() * 0.5);
    System.out.println("Sensor Position: " + Robot.getTilt().getPosition());
    System.out.println("Sensor Setpoint: " + Robot.getTilt().getSetpoint());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
    //we shoud actually use a wii remote instead of x box.
    //lemme explain - tilt for turning robot, 1, for back, 2 for go
    //front pad for height of ball, low for hatch, a for flywheels
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
