// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.turret.Intake;

/** Pick up balls with the Intake */
public class AutoPickUp extends PIDCommand {
  
  private final Intake m_intake;
  private final DriveTrain m_drivetrain;

  /**
   * Creates a new PickUp command.
   *
   * @param intake the Intake subsystem.
   */
  public AutoPickUp(Intake intake, DriveTrain drivetrain, double distance) {
    super(
        new PIDController(1, 0, 0), drivetrain::getDistance, distance, d -> drivetrain.tankDrive(d, d));

    m_drivetrain = drivetrain;
    m_drivetrain.reset();

    getController().setTolerance(0.01);
    m_intake = intake;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_intake, m_drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (!m_intake.isDeployed()) {
      m_intake.deploy();
    }

    m_intake.setSpeed(-.6);

    m_drivetrain.reset();
    super.initialize();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) throws RuntimeException {
    m_intake.retract();
    m_intake.setSpeed(0);

    m_drivetrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return getController().atSetpoint();
  }
}
