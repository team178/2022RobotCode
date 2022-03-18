// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.limelight;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** 

    https://www.chiefdelphi.com/t/help-with-limelight-code/352890/21 - Looked through this forum 
    
    Also it's important to note that we do not want oscallation, that means our kP is too low
    
    "Beware, if you set Kp or min_command too high, your robot can become unstable and can oscillate back and forth as it overshoots the target"
    
*/

public class ModifiedAim extends CommandBase {

  private final DriveTrain m_drivetrain;
  private final LimeLight m_limelight;

  private double m_degrees;
  
  private double Kp;
  private double minTurnSpeed;
  private double tolerance;
  
  private double turnAdjust; 
  private double headingError;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ModifiedAim(double degrees, DriveTrain drivetrain, LimeLight limelight) {
    m_drivetrain = drivetrain;
    m_limelight = limelight;
    m_degrees = degrees;
    
    addRequirements(drivetrain, limelight);
  }

  public ModifiedAim(DriveTrain drivetrain, LimeLight limelight) {
    m_drivetrain = drivetrain;
    m_limelight = limelight;
    m_degrees = 0;
    
    addRequirements(drivetrain, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Kp = .008;
    minTurnSpeed = .365;
    tolerance = .8;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double horizontalDegTarget = m_limelight.getHorizontalDegToTarget(); 

    headingError = ((horizontalDegTarget != 0) ? m_degrees - horizontalDegTarget : headingError); // Ensures heading error nevers = 0

    turnAdjust = Kp * headingError; // Multiplies our error by our speed constant, that way we have a useable speed
    turnAdjust = ((Math.abs(turnAdjust) < minTurnSpeed) ? minTurnSpeed + Math.abs(turnAdjust) : turnAdjust); // Ensures we don't go under min speed needed to turn
    turnAdjust = ((headingError > 0) ? turnAdjust : -turnAdjust); // Ensures correct directional change
    
    m_drivetrain.arcadeDrive(0, turnAdjust);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(headingError) <= tolerance;
  }
}
