// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private final WPI_VictorSPX intakeMotor = new WPI_VictorSPX(IntakeConstants.MOTOR_PORT);
  
  public Intake() {
    intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    intakeMotor.setSensorPhase(true);
  }
  
  /**
   * Sets intakeMotorSpeed to a random int
  */ 
  public void setIntakeMotorSpeed(double speed) {
    intakeMotor.set(speed);
  }
  
  /**
    * Resets position of motor
  */
  
  public void intakeReset() {
    intakeMotor.setSelectedSensorPosition(0);
  }
  
  /**
    * Encoder for motor
  */
  
  public double getIntakeMotorEncoder() {
    return intakeMotor.getSelectedSensorPosition(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
