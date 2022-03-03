package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.drivetrain.AutoDrive;
import frc.robot.commands.intake.AutoPickUp;
import frc.robot.commands.launcher.AutoShootBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Feeder;
import frc.robot.subsystems.turret.Intake;
import frc.robot.subsystems.turret.Launcher;

public class AutoCommands {
    //Left, middle, right, from looking from the hub outwards perspectives
    public static SequentialCommandGroup RedLeftAuto;
    public static SequentialCommandGroup RedMiddleAuto;
    public static SequentialCommandGroup RedRightAuto;
    public static SequentialCommandGroup BlueLeftAuto;
    public static SequentialCommandGroup BlueMiddleAuto;
    public static SequentialCommandGroup BlueRightAuto;

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, Feeder feeder, ArduinoLights lights, LimeLight limelight) {
        // TO-DO: need to add pathing commands in group
        RedLeftAuto = new SequentialCommandGroup(
            //this routine is Done
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights),
            new AutoDrive(1, 48.75 /* inches?? */, drivetrain),
            new TurnDegrees(1, -67.5, drivetrain),
            new AutoDrive(1, 18 /* inches?? */, drivetrain),
            new TurnDegrees(1, 111.05, drivetrain),
            new AutoDrive(1, 25.33 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)

        );
        
        RedMiddleAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoDrive(1, 84.53 /* inches */, drivetrain), 
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );
        
        RedRightAuto = new SequentialCommandGroup(
            new AutoDrive(1, 85.67 /* inches?? */, drivetrain),
            new TurnDegrees(1, 270, drivetrain),
            new AutoDrive(1, 28.89 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );

        BlueLeftAuto = new SequentialCommandGroup(
            //Pathing commands  //this is done now
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights),
            new AutoDrive(1, 48.75 /* inches?? */, drivetrain),
            new TurnDegrees(1, -67.5, drivetrain),
            new AutoDrive(1, 18 /* inches?? */, drivetrain),
            new TurnDegrees(1, 111.05, drivetrain),
            new AutoDrive(1, 25.33 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );
        
        BlueMiddleAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoDrive(1, 84.53 /* inches */, drivetrain), 
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );
        
        BlueRightAuto = new SequentialCommandGroup(
            new AutoDrive(1, 85.67 /* inches?? */, drivetrain),
            new TurnDegrees(1, 270, drivetrain),
            new AutoDrive(1, 28.89 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );
    }
}
