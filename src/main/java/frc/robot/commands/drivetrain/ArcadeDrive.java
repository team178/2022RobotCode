// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

/** Have the robot drive tank style. */
public class ArcadeDrive extends CommandBase {
  private final DriveTrain m_drivetrain;

  private DoubleSupplier m_xaxisSpeedSupplier;
  private DoubleSupplier m_zaxisRotateSuppplier;

  /**
   * Creates a new TankDrive command.
   *
   * @param xaxisSpeedSupplier The control input for the x axis of the drive
   * @param zaxisRotateSuppplier The control input for the z axis of the drive
   * @param drivetrain The drivetrain subsystem to drive
   */
  public ArcadeDrive(DoubleSupplier xaxisSpeedSupplier, DoubleSupplier zaxisRotateSuppplier, DriveTrain drivetrain) {
    m_drivetrain = drivetrain;
    m_xaxisSpeedSupplier = xaxisSpeedSupplier;
    m_zaxisRotateSuppplier = zaxisRotateSuppplier;
    addRequirements(drivetrain);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double xaxisSpeed = m_xaxisSpeedSupplier.getAsDouble();
    double zaxisSpeed = m_zaxisRotateSuppplier.getAsDouble();

    if(Math.abs(xaxisSpeed) < 0.2) {
        xaxisSpeed = 0;
      }
  
    if(Math.abs(zaxisSpeed) < 0.2) {
        zaxisSpeed = 0;
    }

    m_drivetrain.arcadeDrive(xaxisSpeed,    zaxisSpeed);
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }
}
