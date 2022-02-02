// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

/** An example command that uses an example subsystem. */
public class XboxTankDrive extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrain m_driveTrain;

  private final XboxController con = new XboxController(Constants.MAINCON_PORT);

  private double lThrust;
  private double rThrust;

  private XboxController.Axis lThrustButton = XboxController.Axis.kLeftY;
  private XboxController.Axis rThrustButton = XboxController.Axis.kRightY;

  public XboxTankDrive(DriveTrain subsystem) {
    m_driveTrain = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    lThrust = con.getRawAxis(lThrustButton.value) * Constants.thrustSpeedMult;
    rThrust = con.getRawAxis(rThrustButton.value) * Constants.thrustSpeedMult;

    if(Math.abs(lThrust) < 0.2) {
      lThrust = 0;
    }

    if(Math.abs(rThrust) < 0.2) {
      rThrust = 0;
    }

    System.out.println("l" + lThrust);
    System.out.println("r" + rThrust);

    m_driveTrain.drive(lThrust, rThrust);
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
