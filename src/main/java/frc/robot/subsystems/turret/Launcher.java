// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    m_pidController.setOutputRange(LauncherConstants.kMinOutput, LauncherConstants.kMaxOutput);

    SmartDashboard.putNumber("P Gain", LauncherConstants.kP);
    SmartDashboard.putNumber("I Gain", LauncherConstants.kI);
    SmartDashboard.putNumber("D Gain", LauncherConstants.kD);
    SmartDashboard.putNumber("I Zone", LauncherConstants.kIz);
    SmartDashboard.putNumber("Feed Forward", LauncherConstants.kFF);
    SmartDashboard.putNumber("Max Output", LauncherConstants.kMaxOutput);
    SmartDashboard.putNumber("Min Output", LauncherConstants.kMinOutput);
  }

  public void setSpeed(double speed){
    double setPoint = speed * LauncherConstants.kMaxRPM;
    m_pidController.setReference(setPoint, CANSparkMax.ControlType.kVelocity);
    
    SmartDashboard.putNumber("SetPoint", setPoint);
    SmartDashboard.putNumber("ProcessVariable", m_encoder.getVelocity());

    m_flywheel.set(speed);
  }

  public void reset() {
    m_encoder.setPosition(0);
  }

  public double getSpeed() {
    return m_encoder.getVelocity();
  }

  @Override
  public void periodic() {
    // read PID coefficients from SmartDashboard
    double p = SmartDashboard.getNumber("P Gain", 0);
    double i = SmartDashboard.getNumber("I Gain", 0);
    double d = SmartDashboard.getNumber("D Gain", 0);
    double iz = SmartDashboard.getNumber("I Zone", 0);
    double ff = SmartDashboard.getNumber("Feed Forward", 0);
    double max = SmartDashboard.getNumber("Max Output", 0);
    double min = SmartDashboard.getNumber("Min Output", 0);

    // if PID coefficients on SmartDashboard have changed, write new values to controller
    if((p != LauncherConstants.kP)) { m_pidController.setP(p); LauncherConstants.kP = p; }
    if((i != LauncherConstants.kI)) { m_pidController.setI(i); LauncherConstants.kI = i; }
    if((d != LauncherConstants.kD)) { m_pidController.setD(d); LauncherConstants.kD = d; }
    if((iz != LauncherConstants.kIz)) { m_pidController.setIZone(iz); LauncherConstants.kIz = iz; }
    if((ff != LauncherConstants.kFF)) { m_pidController.setFF(ff); LauncherConstants.kFF = ff; }
    if((max != LauncherConstants.kMaxOutput) || (min != LauncherConstants.kMinOutput)) { 
      m_pidController.setOutputRange(min, max); 
      LauncherConstants.kMinOutput = min; LauncherConstants.kMaxOutput = max; 
    }
  }

}
