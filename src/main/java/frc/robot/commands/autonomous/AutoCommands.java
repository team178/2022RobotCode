package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arduinolights.Shoot;
import frc.robot.commands.drivetrain.AutoDrive;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.intake.AutoPickUp;
import frc.robot.commands.intake.PickUp;
import frc.robot.commands.launcher.AutoShootBall;
import frc.robot.commands.launcher.ShootBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
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

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, ArduinoLights lights, LimeLight limelight) {
        // TO-DO: need to add pathing commands in group
        RedLeftAuto = new SequentialCommandGroup(
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, lights)
        );
        
        RedMiddleAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, lights)
        );
        
        RedRightAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, lights)
        );

        BlueLeftAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, lights)
        );
        
        BlueMiddleAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, lights)
        );
        
        BlueRightAuto = new SequentialCommandGroup(
            new AutoDrive(1, 85.67 /* inches?? */, drivetrain),
            new TurnDegrees(1, 270, drivetrain),
            new AutoDrive(1, 34.39 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, lights)
        );
    }
}
