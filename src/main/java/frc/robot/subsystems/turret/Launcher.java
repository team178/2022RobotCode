// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import frc.robot.Constants.LauncherConstants;

public class Launcher extends SubsystemBase {
  
  private CANSparkMax m_flywheel;
  private CANSparkMax m_flywheel_follower;

  private SparkMaxPIDController m_pidController;
  private RelativeEncoder m_encoder;

  public Launcher() {
    m_flywheel = new CANSparkMax(LauncherConstants.kFlyWheelMotorPort1, MotorType.kBrushed);
    m_flywheel_follower = new CANSparkMax(LauncherConstants.kFlyWheelMotorPort2, MotorType.kBrushed);

    m_flywheel.restoreFactoryDefaults();

    m_flywheel_follower.follow(m_flywheel);

    m_encoder = m_flywheel.getEncoder(SparkMaxRelativeEncoder.Type.kQuadrature, 4096);

    m_pidController = m_flywheel.getPIDController();

    m_pidController.setP(LauncherConstants.kP);
    m_pidController.setI(LauncherConstants.kI);
    m_pidController.setD(LauncherConstants.kD);
    m_pidController.setIZone(LauncherConstants.kIz);
    m_pidController.setFF(LauncherConstants.kFF);
    m_pidController.setOutputRange(0, 1);
  }

  public void setSpeed(double speed){
    double setPoint = speed * LauncherConstants.kMaxRPM;
    m_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
    System.out.println(setPoint);
  }

  public void reset() {
    m_encoder.setPosition(0);
  }

  public double getSpeed() {
    return m_encoder.getVelocity();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // System.out.println(getSpeed());
  }

}
