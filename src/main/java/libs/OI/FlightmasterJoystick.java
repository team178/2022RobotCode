/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package libs.OI;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


public class FlightmasterJoystick {
    //JOYSTICK buttons
	public Joystick joystick;
	public Button trigger;
	public Button headBottom;
	public Button headLeft;
	public Button headRight;
	public Button leftPadTop1;
	public Button leftPadTop2;
	public Button leftPadTop3;
	public Button leftPadBottom3;
	public Button leftPadBottom2;
	public Button leftPadBottom1;
	public Button rightPadTop3;
	public Button rightPadTop2;
	public Button rightPadTop1;
	public Button rightPadBottom1;
	public Button rightPadBottom2;
	public Button rightPadBottom3;
    
    public FlightmasterJoystick(int port) {
		joystick = new Joystick(port);
		trigger = new JoystickButton(joystick, 1);
		headBottom = new JoystickButton(joystick, 2);
		headLeft = new JoystickButton(joystick, 3);
		headRight = new JoystickButton(joystick, 4);
		leftPadTop1 = new JoystickButton(joystick, 5);
		leftPadTop2 = new JoystickButton(joystick, 6);
		leftPadTop3 = new JoystickButton(joystick, 7);
		leftPadBottom3 = new JoystickButton(joystick, 8);
		leftPadBottom2  = new JoystickButton(joystick, 9);
		leftPadBottom1 = new JoystickButton(joystick, 10);
		rightPadTop3 = new JoystickButton(joystick, 11);
		rightPadTop2 = new JoystickButton(joystick, 12);
		rightPadTop1 = new JoystickButton(joystick, 13);
		rightPadBottom1 = new JoystickButton(joystick, 14);
		rightPadBottom2 = new JoystickButton(joystick, 15);
		rightPadBottom3 = new JoystickButton(joystick, 16);
    }

    public double getX() {
		return joystick.getX(); //joystick.getRawAxis(0);
	}
	
	public double getY() {
		return joystick.getY(); //joystick.getRawAxis(1);
	}

	public double getTwist() {
		return -joystick.getTwist(); //joystick.getRawAxis(2);
	}

	/**
	 * @return the raw slider value that retuns 0 to +1 insteaad of -1 to +1
	 */
	public double getSlider() {
        return (joystick.getRawAxis(3) + 1) / 2;
    }

	public void printJoystickChannels() {
		System.out.println("X channel: " + joystick.getXChannel());
		System.out.println("Y channel: " + joystick.getYChannel());
		System.out.println("Z channel: " + joystick.getZChannel());
		System.out.println("Twist channel: " + joystick.getTwistChannel());
	}
}