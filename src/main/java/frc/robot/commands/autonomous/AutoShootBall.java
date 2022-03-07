// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Feeder;
import frc.robot.subsystems.turret.Launcher;
import frc.robot.Constants.LauncherConstants;;

/** Run the Launcher */
public class AutoShootBall extends ParallelCommandGroup {
  private double neededVelocity; 
  private double startTime;
  private double motorSpeed;
  
  private final Feeder m_feeder;
  private final Launcher m_launcher;
  private final LimeLight m_limelight;

  /**
   * Creates a new ShootBall command
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutoShootBall(Launcher launcher, Feeder feeder, LimeLight limeLight) {
    m_launcher = launcher;
    m_feeder = feeder;
    m_limelight = limeLight; 

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(launcher, feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp(); 
    
    neededVelocity = m_limelight.calculateLauncherVelocity();
    motorSpeed = neededVelocity / LauncherConstants.kFlyWheelRadius; 
  }

  // Called every time the scheduler runs while the command is scheduled.
  // Note, would be better if launcher speed can be inputed with neededVelocity
  @Override
  public void execute() {
    m_launcher.setSpeed(motorSpeed);
    
    if(m_launcher.getSpeed() > neededVelocity){
      m_feeder.setSpeed(1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.setSpeed(0);
    m_launcher.setSpeed(0);
  }

  // Returns true when the command should end.
  // Scuffed is finished, need to rethink
  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - startTime >= 1);
  }
}
