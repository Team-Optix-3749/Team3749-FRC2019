package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;

class CalibrateTilt extends Command
{
DigitalInput switchie2;
TalonSRX motor2; 
   
   CalibrateTilt()
   {
   
   }

   @Override
   protected void initialize()
   {
      switchie2 = Tilt.getswitchie(); 
      motor2 = Tilt.getmotor();
   }
   
   @Override
   protected void execute()
   {
   
   }
   
   @Override
   protected boolean isFinished()
   {
      return switchie2.get();
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
