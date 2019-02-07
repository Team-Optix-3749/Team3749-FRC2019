/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.HashMap;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 * @author Rupin Mittal
 */
public class RobotMap
{
    private static HashMap <String, Integer> mapPWM; //the PWM port map
	private static HashMap <String, Integer> mapCAN; //the CAN port map
	private static HashMap <String, Integer> mapDIO; //the CAN port map
	private static HashMap <String, Integer> mapCTRL; //the controller port map

  public RobotMap ()
  {
    mapPWM = new HashMap<>();
	mapCAN = new HashMap<>();
    mapDIO = new HashMap<>();
    mapCTRL = new HashMap<>();

	// loading map values for drive
	setCAN("drive_lf", 10);
	setCAN("drive_lm", 21);
	setCAN("drive_lb", 23);
	setCAN("drive_rf", 11);
	setCAN("drive_rm", 20);
	setCAN("drive_rb", 22);
  }

  /**
	* Method to set a PWM port
	* @param String		name of what port is for (what you call it throughout the program)
	* @param int 		the port number
	*/
	public void setPWM (String name, int port)
	{
		mapPWM.put(name, port);
	}
	
	/**
	* Method to set a PWM port
	* @param String		name of what port is for (what you call it throughout the program)
	* @param int 		the port number
	*/
	public void setCAN (String name, int port)
	{
		mapCAN.put(name, port);
	}
	/**
	* Method to set a DIO port
	* @param String		name of what port is for (what you call it throughout the program)
	* @param int 		the port number
	*/
	public void setDIO (String name, int port)
	{
		mapDIO.put(name, port);
	}
	
	/**
	* Method to set a CTRL port
	* @param String		name of what port is for (what you call it throughout the program)
	* @param int 		the port number
	*/
	public void setCTRL (String name, int port)
	{
		mapCTRL.put(name, port);
	}
	
	/**
	* Method to get a PWM port
	* @param String		name of what port is for (what you call it throughout the program)
	*/
	public int getPWM (String name)
	{
		return mapPWM.get(name);
	}
	
	/**
	* Method to get a CAN port
	* @param String		name of what port is for (what you call it throughout the program)
	*/
	public int getCAN (String name)
	{
		return mapCAN.get(name);
	}
	
	/**
	* Method to get a DIO port
	* @param String		name of what port is for (what you call it throughout the program)
	*/
	public int getDIO (String name)
	{
		return mapDIO.get(name);
	}
	/**
	* Method to get a CTRL port
	* @param String		name of what port is for (what you call it throughout the program)
	*/
	public int getCTRL (String name)
	{
		return mapCTRL.get(name);
	}
}