// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.launcher;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.commands.arduinolights.Shoot;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.turret.Feeder;

/** Run the Launcher */
public class AutoShootBall extends ParallelCommandGroup {
  
  private final Feeder m_feeder;
  private double startTime;

  /**
   * Creates a new ShootBall command
   *
   * @param subsystem The subsystem used by this command.
   */
  public AutoShootBall(Feeder feeder, ArduinoLights lights) {
    m_feeder = feeder;;
    alongWith(new Shoot(lights));
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(feeder);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_feeder.setSpeed(1);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_feeder.setSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Timer.getFPGATimestamp() - startTime >= 3;
  }
}
