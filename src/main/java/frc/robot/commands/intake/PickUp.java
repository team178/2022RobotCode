// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.turret.Intake;

/** Pick up balls with the Intake */
public class PickUp extends CommandBase {
  
  private final Intake m_intake;

  /**
   * Creates a new PickUp command.
   *
   * @param intake the Intake subsystem.
   */
  public PickUp(Intake intake) {
    m_intake = intake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // m_intake.close();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!m_intake.isDeployed()) {
      m_intake.deploy();
    }

    if (m_intake.getSwitchState()) {
      m_intake.enableBouncer();
    }

    m_intake.setSpeed(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) throws RuntimeException {
    m_intake.disableBouncer();
    m_intake.retract();
    m_intake.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
