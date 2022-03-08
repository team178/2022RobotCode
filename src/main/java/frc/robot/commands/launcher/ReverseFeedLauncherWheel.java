// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.launcher;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.turret.Feeder;
import frc.robot.subsystems.turret.Launcher;

/** Run the Launcher at it's set speed */
public class ReverseFeedLauncherWheel extends CommandBase {
  private final Launcher m_launcher;
  private final Feeder m_feeder;
  private double speed; 

  /**
   * Creates a new RunLauncher command
   *
   * @param launcher The Launcher subsystem.
   */

  public ReverseFeedLauncherWheel(Launcher launcher, Feeder feeder) {
    m_launcher = launcher;
    m_feeder = feeder;

    this.speed = LauncherConstants.kLauncherSpeed.getDouble(-0.05);
    m_feeder.setSpeed(-0.05);

    addRequirements(launcher);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_launcher.setSpeed(speed);
    m_feeder.setSpeed(speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_launcher.setSpeed(0);
    m_feeder.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
