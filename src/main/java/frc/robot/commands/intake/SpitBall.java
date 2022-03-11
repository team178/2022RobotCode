// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.turret.Intake;

/** Run the Launcher at it's set speed */
public class SpitBall extends CommandBase {
  private final Intake m_intake;

  /**
   * Creates a new SpitBall command
   *
   * @param launcher The Launcher subsystem.
   */

  public SpitBall(Intake intake) {
    m_intake = intake;

    addRequirements(intake);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {


  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.setSpeed(.6);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
