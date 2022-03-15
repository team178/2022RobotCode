// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.launcher;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.turret.Feeder;
import frc.robot.subsystems.turret.Launcher;

/** Run the Launcher at it's set speed */
public class FireBall extends CommandBase {
  private double startTime;

  private final Feeder m_feeder;  
  private final Launcher m_launcher;

  private double speed; 

  /**
   * Creates a new RunLauncher command
   *
   * @param launcher The Launcher subsystem.
   */
  public FireBall(Launcher launcher, Feeder feeder, double inputSpeed) {
    m_launcher = launcher;
    m_feeder = feeder;

    this.speed = inputSpeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(launcher);
  }

  public FireBall(Launcher launcher, Feeder feeder) {
    m_launcher = launcher;
    m_feeder = feeder;

    this.speed = LauncherConstants.kLauncherSpeed.getDouble(0);

    addRequirements(launcher);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    startTime = Timer.getFPGATimestamp(); 

    m_feeder.setSpeed(-.25);
    m_launcher.setSpeed(.25);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(Timer.getFPGATimestamp() > .15){
        m_launcher.setSpeed(speed);
    }
    if(Timer.getFPGATimestamp() > 1){
        m_feeder.setSpeed(1);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_launcher.setSpeed(0.06);
    m_feeder.setSpeed(0.06);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return (Timer.getFPGATimestamp() - startTime >= 1.25);
  }
}
