package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import java.lang.IllegalArgumentException;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Elevator {

    private TalonSRX elevatorMotor; 
    private DigitalInput limitSwitch;

    private int maxPos; //the maximum position to which the elevator can go to
    private int minPos = 0; //the minimum position to which the elevator can go to 
    private int P; 

    /*
    * This method will be used to determine
    * what position we want the elevator to 
    * move to
    */
    

    Elevator() {
        
        elevatorMotor = new TalonSRX(42); 
        //instantiate the elevator motor idk what id to use so I used the one from last year
        limitSwitch = new DigitalInput(1);
        elevatorMotor.setSelectedSensorPosition(0, 0, 100);
    }
    
    void setPosition(int pos) {

        //check to make sure position is within requirements(set by calibrate())
        if(pos > maxPos || pos < minPos) {
           
            //throw an error here OR AN ASSERTION
        }

        elevatorMotor.set(ControlMode.Position, pos);
    }
        
        //use the current position and an encoder to move the elevator to the position
        //asked by the method
    
    /*
    * This method will be used to set the maximum 
    * and minimum positions for the elevator range
    * using limit switches
    */

    /*
    void calibrate() {
        elevatorMotor.setSelectedSensorPosition(0, 0, 100);
        //use current position and set it to the minimum
        while (limitSwitch.get()) {
            Timer.delay(10);
            elevatorMotor.set(ControlMode.PercentOutput, -0.12);
            //continuously move up until limit switch is hit
        }
        //check current position of the robot and hold it 
        maxPos = elevatorMotor.getSelectedSensorPosition();

    } WILL TURN THIS INTO A COMMAND*/

}

//Add command later on once position/encoder stuff is figured out