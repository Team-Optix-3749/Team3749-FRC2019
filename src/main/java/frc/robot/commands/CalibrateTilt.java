package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;

class CalibrateTilt extends Command
{
private double encoderconstant;

   CalibrateTilt()
   {
   
   }

   @Override
   protected void initialize()
   {
  
   }
   
   @Override
   protected void execute()
   {
   //have the motor go all the way down then calculate the encoder constant
   System.out.println(encoderconstant);
   }
   
   @Override
   protected boolean isFinished()
   {
      return Tilt.isSwitchieClosed();
   }
   
   @Override 
   protected void end()
   {
   }
   
   void calibrate()
   {
   }
   
   @Override
   protected void interrupted()
   {
   }
   
}
