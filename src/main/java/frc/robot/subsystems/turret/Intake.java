// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase implements AutoCloseable {
  private final WPI_VictorSPX intakeMotor = new WPI_VictorSPX(IntakeConstants.MOTOR_PORT);
  
  private final DoubleSolenoid intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.PISTON_FWD, IntakeConstants.PISTON_REV);  
 
  public Intake() {
    intakeMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 10);
    intakeMotor.setSensorPhase(true);
  }
  
  /**
   * Deploys the pistons
  */ 
  public void deploy() {
    intakePiston.set(DoubleSolenoid.Value.kForward);
  } 
  
  /**
    * Retracts/undeploys the pistons
  */
  public void retract() {
    intakePiston.set(DoubleSolenoid.Value.kReverse);
    //intakeMotor.set(0);
  }
  
  /**
    * Activates the motor and pistons
  */
  public boolean activate(double speed) {
    if (intakePiston.get() == DoubleSolenoid.Value.kForward) {
      intakeMotor.set(speed);
      return true;
    }
    intakeMotor.set(0);
    return false;
  }
  
  @Override
  public void close() throws RuntimeException {
    intakePiston.close();
    intakeMotor.close();
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
