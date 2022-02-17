package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class LowerMast extends CommandBase {

    private final Climber m_climber;

    public LowerMast(Climber climber) {
        m_climber = climber;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(climber);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        m_climber.setExtendingArmWinchSpeed(0);
        m_climber.reset();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        m_climber.setExtendingArmWinchSpeed(-1); //may need to adjust based on winch
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        m_climber.setExtendingArmWinchSpeed(0);
    }
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return m_climber.getLowLimitSwitch().get();
    }
}
