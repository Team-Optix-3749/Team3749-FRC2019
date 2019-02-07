package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;

class CalibrateTilt extends Command
{
private double encoderconstant;
private Tilt tiltmechanism; 

   CalibrateTilt(Tilt t)
   {
   tiltmechanism = t; 
   }

   @Override
   protected void initialize()
   {
   
   }
   
   @Override
   protected void execute()
   {
   t.moveMotor();
   }
   
   @Override
   protected boolean isFinished()
   {
      return Tilt.isSwitchieClosed();
   }
   
   @Override 
   protected void end()
   {
   t.getMotorEncoderValue(); 
   //calculate encoder constant
   System.out.println(encoderconstant);
   }
   
   void calibrate()
   {
   }
   
   @Override
   protected void interrupted()
   {
   }
   
}
