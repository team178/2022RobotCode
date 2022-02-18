// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.limelight;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class AimRange extends CommandBase {
  
  private final DriveTrain m_drivetrain;
  private final LimeLight m_limelight;
  
  private double KpAim = -0.1;
  private double KpDistance = -0.1;
  private double min_command = 0.05;
  private double tolerance = .1;
  
  private double tx;
  private double ty;

  private double steering_adjust; 
  private double heading_error;
  private double distance_error;
  private double distance_adjust;

  private double m_left;
  private double m_right; 

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public AimRange(DriveTrain drivetrain, LimeLight limelight) {
    m_drivetrain = drivetrain;
    m_limelight = limelight;
    
    addRequirements(drivetrain, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    tx = m_limelight.getHorizontalDegToTarget();
    ty = m_limelight.getVerticalDegToTarget();

    heading_error = -tx;
    distance_error = -ty;
    
    if (tx > 1.0){
      steering_adjust = KpAim*heading_error - min_command;
    }
    else if (tx < 1.0){
      steering_adjust = KpAim*heading_error + min_command;
    }
    
    distance_adjust = KpDistance * distance_error;

    m_left += steering_adjust + distance_adjust;
    m_right -= steering_adjust + distance_adjust;

    m_drivetrain.tankDrive(m_left, m_right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(heading_error) <= tolerance && Math.abs(distance_error) <= tolerance;
  }
}
