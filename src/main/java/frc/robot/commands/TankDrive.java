// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.Constants.OIConstants;
import frc.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

/** Have the robot drive tank style. */
public class TankDrive extends CommandBase {
  private final DriveTrain m_drivetrain;
  
  private double m_left;
  private double m_right;

  /**
   * Creates a new TankDrive command.
   *
   * @param left The control input for the left side of the drive
   * @param right The control input for the right sight of the drive
   * @param drivetrain The drivetrain subsystem to drive
   */
  public TankDrive(DoubleSupplier left, DoubleSupplier right, DriveTrain drivetrain) {
    m_drivetrain = drivetrain;
    m_left = left.getAsDouble() * OIConstants.kThrustSpeedMult;
    m_right = right.getAsDouble() * OIConstants.kThrustSpeedMult;
    addRequirements(drivetrain);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {

    if(Math.abs(m_left) < 0.2) {
        m_left = 0;
      }
  
      if(Math.abs(m_right) < 0.2) {
        m_right = 0;
      }

    m_drivetrain.drive(m_left, m_right);
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.drive(0, 0);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false; // Runs until interrupted
  }
}