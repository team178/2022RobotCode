// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.LauncherConstants;

public class Feeder extends SubsystemBase {
  
  private WPI_VictorSPX m_feedwheel;


  public Feeder() {
    m_feedwheel = new WPI_VictorSPX(LauncherConstants.kFeedWheelMotorPort);

    m_feedwheel.setInverted(true);
  }

  public void setSpeed(double speed){

    m_feedwheel.set(speed);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // System.out.println(getSpeed());
  }

}
