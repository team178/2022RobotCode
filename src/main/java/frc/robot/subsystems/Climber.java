// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.ClimberConstants;

public class Climber extends SubsystemBase {

  private final WPI_VictorSPX climberMotor = new WPI_VictorSPX(ClimberConstants.kClimberMotorPort);
  private final WPI_VictorSPX ExtendingArmWinch = new WPI_VictorSPX(ClimberConstants.kExtendingArmWinchPort);

  private final Solenoid HookSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ClimberConstants.kHookSolenoidPort);

  private final DigitalInput HighlimitSwitch = new DigitalInput(ClimberConstants.kHighLimitSwitchPort);
  private final DigitalInput LowlimitSwitch = new DigitalInput(ClimberConstants.kLowLimitSwitchPort);

  // Do we need to use rate for climber motor (with a double supplier)
  public Climber() {
    climberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    climberMotor.setSensorPhase(true);

    ExtendingArmWinch.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    ExtendingArmWinch.setSensorPhase(true);
  }

  /**
   * Sets climberMotorSpeed to a random int
  */ 
  public void setClimberMotorSpeed(double speed) {
    climberMotor.set(speed);
  }

  /**
    * Sets ExtendingArmWinchSpeed to a random int
  */ 
  public void setExtendingArmWinchSpeed(double speed) {
    ExtendingArmWinch.set(speed);

  }

  /**
    * Toggles the Hook release
  */ 
  public void toggleHookSolenoid() {
    HookSolenoid.toggle();
  }
  
  /**
    * Gets the high limit switch
  */

  public DigitalInput getHighLimitSwitch() {
    return HighlimitSwitch;
  }
  
  /**
    * Gets the low limit switch
  */

  public DigitalInput getLowLimitSwitch() {
    return LowlimitSwitch;
  }

  public void reset() {
    climberMotor.setSelectedSensorPosition(0);
    ExtendingArmWinch.setSelectedSensorPosition(0);
  }
  
  public double getClimberMotorEncoder() {
    return climberMotor.getSelectedSensorPosition(0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
