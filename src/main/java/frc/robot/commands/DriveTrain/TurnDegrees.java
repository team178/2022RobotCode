// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.DriveTrain;

public class TurnDegrees extends CommandBase {
  private final DriveTrain m_drivetrain;
  private final double m_degrees;
  private final double m_speed;

  /**
   * Creates a new TurnDegrees. This command will turn your robot for a desired rotation (in
   * degrees) and rotational speed.
   *
   * @param speed The speed which the robot will drive. Negative is in reverse.
   * @param degrees Degrees to turn. Leverages encoders to compare distance.
   * @param drive The drive subsystem on which this command will run
   */
  public TurnDegrees(double speed, double degrees, DriveTrain drivetrain) {
    m_degrees = degrees;
    m_speed = speed;
    m_drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Set motors to stop, read encoder values for starting point
    m_drivetrain.arcadeDrive(0, 0);
    m_drivetrain.reset();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drivetrain.arcadeDrive(0, m_speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Need to convert distance travelled to degrees. 
    double inchPerDegree = Math.PI * DriveConstants.kWheelDiameterInches / 360;
    
    // Compare distance travelled from start to distance based on degree turn
    return getAverageTurningDistance() >= (inchPerDegree * m_degrees);
  }

  private double getAverageTurningDistance() {
    double leftDistance = Math.abs(m_drivetrain.getLeftDistance());
    double rightDistance = Math.abs(m_drivetrain.getRightDistance());
    return (leftDistance + rightDistance) / 2.0;
  }
}