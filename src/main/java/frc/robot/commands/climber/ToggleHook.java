package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;

public class ToggleHook extends CommandBase {
    private final Climber m_climber;

    public ToggleHook(Climber climber) {
        m_climber = climber;
        // Use addRequirements() here to declare subsystem dependencies.
        addRequirements(climber);
    }
}
