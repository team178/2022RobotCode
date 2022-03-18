// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import frc.robot.RobotContainer;
import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;
import java.util.function.DoubleSupplier;

/** Have the robot drive tank style. */
public class ArcadeDrive extends CommandBase {
  private final DriveTrain m_drivetrain;

  private DoubleSupplier m_xaxisSpeedSupplier;
  private DoubleSupplier m_zaxisRotateSuppplier;

  private boolean m_trigger;

  /**
   * Creates a new TankDrive command.
   *
   * @param xaxisSpeedSupplier The control input for the x axis of the drive
   * @param zaxisRotateSuppplier The control input for the z axis of the drive
   * @param drivetrain The drivetrain subsystem to drive
   */
  public ArcadeDrive(DoubleSupplier xaxisSpeedSupplier, DoubleSupplier zaxisRotateSuppplier, DriveTrain drivetrain, boolean trigger) {
    m_drivetrain = drivetrain;
    m_xaxisSpeedSupplier = xaxisSpeedSupplier;
    m_zaxisRotateSuppplier = zaxisRotateSuppplier;

    m_trigger = trigger;

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    RobotContainer.m_lights.enforcers();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    double xaxisSpeed = ((!m_trigger) ? m_xaxisSpeedSupplier.getAsDouble() * OIConstants.kXAxisSpeedMult.getDouble(.8) : 
      m_xaxisSpeedSupplier.getAsDouble() * OIConstants.kSlowXAxisSpeedMult.getDouble(.5));
    
    double zaxisSpeed = ((!m_trigger) ? m_zaxisRotateSuppplier.getAsDouble() * OIConstants.kZAxisSpeedMult.getDouble(.8) : 
      m_zaxisRotateSuppplier.getAsDouble() * OIConstants.kSlowZAxisSpeedMult.getDouble(.5));
    
    m_drivetrain.arcadeDrive(xaxisSpeed, -zaxisSpeed);
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
    RobotContainer.m_lights.sendNormal();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }
}
