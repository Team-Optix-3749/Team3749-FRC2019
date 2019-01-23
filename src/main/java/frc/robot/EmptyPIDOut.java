/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.PIDOutput;

/**
 * class EmptyPIDOut allows for a PIDOutput (such as a motor controller)
 * to be replaced by no functionality, for use with the PIDController class
 * when no motor output is required or desired
 */
public class EmptyPIDOut implements PIDOutput
{
    /**
     * override default functional pid write to output
     */
    public void pidWrite (double output)
    {
        // do nothing!
    }
}
