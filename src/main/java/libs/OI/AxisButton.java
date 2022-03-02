package libs.OI;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;

/**
 * Turn a GenericHID axis into a button
 */
public class AxisButton extends Button {

    private final GenericHID m_joystick;
    private final int m_axis;
    private final double m_deadzone;

    public AxisButton(GenericHID joystick, int axis) {
        m_joystick = joystick;
        m_axis = axis;
        m_deadzone = 0;
    }
    
    public AxisButton(GenericHID joystick, int axis, double deadzone) {
        m_joystick = joystick;
        m_axis = axis;
        m_deadzone = deadzone;
    }

    @Override
    public boolean get() {
        return Math.abs(m_joystick.getRawAxis(m_axis)) > (0 + m_deadzone);
    }
}
