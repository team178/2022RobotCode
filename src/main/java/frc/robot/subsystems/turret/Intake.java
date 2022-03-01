// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.turret;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
  private final WPI_VictorSPX intakeMotor;
  
  private final DoubleSolenoid intakePiston;
  private final DoubleSolenoid bouncer;  

  private final DigitalInput limitSwitch;
 
  public Intake() {
    intakeMotor = new WPI_VictorSPX(IntakeConstants.kMotorPort);

    intakePiston = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kForwardPort, IntakeConstants.kRevPort);
    bouncer = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, IntakeConstants.kBouncerPortFOR, IntakeConstants.kBouncerPortREV);

    limitSwitch = new DigitalInput(IntakeConstants.kLimitSwitchPort);
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
  }

  public void enableBouncer() {
    bouncer.set(DoubleSolenoid.Value.kForward);
  }

  public void disableBouncer() {
    bouncer.set(DoubleSolenoid.Value.kReverse);
  }

  /**
   * Set the speed of the intake motors
   * @param speed A percent speed value, between -1.0 and 1.0
   */
  public void setSpeed(double speed) {
    intakeMotor.set(speed);
  }

  /**
   * Get if the Intake is deployed
   * @return is the Intake deployed?
   */
  public boolean isDeployed() {
    return intakePiston.get() == DoubleSolenoid.Value.kForward;
  }

  public boolean getSwitchState() {
    return limitSwitch.get();
  }
  
  // Not sure why we need this
  // @Override
  // public void close() throws RuntimeException {
  //   intakePiston.close();
  //   intakeMotor.close();
  // }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
