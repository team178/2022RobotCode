package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.arduinolights.Shoot;
import frc.robot.commands.intake.PickUp;
import frc.robot.commands.launcher.ShootBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Intake;
import frc.robot.subsystems.turret.Launcher;

public class AutoCommands {
    public static SequentialCommandGroup RedLeftAuto;
    public static SequentialCommandGroup RedMiddleAuto;
    public static SequentialCommandGroup RedRightAuto;
    public static SequentialCommandGroup BlueLeftAuto;
    public static SequentialCommandGroup BlueMiddleAuto;
    public static SequentialCommandGroup BlueRightAuto;

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, ArduinoLights lights, LimeLight limelight) {
        //TODO: need to add pathing commands in group
        RedLeftAuto = new SequentialCommandGroup(
            //Pathing commands,
            new PickUp(intake),
            new AimRange(drivetrain, limelight),
            new ShootBall(launcher),
            new Shoot(lights)
        );
        
        RedMiddleAuto = new SequentialCommandGroup(
            //Pathing commands,
            new PickUp(intake),
            new AimRange(drivetrain, limelight),
            new ShootBall(launcher),
            new Shoot(lights)
        );
        
        RedRightAuto = new SequentialCommandGroup(
            //Pathing commands,
            new PickUp(intake),
            new AimRange(drivetrain, limelight),
            new ShootBall(launcher),
            new Shoot(lights)
        );

        BlueLeftAuto = new SequentialCommandGroup(
            //Pathing commands,
            new PickUp(intake),
            new AimRange(drivetrain, limelight),
            new ShootBall(launcher),
            new Shoot(lights)
        );
        
        BlueMiddleAuto = new SequentialCommandGroup(
            //Pathing commands,
            new PickUp(intake),
            new AimRange(drivetrain, limelight),
            new ShootBall(launcher),
            new Shoot(lights)
        );
        
        BlueRightAuto = new SequentialCommandGroup(
            //Pathing commands,
            new PickUp(intake),
            new AimRange(drivetrain, limelight),
            new ShootBall(launcher),
            new Shoot(lights)
        );
    }
}
