package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;

class CalibrateTilt extends Command
{

TalonSRX motor2; 
   
   CalibrateTilt()
   {
   
   }

   @Override
   protected void initialize()
   {
      motor2 = Tilt.getmotor();
   }
   
   @Override
   protected void execute()
   {
   
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
