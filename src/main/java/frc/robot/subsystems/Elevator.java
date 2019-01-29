package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import java.lang.IllegalArgumentException; 

public class Elevator {
    TalonSRX elevatorMotor; 
    DigitalInput limitSwitch;

    int maxPos; //the maximum position to which the elevator can go to
    int minPos; //the minimum position to which the elevator can go to 
    /*
    * This method will be used to determine
    * what position we want the elevator to 
    * move to
    */
    void setPosition(int pos) {

        //check to make sure position is within requirements(set by calibrate())
        if(pos > maxPos || pos < minPos) {
            throw new IllegalArguementException();
            //throw an error here
        }

        //use the current position and an encoder to move the elevator to the position
        //asked by the method
    
    /*
    * This method will be used to set the maximum 
    * and minimum positions for the elevator range
    * using limit switches
    */
    void calibrate() {
        //use current position and set it to the minimum
        while (limitSwitch.get()) {
            Timer.delay(10);
            //continuously move up until limit switch is hit
        }
        //check current position of the robot and hold it 
        

    }

    void elevatorInit() {
        elevatorMotor = new TalonSRX(42); 
        //instantiate the elevator motor idk what id to use so I used the one from last year
        limitSwitch = new DigitalInput(1);
    }
    }
}

//Add command later on once position/encoder stuff is figured out