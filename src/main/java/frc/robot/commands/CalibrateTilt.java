package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

class CalibrateTilt extends Command
{
   
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
   }
   
   @Override
   protected boolean isFinished()
   {
      return switchie.get();
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
