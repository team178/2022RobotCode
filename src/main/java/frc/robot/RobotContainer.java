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
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.launcher.ReverseFeedLauncherWheel;
import frc.robot.commands.launcher.RunLauncher;
import frc.robot.commands.launcher.ShootBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OIConstants;
import frc.robot.commands.autonomous.AutoCommands;
import frc.robot.commands.autonomous.AutoPickUp;
import frc.robot.commands.autonomous.AutoShootBall;
import frc.robot.commands.limelight.ModifiedAim;
import frc.robot.commands.limelight.ModifiedRange;
import frc.robot.commands.climber.LowerMast;
import frc.robot.commands.climber.RaiseMast;
import frc.robot.commands.climber.ToggleHook;
import frc.robot.commands.climber.TomahawkDown;
import frc.robot.commands.climber.TomahawkUp;
import frc.robot.commands.drivetrain.ArcadeDrive;
import frc.robot.commands.drivetrain.TankDrive;
import frc.robot.commands.intake.PickUp;
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
  private final ArduinoLights m_arduino;

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
    m_arduino = new ArduinoLights(7, 8, 9);

    // AutoCommands.init(m_drivetrain, m_intake, m_launcher, m_feeder, m_arduino, m_limelight);

    if(RobotBase.isReal()){
      //Camera 1
      camera1 = CameraServer.startAutomaticCapture("cam0", 0);
      //camera1.setResolution(160, 90);
      camera1.setFPS(14);
      camera1.setPixelFormat(PixelFormat.kYUYV); //formats video specifications for cameras

      //Camera 2
      camera2 = CameraServer.startAutomaticCapture("cam1", 1);
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

    // m_drivetrain.setDefaultCommand(
    //   new TankDrive(m_controller_main::getLeftStickY, m_controller_main::getRightStickY, m_drivetrain));

    m_drivetrain.setDefaultCommand(
      new ArcadeDrive(m_controller_main::getLeftStickY, m_controller_main::getRightStickX, m_drivetrain));

    // Slowness II
    m_controller_main.leftTrigger
      .whenPressed(() -> m_drivetrain.setSpeedMultiplier(OIConstants.kBaseDriveSpeedMult/2))
      .whenReleased(() -> m_drivetrain.setSpeedMultiplier(OIConstants.kBaseDriveSpeedMult));
    
    // Limelight trigger
    // m_controller_main.rightTrigger
    //   .whileHeld();

    // Control the launcher via right trigger
    m_controller_aux.rightTrigger
      .whileHeld(new RunLauncher(m_launcher, -0.60));

    //m_controller_aux.leftTrigger
      //.whileHeld(new SlowIntakeUp(m_intake));

    m_controller_aux.rightBumper
      .whileHeld(new ShootBall(m_feeder));

    m_controller_aux.topDPAD
      .whileHeld(new RaiseMast(m_climber));

    m_controller_aux.bottomDPAD
      .whileHeld(new LowerMast(m_climber));
    
    m_controller_aux.leftDPAD
      .whileHeld(new TomahawkDown(m_climber));

    m_controller_aux.rightDPAD
      .whileHeld(new TomahawkUp(m_climber));

    m_controller_aux.a
      .toggleWhenPressed(new PickUp(m_intake));
    
    m_controller_aux.b
      .whileHeld(new RunLauncher(m_launcher,-0.1));

    m_controller_aux.x  
      .whenPressed(new ToggleHook(m_climber));

    m_controller_aux.y  
      .whenPressed(new ReverseFeedLauncherWheel(m_launcher,m_feeder));

  }

  private void configureShuffleBoard() {
    //Autonomous Chooser Options (How our robot is going to tackle auto)
    m_autoChooser.setDefaultOption("General Auto", AutoCommands.GeneralAuto);
    //m_autoChooser.addOption("Left Auto", AutoCommands.LeftAuto);
    //m_autoChooser.addOption("Middle Auto", AutoCommands.MiddleAuto);
    //m_autoChooser.addOption("Right Auto", AutoCommands.RightAuto);
    m_autoChooser.addOption("Modified Range", new ModifiedRange(m_drivetrain, m_limelight));
    m_autoChooser.addOption("Modified Aim", new ModifiedAim(m_drivetrain, m_limelight));
    m_autoChooser.addOption("Aim and Range", new AimRange(m_drivetrain, m_limelight));

    //Creates new Shuffleboard tab called Drivebase
    ShuffleboardTab testTab = Shuffleboard.getTab("Drivebase");

    //Adds a chooser to the Drivebase tab to select autonomous routine (before anything is ran)
    testTab
      .add("Autonomous Routine", m_autoChooser)
        .withSize(2, 1)
          .withPosition(0, 3);
    
    // //Adds a Layout (basically a empty list) to the Drivebase tab for Limelight Commands 
    // ShuffleboardLayout limelightCommands = testTab
    //   .getLayout("Limelight Commands", BuiltInLayouts.kList)
    //     .withSize(2, 2)
    //       .withPosition(2, 4)
    //         .withProperties(Map.of("Label position", "HIDDEN")); // hide labels for commands
    
    // //Adds buttons to the aforementioned Layout that run Limelight related commands when selected
    // limelightCommands.add(new modifiedAim(m_drivetrain, m_limelight));
    // limelightCommands.add(new modifiedRange(m_drivetrain, m_limelight));

    //Adds a Layout (basically a empty list) to the Drivebase tab for Limelight Commands 
    ShuffleboardLayout turretCommands = testTab
      .getLayout("Turret Commands", BuiltInLayouts.kList)
        .withSize(2, 2)
          .withPosition(6, 4);

    turretCommands.add(new AutoPickUp(m_intake, m_drivetrain, 0));
    turretCommands.add(new AutoShootBall(m_launcher, m_feeder, m_limelight));

    LauncherConstants.kLauncherSpeed = testTab
      .add("Speed for Launcher", 0)
        .withWidget(BuiltInWidgets.kNumberSlider)
          .withProperties(Map.of("min", -1, "max", 1))
            .withPosition(0, 2)
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
