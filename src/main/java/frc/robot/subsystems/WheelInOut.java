import edu.wpi.first.wpilibj.DigitalInput;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
edu.wpi.first.wpilibj.Encoder;

public class WheelInOut extends Subsystem
{
	private TalonSRX intakeMotor1;
	private TalonSRX intakeMotor2;
	private DigitalInput flywheelSwitch;
	private double speed;

	public WheelInOut()
	{
		flywheelSwitch = new DigitalInput(1);
		speed = 0;
		intakeMotor1 = new TalonSRX(1);
		intakeMotor2 = new TalonSRX(2);
		intakeMotor2.setInverted(true);
	}

	private void setSpeed(int newSpeed) 
	{
		speed = newSpeed;
		intakeMotor1.set(newSpeed);
		intakeMotor2.set(newSpeed);
	}

	public void intake()
	{
		if(intakeMotor1.getInverted())
		{
			intakeMotor1.setInverted(false);
			intakeMotor2.setInverted(true);
		}
		
		setSpeed(.3);
	}
	
	public void unload()
	{
		if(!intakeMotor1.getInverted())
		{
			intakeMotor1.setInverted(false);
			intakeMotor2.setInverted(true);
		}
		
		setSpeed(.3);
	}

	boolean hasCargo() {return flywheelSwitch.get();}	
}
