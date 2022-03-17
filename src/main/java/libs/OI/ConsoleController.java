/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package libs.OI;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.*;

/**
 * Add your docs here.
 */
public class ConsoleController {

    public Joystick controller;
    public static double sensitivity = 1;
    public static double turnLimit = 1;
    public static double fowardLimit = 1; 
    
    public Button a;
    public Button b;
    public Button x;
    public Button y;
    
    public Button leftBumper;
    public Button rightBumper;
    
    public Button back;
    public Button start;
    
    public POVButton rightDPAD;  
    public POVButton leftDPAD;
    public POVButton topDPAD;
    public POVButton bottomDPAD; 

    public POVButton rightBottomDPAD; 
    public POVButton leftBottomDPAD; 
    public POVButton rightTopDPAD;
    public POVButton leftTopDPAD;

    public AxisButton leftTrigger; 
    public AxisButton rightTrigger;    

    public ConsoleController(int port) {
        controller = new Joystick(port);
        a = new JoystickButton(controller, 1);
        a = new JoystickButton(controller, 1);
        a = new JoystickButton(controller, 1);
        b = new JoystickButton(controller, 2);
        x = new JoystickButton(controller, 3);
        y = new JoystickButton(controller, 4);
        
        leftBumper = new JoystickButton(controller, 5);
        rightBumper = new JoystickButton(controller, 6);
        
        back = new JoystickButton(controller, 7);
        start = new JoystickButton(controller, 8);
        
        rightDPAD = new POVButton(controller, 90);
        leftDPAD = new POVButton(controller, 270);
        topDPAD = new POVButton(controller, 0);
        bottomDPAD = new POVButton(controller, 180);

        rightBottomDPAD = new POVButton(controller, 135);
        leftBottomDPAD = new POVButton(controller, 225);
        rightTopDPAD = new POVButton(controller, 45);
        leftTopDPAD = new POVButton(controller, 315);

        leftTrigger = new AxisButton(controller, 2);
        rightTrigger = new AxisButton(controller, 3);
    }

    public double getLeftStickX() {
	    return controller.getRawAxis(0);
    }
	
    public double getLeftStickY() {
	    return -(controller.getRawAxis(1));
    }
	
    public double getRightStickX() {
	    return controller.getRawAxis(4);
    }
	
    public double getRightStickY() {
	    return -(controller.getRawAxis(5));
    }
	
    public double getLeftTrigger() {
	    return controller.getRawAxis(2);
    }
	
    public double getRightTrigger() {
        return controller.getRawAxis(3);
    }
}