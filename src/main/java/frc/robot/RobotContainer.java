// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.UsbCamera;
import edu.wpi.first.cscore.VideoMode.PixelFormat;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.launcher.FireBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonomous.AutoCommands;
import frc.robot.commands.limelight.ModifiedAim;
import frc.robot.commands.limelight.ModifiedRange;
import frc.robot.commands.climber.ClimberMove;
import frc.robot.commands.climber.ToggleHook;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.DriveDistance;
import frc.robot.commands.drivetrain.DriveStraight;
import frc.robot.commands.intake.AutoPickUp;
import frc.robot.commands.intake.PickUp;
import frc.robot.commands.intake.SpitBall;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Intake;
import frc.robot.subsystems.turret.Launcher;
import frc.robot.subsystems.turret.Feeder;
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
  private final Feeder m_feeder;

  private final Climber m_climber;
  private final LimeLight m_limelight;

  public static ArduinoLights m_lights;

  //USB Camera declarations
  private final UsbCamera camera1;
  private final UsbCamera camera2;
  private final UsbCamera camera3;

  //Creates joystick object for the Main (0) and Aux (1) controllers
  private final ConsoleController m_controller_main = new ConsoleController(0);
  private final ConsoleController m_controller_aux = new ConsoleController(1);

  // Create SmartDashboard chooser for autonomous routines and drive
  private final SendableChooser<Command> m_autoChooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    m_drivetrain = new DriveTrain();

    m_intake = new Intake();
    m_launcher = new Launcher();
    m_feeder = new Feeder();

    m_climber = new Climber();
    m_limelight = new LimeLight();
    m_lights = new ArduinoLights(7, 8, 9);

    AutoCommands.init(m_drivetrain, m_intake, m_launcher, m_feeder, m_limelight);

    if(RobotBase.isReal()){
      //Camera 1
      camera1 = CameraServer.startAutomaticCapture("Main", 0);
      //camera1.setResolution(160, 90);
      camera1.setFPS(14);
      camera1.setPixelFormat(PixelFormat.kYUYV); //formats video specifications for cameras

      //Camera 2
      camera2 = CameraServer.startAutomaticCapture("Intake", 1);
      //camera2.setResolution(160, 120);
      camera2.setFPS(14);
      camera2.setPixelFormat(PixelFormat.kYUYV); //formats video specifications for cameras

      //Camera 3
      camera3 = CameraServer.startAutomaticCapture("cam2", 2);
      //camera3.setResolution(160, 120);
      camera3.setFPS(14);
      camera3.setPixelFormat(PixelFormat.kYUYV); //formats video specifications for cameras
    }
    else{
      camera1 = null;
      camera2 = null;
      camera3 = null;
    }

    // Configure the button bindings
    configureShuffleBoard();
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(m_controller_main::getLeftStickY, m_controller_main::getRightStickX, m_drivetrain, false));
    
    m_climber.setDefaultCommand(
      new ClimberMove(m_controller_aux::getLeftStickY, m_controller_aux::getRightStickX, m_climber)
    );

    
    // Slowness II
    m_controller_main.leftTrigger
      .whenPressed(new ArcadeDrive(m_controller_main::getLeftStickY, m_controller_main::getRightStickX, m_drivetrain, true))
      .whenReleased(new ArcadeDrive(m_controller_main::getLeftStickY, m_controller_main::getRightStickX, m_drivetrain, false));

    // figure out what the joystick buttons do then figure out what to bind these to
    m_controller_main.a
      .whileHeld(new ModifiedAim(m_drivetrain, m_limelight));

    m_controller_main.b
      .whileHeld(new ModifiedRange(m_drivetrain, m_limelight, 1.51));

    // Controller Aux
    m_controller_aux.rightTrigger
      .whileHeld(new FireBall(m_launcher, m_feeder, m_intake, -.7));

    m_controller_aux.rightBumper
      .whileHeld(new FireBall(m_launcher, m_feeder, m_intake, -.4));

    m_controller_aux.leftTrigger
      .whileHeld(new PickUp(m_intake));
    
    m_controller_aux.b
      .whileHeld(new SpitBall(m_intake));

    m_controller_aux.leftBumper 
      .whenPressed(new ToggleHook(m_climber));
    

  }

  private void configureShuffleBoard() {
    //Autonomous Chooser Options (How our robot is going to tackle auto)
    m_autoChooser.setDefaultOption("Modified General Auto", AutoCommands.ModifiedGeneralAuto);
    m_autoChooser.addOption("General Auto", AutoCommands.GeneralAuto);

    //m_autoChooser.addOption("Left Auto", AutoCommands.LeftAuto);
    //m_autoChooser.addOption("Middle Auto", AutoCommands.MiddleAuto);
    //m_autoChooser.addOption("Right Auto", AutoCommands.RightAuto);
    m_autoChooser.addOption("Modified Range", new ModifiedRange(m_drivetrain, m_limelight, 2.5));
    m_autoChooser.addOption("Modified Aim", new ModifiedAim(2, m_drivetrain, m_limelight));
    m_autoChooser.addOption("Aim and Range", new AimRange(m_drivetrain, m_limelight,1.2192));//number got by looking at limelight distance and pushing it against the hub -.1 meter
    m_autoChooser.addOption("Auto PickUp", new AutoPickUp(m_intake, m_drivetrain, 1.5));
    m_autoChooser.addOption("Auto Shoot", new FireBall(m_launcher, m_feeder, m_intake, -0.65));
    m_autoChooser.addOption("Drive Straight", new DriveStraight(-1.5, m_drivetrain));
    m_autoChooser.addOption("Drive Distance", new DriveDistance(m_drivetrain, 1, .1));

    //Creates new Shuffleboard tab called Drivebase
    ShuffleboardTab testTab = Shuffleboard.getTab("Test Tab");

    //Adds a chooser to the Drivebase tab to select autonomous routine (before anything is ran)
    testTab
      .add("Autonomous Routine", m_autoChooser)
        .withSize(2, 1)
          .withPosition(0, 3);

    LauncherConstants.kLauncherSpeed = testTab
      .add("Speed for Launcher", -0.65)
        .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1))
            .withPosition(0, 2)
              .getEntry();
    
    OIConstants.kXAxisSpeedMult = testTab
      .add("X Axis Speed Mult", 0.8)
        .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1))
            .withPosition(1, 2)
              .getEntry();
    
    OIConstants.kSlowXAxisSpeedMult = testTab
      .add("Slow X Axis Speed Mult", 0.6)
        .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1))
            .withPosition(1, 4)
              .getEntry();
    
    OIConstants.kZAxisSpeedMult = testTab
      .add("Z Axis Speed Mult", 0.8)
        .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1))
            .withPosition(2, 2)
              .getEntry();

    OIConstants.kSlowZAxisSpeedMult = testTab
      .add("Slow Z Axis Speed Mult", 0.6)
        .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1))
            .withPosition(1, 4)
              .getEntry();
    
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
}
