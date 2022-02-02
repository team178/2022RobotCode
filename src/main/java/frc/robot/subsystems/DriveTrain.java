// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.Constants;
import frc.robot.commands.XboxTankDrive;

public class DriveTrain extends SubsystemBase {
 
  private static WPI_TalonSRX left;
  private static WPI_VictorSPX left_s;
  private static WPI_TalonSRX right;
  private static WPI_VictorSPX right_s;
  
  public DriveTrain() {

    // Init Drive Motors
    left = new WPI_TalonSRX(Constants.DMTopLeftPort);
	  left_s = new WPI_VictorSPX(Constants.DMBottomLeftPort);
	  right = new WPI_TalonSRX(Constants.DMTopRightPort);
	  right_s = new WPI_VictorSPX(Constants.DMBottomRightPort);

    //Set victors to slaves
    left_s.follow(left);
    right_s.follow(right);

    //Config left motor & sensor directions
    left.setInverted(true);
    left.setSensorPhase(true);
    left_s.setInverted(InvertType.FollowMaster);
    
    //Config right motor & sensor directions
    right.setInverted(false);
    right.setSensorPhase(false);
    right_s.setInverted(InvertType.FollowMaster);
    
  }

  public void drive(double leftPower, double rightPower) {
    left.set(ControlMode.PercentOutput, leftPower);
    right.set(ControlMode.PercentOutput, rightPower);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    setDefaultCommand(new XboxTankDrive(this));
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    setDefaultCommand(new XboxTankDrive(this));
  }
}