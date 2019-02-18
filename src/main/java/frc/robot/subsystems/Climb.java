/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.ClimbStick;

/**
 * Add your docs here.
 */
public class Climb extends Subsystem {
  private VictorSPX motor;
  private DigitalInput limitSwitch;

  public Climb ()
  {
    motor = new VictorSPX(2);
    limitSwitch = new DigitalInput(1);
  }

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ClimbStick());
  }

  public void rawMove (double power)
  {
    motor.set(ControlMode.PercentOutput, power);
  }

  public boolean atEnd()
  {
    return !limitSwitch.get();
  }
}
