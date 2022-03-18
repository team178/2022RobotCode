// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveDistance extends CommandBase {
  private final DriveTrain m_drivetrain;
  private final double distance;
  
  private double kP = 3;  
  private double kD = 0; //prob not needed 
  private double tolerance;

  private double error;
  private double motorCorrection; 

  private double speed;

  /**
   * Creates a new DriveDistance. This command will drive your your robot for a desired distance at
   * a desired speed.
   *
   * @param drive The speed at which the robot will drive
   * @param distance The number of inches the robot will drive
   * @param drive The drivetrain subsystem on which this command will run
   */
  public DriveDistance(DriveTrain drive, double distance, double tolerance) {
    this.distance = distance;
    this.tolerance = tolerance;
    this.m_drivetrain = drive;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drivetrain.reset();
    speed = (distance > 0 ? .3 : -.3);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    error = m_drivetrain.getRightDistance() - m_drivetrain.getLeftDistance();
    if(Math.abs(error) > tolerance){
      motorCorrection = error * kP;
    }
    else{
      motorCorrection = 0;
    }
    
    m_drivetrain.tankDrive(speed + motorCorrection, motorCorrection - speed);


  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drivetrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    // Compare distance travelled from start to desired distance
    return Math.abs(m_drivetrain.getDistance()) >= Math.abs(distance);
  }
}