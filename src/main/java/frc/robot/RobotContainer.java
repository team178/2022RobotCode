// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;
import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.launcher.ShootBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.commands.limelight.modifiedAim;
import frc.robot.commands.limelight.modifiedRange;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.climber.LowerMast;
import frc.robot.commands.climber.RaiseMast;
import frc.robot.commands.climber.ToggleHook;
import frc.robot.commands.climber.TomahawkDown;
import frc.robot.commands.climber.TomahawkUp;
import frc.robot.commands.drivetrain.ArcadeDrive;
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
  private final DriveTrain m_drivetrain;
  private final Intake m_intake;
  private final Launcher m_launcher;
  private final Climber m_climber;
  private final LimeLight m_limelight;

  //Creates joystick object for the Main (0) and Aux (1) controllers
  private final ConsoleController m_controller_main = new ConsoleController(0);
  private final ConsoleController m_controller_aux = new ConsoleController(1);

  // Create SmartDashboard chooser for autonomous routines and drive
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();
  private final SendableChooser<Command> m_driveChooser = new SendableChooser<>();

  //Creates SmartDashboard chooser for drive axises (for example - Right Joystick Y controls Left side of the robot in TankDrive)
  private final SendableChooser<DoubleSupplier> m_driveAxis1 = new SendableChooser<>();
  private final SendableChooser<DoubleSupplier> m_driveAxis2 = new SendableChooser<>(); 

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_drivetrain = new DriveTrain();
    m_intake = new Intake();
    m_launcher = new Launcher();
    m_climber = new Climber();
    m_limelight = new LimeLight();

    // Configure the button bindings
    configureButtonBindings();
    configureShuffleBoard();
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

  private void configureShuffleBoard() {
    //Drive Axis Control Options (example - LeftStickY)
    m_driveAxis1.setDefaultOption("Left Controller Stick Y", m_controller_main::getLeftStickY); 
      
      //For the Xbox Controller
      m_driveAxis1.addOption("Left Controller Stick X", m_controller_main::getLeftStickX);
      m_driveAxis1.addOption("Right Controller Stick X", m_controller_main::getRightStickX);
      m_driveAxis1.addOption("Right Controller Stick Y", m_controller_main::getRightStickY);
      m_driveAxis1.addOption("Left Controller Trigger", m_controller_main::getLeftTrigger);
      m_driveAxis1.addOption("Right Controller Trigger", m_controller_main::getRightTrigger);



    m_driveAxis2.setDefaultOption("Right Controller Stick Y", m_controller_main::getRightStickY);

      //For the Xbox Controller
      m_driveAxis2.addOption("Left Controller Stick X", m_controller_main::getLeftStickX);
      m_driveAxis2.addOption("Left Controller Stick Y", m_controller_main::getLeftStickY);
      m_driveAxis2.addOption("Right Controller Stick X", m_controller_main::getRightStickX);
      m_driveAxis2.addOption("Left Controller Trigger", m_controller_main::getLeftTrigger);
      m_driveAxis2.addOption("Right Controller Trigger", m_controller_main::getRightTrigger);


    //Drive Routine Options (How our robot is going to drive)
    m_driveChooser.setDefaultOption("Tank Drive", new TankDrive(m_driveAxis1, m_driveAxis2, m_drivetrain));
    m_driveChooser.addOption("Arcade Drive", new ArcadeDrive(m_driveAxis1, m_driveAxis2, m_drivetrain));

    //Autonomous Chooser Options (How our robot is going to tackle auto)
    m_autoChooser.setDefaultOption("Modified Range", new modifiedRange(m_drivetrain, m_limelight));
    m_autoChooser.addOption("Modified Aim", new modifiedAim(m_drivetrain, m_limelight));
    m_autoChooser.addOption("Aim and Range", new AimRange(m_drivetrain, m_limelight));

    //Creates new Shuffleboard tab called Drivebase
    ShuffleboardTab driveBaseTab = Shuffleboard.getTab("Drivebase");

    //Adds a chooser to the Drivebase tab to select autonomous routine (before anything is ran)
    driveBaseTab
      .add("Autonomous Routine", m_autoChooser)
        .withSize(2, 1)
          .withPosition(0, 3);

    //Adds a chooser to the Drivebase tab to select drive routine (before anything is ran)
    driveBaseTab
      .add("Drive Routine", m_driveChooser)
        .withSize(2, 1)
          .withPosition(0, 0);
    
    //Adds a chooser to the Drivebase tab to select drive axis 1 (before anything is ran)
    driveBaseTab
      .add("Drive Axis 1", m_driveAxis1)
        .withSize(2, 1)
          .withPosition(2, 0);

    //Adds a chooser to the Drivebase tab to select drive axis 2 (before anything is ran)
    driveBaseTab
      .add("Drive Axis 2", m_driveAxis2)
        .withSize(2, 1)
          .withPosition(2, 3);
    
    //Adds a slider to the Drivebase tab so driver can adjust sensitivity for input 1 of the given drive command 
    OIConstants.kDriveSpeedMult1 = driveBaseTab
    .add("Max Speed for Joystick 1", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 2)) // specify widget properties here
          .withPosition(0, 1)
            .getEntry();
    
    //Adds a slider to the Drivebase tab so driver can adjust sensitivity for input 2 of the given drive command 
    OIConstants.kDriveSpeedMult2 = driveBaseTab
    .add("Max Speed for Joystick 2", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", 0, "max", 2)) // specify widget properties here
          .withPosition(0, 2)
            .getEntry();
    

    //Adds a Layout (basically a empty list) to the Drivebase tab for Limelight Commands 
    ShuffleboardLayout limelightCommands = driveBaseTab
      .getLayout("Limelight Commands", BuiltInLayouts.kList)
        .withSize(2, 2)
          .withPosition(2, 4)
            .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands
    
    //Adds buttons to the aforementioned Layout that run Limelight related commands when selected
    limelightCommands.add(new modifiedAim(m_drivetrain, m_limelight));
    limelightCommands.add(new modifiedRange(m_drivetrain, m_limelight));

    //Adds a Layout (basically a empty list) to the Drivebase tab for Drive Commands which will allow drivers to change from TankDrive to Arcade drive (or any drive command) on the spot  
    ShuffleboardLayout driveCommands = driveBaseTab
      .getLayout("Drive Commands", BuiltInLayouts.kList)
        .withSize(2, 2)
          .withPosition(4, 6)
            .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands

    //Adds buttons to the aforementioned Layout that run drive commands when selected
    driveCommands.add("Tank Drive", new TankDrive(m_driveAxis1, m_driveAxis2, m_drivetrain));
    driveCommands.add("Arcade Drive", new ArcadeDrive(m_driveAxis1, m_driveAxis2, m_drivetrain));

    //Adds a Layout (basically a empty list) to the Drivebase tab for Limelight Commands 
    ShuffleboardLayout driveConstants = driveBaseTab
      .getLayout("Drive Constants", BuiltInLayouts.kList)
        .withSize(2, 2)
          .withPosition(6, 4);
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoChooser.getSelected();
  }

  public void setDriveCommand(){
    m_drivetrain.setDefaultCommand(m_driveChooser.getSelected());
  }
}
