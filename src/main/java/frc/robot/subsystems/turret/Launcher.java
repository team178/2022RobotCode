// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.LauncherConstants;

public class Launcher extends SubsystemBase {
  
  private final WPI_TalonSRX MotorFlywheel = new WPI_TalonSRX(LauncherConstants.kFlyWheelMotorPort);
  private final WPI_VictorSPX PrelaunchWheel = new WPI_VictorSPX(LauncherConstants.kPreLaunchWheelMotorPort);

  //Encoder method
  public DoubleSupplier flywheelRate;

  /**
    * Sets up the ball to be launched
  */ 
  public Launcher() {
    // Sets the distance per pulse for the encoders
    MotorFlywheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    
    MotorFlywheel.setSensorPhase(true);
    
    flywheelRate = () -> MotorFlywheel.getSelectedSensorVelocity(0) * DriveConstants.kEncoderDistancePerPulse * 10; //r

  }

  /**
    * Sets FlyWheelSpeed to a random int
  */ 
  public void setFlyWheelSpeed(double speed){

    MotorFlywheel.set(speed);

  }

  /**
    * Sets PrelaunchWheelSpeed to a random int
  */ 
  public void setPrelaunchWheelSpeed(double speed){

    PrelaunchWheel.set(speed);

  }

  /**
    * Resets FlyWheelSpeed to 0
  */ 
  public void reset() {
    MotorFlywheel.setSelectedSensorPosition(0);
  }

  /**
    * Gets encoder
  */ 
  public double getFlywheelEncoder() {
    return MotorFlywheel.getSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

}
