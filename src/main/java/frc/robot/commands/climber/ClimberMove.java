package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ClimberMove extends CommandBase {
    private final Climber m_climber;
    private DoubleSupplier m_mastSpeed;
    private DoubleSupplier m_tomahawkSpeed;

    public ClimberMove(DoubleSupplier mastSpeed, DoubleSupplier tomahawkSpeed, Climber climber) {
        m_climber = climber;
        m_mastSpeed = mastSpeed;
        m_tomahawkSpeed = tomahawkSpeed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(climber);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_climber.setClimberMotorSpeed(m_tomahawkSpeed.getAsDouble()); //may need to adjust based on motor
        m_climber.setExtendingArmWinchSpeed(m_mastSpeed.getAsDouble());
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_climber.setClimberMotorSpeed(0);
        m_climber.setExtendingArmWinchSpeed(0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return (m_climber.getLowLimitSwitch().get() || !m_climber.getHighLimitSwitch().get());
    }
}
