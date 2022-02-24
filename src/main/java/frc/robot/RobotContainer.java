// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.launcher.ShootBall;
import frc.robot.commands.climber.LowerMast;
import frc.robot.commands.climber.RaiseMast;
import frc.robot.commands.climber.ToggleHook;
import frc.robot.commands.climber.TomahawkDown;
import frc.robot.commands.climber.TomahawkUp;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.intake.PickUp;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Intake;
import frc.robot.subsystems.turret.Launcher;
import libs.OI.ConsoleController;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_drivetrain = new DriveTrain();
  private final Intake m_intake = new Intake();
  private final Launcher m_launcher =  new Launcher();
  private final Climber m_climber = new Climber();
  private final LimeLight m_limelight = new LimeLight();

  //Creates joystick object for the Main (0) and Aux (1) controllers
  private final ConsoleController m_controller_main = new ConsoleController(0);
  private final ConsoleController m_controller_aux = new ConsoleController(1);

  // Create SmartDashboard chooser for autonomous routines
  private final SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    // Set drive controls
    m_drivetrain.setDefaultCommand(
        new TankDrive(m_controller_main::getLeftStickY, m_controller_main::getRightStickY, m_drivetrain));

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    /*m_joystick.whenpressed() {
      
    }*/

    //What to include here: LowerMast, RaiseMast, ToggleHook,TomahawkDown, TomahawkUp, DriveStraight, TankDrive, TurnDegrees, PickUp, ShootBall

    //Console Controller Mapping 
    m_controller_aux.y
        .whileHeld(new ShootBall(m_launcher));

    m_controller_aux.a
      .whileHeld(new PickUp(m_intake));

    m_controller_aux.topDPAD
      .whileHeld(new RaiseMast(m_climber));

    m_controller_aux.bottomDPAD
      .whileHeld(new LowerMast(m_climber));
    
    m_controller_aux.leftDPAD
      .whileHeld(new TomahawkDown(m_climber));

    m_controller_aux.rightDPAD
      .whileHeld(new TomahawkUp(m_climber));

    m_controller_aux.x  
      .whileHeld(new ToggleHook(m_climber));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_chooser.getSelected();
  }
}
