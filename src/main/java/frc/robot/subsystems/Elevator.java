package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.DriveStick;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;


public class Elevator extends Subsystem {

    private TalonSRX elevatorMotor; 
    private DigitalInput limitSwitch;

    private int maxPos = 1; //the maximum position to which the elevator can go to
    final int minPos = 0; //the minimum position to which the elevator can go to 
    private double encoderScale = 99.999; //need to CHANGE used last year's number but number may need to change
    /*
    * This method will be used to determine
    * what position we want the elevator to 
    * move to
    */
    @Override    
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveStick());
    }

    public Elevator() {
        
        elevatorMotor = new TalonSRX(42); 
        //instantiate the elevator motor idk what id to use so I used the one from last year
        limitSwitch = new DigitalInput(1);
    }
    
    public TalonSRX getElevator() {
        return elevatorMotor;
    }
    
    public DigitalInput getLimitSwitch() {
        return limitSwitch;
    }
    
    public double getMaxPosition() {
        return maxPos; 
    }
    
    public void moveMotor(double speed) {
    elevatorMotor.set(ControlMode.PercentOutput, speed);
    }
    
    public void setMaxPosition(int max) {
        if(max<=1 && max>=0){
            maxPos = max; 
        }
    }
    public double getMotorEncoderValue()
    {
        return elevatorMotor.getSelectedSensorPosition();
    }
    
    public boolean isSwitchClosed() {
    return limitSwitch.get(); 
  }
    
    public void setPosition(double pos) { //set a value from 0-1

        //check to make sure position is within requirements(set by calibrate())
        if(pos > maxPos && pos > 1) {
            pos = maxPos;
        } else if(pos < minPos && pos < 0) {
            pos = minPos;
        }
        int scaledPos = (int)(pos * encoderScale);
        //use the current position and an encoder to move the elevator to the position
        //asked by the method
        elevatorMotor.set(ControlMode.Position, scaledPos);
    //Add a get method?
    /*
    * This method will be used to set the maximum 
    * and minimum positions for the elevator range
    * using limit switches
    */
    /*
    void calibrate() {
        //use current position and set it to the minimum
        while (limitSwitch.get()) {
            Timer.delay(10);
            //continuously move up until limit switch is hit
        }
        //check current position of the robot and hold it 
        

    } */

} 
}

//Add command later on once position/encoder stuff is figured out
