// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;

/**
 * Drive the given distance straight (negative values go backwards). Uses a local PID controller to
 * run a simple PID loop that is only enabled while this command is running. The input is the
 * averaged values of the left and right encoders.
 */
public class DriveStraight extends PIDCommand {
  private final DriveTrain m_drivetrain;
  /**
   * Create a new DriveStraight command.
   *
   * @param distance The distance to drive
   */
  public DriveStraight(double distance, DriveTrain drivetrain) {
    super(
        new PIDController(1, 0, 0), drivetrain::getDistance, distance, d -> drivetrain.tankDrive(d, d));

    m_drivetrain = drivetrain;
    m_drivetrain.reset();
    addRequirements(m_drivetrain);

    getController().setTolerance(1);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    Timer.getFPGATimestamp();
    m_drivetrain.reset();
    super.initialize();
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return (getController().atSetpoint());
  }
}
