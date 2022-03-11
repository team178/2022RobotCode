// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Based on the Getting in Range and Aim Case Studies 
 */
public class aimRange extends CommandBase {

  private final DriveTrain m_drivetrain;
  private final LimeLight m_limelight;

  // Fields for range
  private boolean crosshairCalibrated = false;
  
  private double KpAngle;
  private double KpMeter;

  private double rangeTolerance;
  
  private double desiredDistance;

  private double driveAdjust; 
  private double distanceError;

  private double minDriveSpeed;

  // Fields for aim
  private double KpAim;

  private double aimTolerance;

  private double turnAdjust;
  private double headingError;
  
  private double minTurnSpeed;

  /**
   * Use this constructor cross-hair has been calibrated for the distance we want, 
   * thus we can use ty to command our distance error
   * 
   * @param drivetrain
   * @param limelight
   */
  public aimRange(DriveTrain drivetrain, LimeLight limelight) {
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
  public aimRange(DriveTrain drivetrain, LimeLight limelight, double desiredDistance) {
    m_drivetrain = drivetrain;
    m_limelight = limelight;

    this.desiredDistance = desiredDistance;
    crosshairCalibrated = false;
    
    addRequirements(drivetrain, limelight);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    // Initialize Range Fields
    KpAngle = 0.005;
    KpMeter = 0.030;
    
    minDriveSpeed = 0.365;

    // Initialize Aim Fields
    KpAim = 0.008;
    minTurnSpeed = 0.365;
    aimTolerance = 0.4;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double horizontalDegTarget = m_limelight.getHorizontalDegToTarget(); 

    headingError = ((horizontalDegTarget != 0) ? horizontalDegTarget : headingError); // Ensures heading error nevers = 0

    if(!crosshairCalibrated){
        double currentDistance = m_limelight.estimateDistance(2.4384); // Input actually height from target later
        rangeTolerance = 0.1;
        
        distanceError = desiredDistance - currentDistance;
        driveAdjust = KpMeter * Math.abs(distanceError);
    }
    else{
        double verticalDegTarget = m_limelight.getVerticalDegToTarget();
        rangeTolerance = 0.1;

        distanceError = ((verticalDegTarget != 0) ? -verticalDegTarget : distanceError);
        driveAdjust = KpAngle * distanceError;
    }

    driveAdjust = ((Math.abs(driveAdjust) < minDriveSpeed) ? minDriveSpeed + Math.abs(driveAdjust) : driveAdjust); // Added the latter condition to ensure that if the drive adjust ends earlier than the turn adjust, the robot stops moving along the x axis
    driveAdjust = ((distanceError > 0) ? -driveAdjust: driveAdjust);

    turnAdjust = KpAim * headingError; // Multiplies our error by our speed constant, that way we have a useable speed
    turnAdjust = ((Math.abs(turnAdjust) < minTurnSpeed && Math.abs(headingError) > aimTolerance) ? minTurnSpeed + Math.abs(turnAdjust) : turnAdjust); // Ensures we don't go under min speed needed to turn
    turnAdjust = ((headingError > 0) ? turnAdjust : -turnAdjust); // Ensures correct directional change

    m_drivetrain.arcadeDrive(driveAdjust, turnAdjust);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (((Math.abs(headingError) <= aimTolerance && Math.abs(distanceError) <= rangeTolerance) ? true : false));
  }

}
