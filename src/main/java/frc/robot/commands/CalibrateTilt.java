package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;

class CalibrateTilt extends Command
{
DigitalInput switchie2;
   
   CalibrateTilt()
   {
   
   }

   @Override
   protected void initialize()
   {
      switchie2 = Tilt.getswitchie(); 
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
