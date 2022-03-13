package frc.robot.commands.climber;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class TomahawkMove extends CommandBase {
    private final Climber m_climber;
    private DoubleSupplier m_speed;

    public TomahawkMove(DoubleSupplier speed, Climber climber) {
        m_climber = climber;
        m_speed = speed;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(climber);
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        double inputSpeed = m_speed.getAsDouble();
        inputSpeed = ((Math.abs(inputSpeed) < .1) ? 0 : inputSpeed);

        m_climber.setClimberMotorSpeed(inputSpeed); //may need to adjust based on motor
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_climber.setClimberMotorSpeed(0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }
}
