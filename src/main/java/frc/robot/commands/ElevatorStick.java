/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorStick extends Command {
  public ElevatorStick() {
    requires(Robot.getElevator());
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.getElevator().setVelocity(Robot.getOI().getElevatorY()); 
    if (Robot.getMap().getSys("elevator") == 2)
    {
      System.out.println("Elevator Position: " + Robot.getElevator().getPosition());
      System.out.println("Elevator Setpoint: " + Robot.getElevator().getSetpoint());
      System.out.println("Elevator Y: " + Robot.getOI().getElevatorY());
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }
}
