// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
//import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.ClimberConstants;



public class Climber extends SubsystemBase {

  private final WPI_VictorSPX climberMotor = new WPI_VictorSPX(ClimberConstants.kClimberMotorPort);
  private final WPI_VictorSPX ExtendingArmWinch = new WPI_VictorSPX(ClimberConstants.kExtendingArmWinchPort);

  private final Solenoid HookSolenoid = new Solenoid(PneumaticsModuleType.CTREPCM, ClimberConstants.kHookSolenoidPort);

  private final DigitalInput HighlimitSwitch = new DigitalInput(ClimberConstants.kHighLimitSwitchPort);
  private final DigitalInput LowlimitSwitch = new DigitalInput(ClimberConstants.kLowLimitSwitchPort);

//Ask if You need to use rate for climber motor(with a double supplier)
//ask if we need to include conditionals for the limit switches to halt the motors when they are at limit levels
public Climber() {

    climberMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    climberMotor.setSensorPhase(true);

    //Is this right???
    ExtendingArmWinch.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    ExtendingArmWinch.setSensorPhase(true);

  }

  public void setClimberMotorSpeed(double speed){

    climberMotor.set(speed);

  }

  public void setExtendingArmWinchSpeed(double speed){

    ExtendingArmWinch.set(speed);

  }

  public void toggleHookSolenoid(){

    HookSolenoid.toggle();

  }

  public void reset() {
    climberMotor.setSelectedSensorPosition(0);
    ExtendingArmWinch.setSelectedSensorPosition(0);
  }
//Is this right???
  public double getClimberMotorEncoder() {
    return climberMotor.getSelectedSensorPosition(0);
  }




  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
