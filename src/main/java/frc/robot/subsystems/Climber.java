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

  private final WPI_VictorSPX climberMotor;
  private final WPI_VictorSPX extendingArmWinch;

  private final Solenoid hookSolenoid;

  private final DigitalInput highlimitSwitch;
  private final DigitalInput LowlimitSwitch;

  // Do we need to use rate for climber motor (with a double supplier)
  public Climber() {
    climberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    climberMotor.setSensorPhase(true);

    extendingArmWinch.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    extendingArmWinch.setSensorPhase(true);
    climberMotor = new WPI_VictorSPX(ClimberConstants.kClimberMotorPort);
    extendingArmWinch = new WPI_VictorSPX(ClimberConstants.kExtendingArmWinchPort);

    hookSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ClimberConstants.kHookSolenoidPort);

    highlimitSwitch = new DigitalInput(ClimberConstants.kHighLimitSwitchPort);
    LowlimitSwitch = new DigitalInput(ClimberConstants.kLowLimitSwitchPort);
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
    extendingArmWinch.set(speed);

  }

  /**
    * Toggles the Hook release
  */ 
  public void toggleHookSolenoid() {
    hookSolenoid.toggle();
  }
  
  /**
    * Gets the high limit switch
  */

  public DigitalInput getHighLimitSwitch() {
    return highlimitSwitch;
  }
  
  /**
    * Gets the low limit switch
  */

  public DigitalInput getLowLimitSwitch() {
    return LowlimitSwitch;
  }
  
  /**
    * Toggles hook
  */

  public Solenoid getHookSolenoid() {
    return hookSolenoid;
  }

  /**
    * Resets position of motor/winch
  */
  
  public void climberReset() {
    climberMotor.setSelectedSensorPosition(0);
    extendingArmWinch.setSelectedSensorPosition(0);
  }
  
  /**
    * Encoder for motor
  */
  
  public double getClimberMotorEncoder() {
    return climberMotor.getSelectedSensorPosition(0);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
