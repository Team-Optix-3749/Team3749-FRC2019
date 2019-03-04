package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;
import frc.robot.Robot; 

class CalibrateTilt extends Command
{
   private Tilt t = Robot.getTilt(); 

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
      t.rawMove(0.3);
   }
   
   @Override
   protected boolean isFinished()
   {
      return t.atTop();
   }
   
   @Override 
   protected void end()
   {
      System.out.println(t.getPosition());
   }
   
   void calibrate()
   {
   }
   
   @Override
   protected void interrupted()
   {
   }
   
}
