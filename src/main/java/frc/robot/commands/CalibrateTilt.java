package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Tilt;
import frc.robot.Robot; 

class CalibrateTilt extends Command
{
private double encoderconstant;
private Tilt tiltmechanism = Robot.getTilt(); 

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
   t.moveMotor(0.3);
   }
   
   @Override
   protected boolean isFinished()
   {
      return t.isSwitchieClosed();
   }
   
   @Override 
   protected void end()
   {
   encoderconstant = t.getMotorEncoderValue(); 
   encoderconstant = encoderconstant/90; 
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
