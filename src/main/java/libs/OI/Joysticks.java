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
import frc.robot.Constants.OIConstants;


public class Joysticks {
    //JOYSTICK buttons
	public static Joystick joystickMain = new Joystick(OIConstants.kJoystickPort);
	public Button trigger = new JoystickButton(joystickMain, 1);
	public Button headBottom = new JoystickButton(joystickMain, 2);
	public Button headLeft = new JoystickButton(joystickMain, 3);
	public Button headRight = new JoystickButton(joystickMain, 4);
	public Button leftPadTop1 = new JoystickButton(joystickMain, 5);
	public Button leftPadTop2 = new JoystickButton(joystickMain, 6);
	public Button leftPadTop3 = new JoystickButton(joystickMain, 7);
	public Button leftPadBottom3 = new JoystickButton(joystickMain, 8);
	public Button leftPadBottom2  = new JoystickButton(joystickMain, 9);
	public Button leftPadBottom1 = new JoystickButton(joystickMain, 10);
	public Button rightPadTop3 = new JoystickButton(joystickMain, 11);
	public Button rightPadTop2 = new JoystickButton(joystickMain, 12);
	public Button rightPadTop1 = new JoystickButton(joystickMain, 13);
	public Button rightPadBottom1 = new JoystickButton(joystickMain, 14);
	public Button rightPadBottom2 = new JoystickButton(joystickMain, 15);
	public Button rightPadBottom3 = new JoystickButton(joystickMain, 16);

	//AUX controller buttons
	public static Joystick xboxAux = new Joystick(OIConstants.kControllerPort); //Controller
	public Button auxA = new JoystickButton(xboxAux, 1);
	public Button auxB = new JoystickButton(xboxAux, 2);
	public Button auxX = new JoystickButton(xboxAux, 3);
	public Button auxY = new JoystickButton(xboxAux, 4);
	public Button auxLeftBumper = new JoystickButton(xboxAux, 5);
	public Button auxRightBumper = new JoystickButton(xboxAux, 6);
	public Button auxBack = new JoystickButton(xboxAux, 7);
    public Button auxStart = new JoystickButton(xboxAux, 8);
    
    public Joysticks() {
    }

    public double getX() {
		return joystickMain.getX(); //joystick.getRawAxis(0);
	}
	
	public double getY() {
		return joystickMain.getY(); //joystick.getRawAxis(1);
	}

	public double getTwist() {
		return -joystickMain.getTwist(); //joystick.getRawAxis(2);
	}

	/**
	 * @return the raw slider value that retuns 0 to +1 insteaad of -1 to +1
	 */
	public double getSlider() {
        return (joystickMain.getRawAxis(3) + 1) / 2;
    }
    
    //AUX controller accessor methods
	public double getLeftStickYAux() {
		return xboxAux.getRawAxis(1);
	}
	
	public double getRightStickYAux() {
		return xboxAux.getRawAxis(5);
	}

	public double getRightStickXAux() {
		return -xboxAux.getRawAxis(4);
	}
	
	public double getLeftTriggerAux() {
		return xboxAux.getRawAxis(2);
	}
	
	public double getRightTriggerAux() {
		return xboxAux.getRawAxis(3);
	}

	public void printJoystickChannels() {
		System.out.println("X channel: " + joystickMain.getXChannel());
		System.out.println("Y channel: " + joystickMain.getYChannel());
		System.out.println("Z channel: " + joystickMain.getZChannel());
		System.out.println("Twist channel: " + joystickMain.getTwistChannel());
	}
}