// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.turret.Launcher;

/** Stop the launcher wheel */
public class StopLauncher extends CommandBase {
  private final Launcher m_launcher;

  /**
   * Creates a new RunLauncher command
   *
   * @param launcher The Launcher subsystem.
   */
  public StopLauncher(Launcher launcher) {
    m_launcher = launcher;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(launcher);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_launcher.setSpeed(0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
