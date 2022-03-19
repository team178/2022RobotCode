// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.limelight;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Based on the Getting in Range Case Study 
 */
public class ModifiedRange extends CommandBase {

  private final DriveTrain m_drivetrain;
  private final LimeLight m_limelight;

  private boolean crosshairCalibrated = false;
  
  private double KpAngle;
  private double KpMeter;

  private double angleTolerance;
  private double meterTolerance;
  
  private double desiredDistance;

  private double driveAdjust; 
  private double distanceError;

  private double minDriveSpeed;

  /**
   * Use this constructor cross-hair has been calibrated for the distance we want, 
   * thus we can use ty to command our distance error
   * 
   * @param drivetrain
   * @param limelight
   */
  public ModifiedRange(DriveTrain drivetrain, LimeLight limelight) {
    m_drivetrain = drivetrain;
    m_limelight = limelight;

    this.desiredDistance = 0;
    crosshairCalibrated = true;
    
    addRequirements(drivetrain, limelight);
  }

  /**
   * Use this constructor if we have not calibrated for the distance we want, or for whatever else reason their might be.
   * Will use our current distance to adjust our error.
   * 
   * @param drivetrain
   * @param limelight
   * @param desiredDistance
   */
  public ModifiedRange(DriveTrain drivetrain, LimeLight limelight, double desiredDistance) {
    m_drivetrain = drivetrain;
    m_limelight = limelight;

    this.desiredDistance = desiredDistance;
    crosshairCalibrated = false;
    
    addRequirements(drivetrain, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    KpAngle = 0.005;
    KpMeter = .065; // try .125 next time

    angleTolerance = 0.1;
    meterTolerance = 0.1;
    
    minDriveSpeed = .365;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if(!crosshairCalibrated){
        double currentDistance = m_limelight.estimateDistance(); // Input actually height from target later
        
        distanceError = desiredDistance - currentDistance;
        driveAdjust = KpMeter * Math.abs(distanceError);
        
        //driveAdjust = distanceError/desiredDistance
    }
    else{
        double verticalDegTarget = m_limelight.getVerticalDegToTarget();

        distanceError = ((verticalDegTarget != 0) ? verticalDegTarget : distanceError);
        driveAdjust = KpAngle * distanceError;
    }

    driveAdjust = ((Math.abs(driveAdjust) < minDriveSpeed) ? minDriveSpeed + driveAdjust : driveAdjust);
    //driveAdjust = ((Math.abs(driveAdjust) < minDriveSpeed) ? minDriveSpeed : driveAdjust);
    driveAdjust = ((distanceError > 0) ? -driveAdjust: driveAdjust);

    m_drivetrain.arcadeDrive(driveAdjust, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(!crosshairCalibrated){
      return Math.abs(distanceError) <= meterTolerance; 
    }
    else{
      return Math.abs(distanceError) <= angleTolerance;
    }
  }
}
