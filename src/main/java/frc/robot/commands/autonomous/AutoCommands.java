package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.drivetrain.DriveStraight;
import frc.robot.commands.limelight.AimRange;
import frc.robot.commands.limelight.ModifiedAim;
import frc.robot.commands.limelight.ModifiedRange;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Feeder;
import frc.robot.subsystems.turret.Intake;
import frc.robot.subsystems.turret.Launcher;

public class AutoCommands {
    //Left, middle, right, from looking from the hub outwards perspectives
    public static SequentialCommandGroup LeftAuto;
    public static SequentialCommandGroup MiddleAuto;
    public static SequentialCommandGroup RightAuto;
    public static SequentialCommandGroup GeneralAuto;
    public static SequentialCommandGroup ModifiedGeneralAuto;

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, Feeder feeder, LimeLight limelight) {
        // Use for first competition
        /*
        OLD
        GeneralAuto = new SequentialCommandGroup(
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher,feeder,limelight),
            new DriveStraight(-3.3, drivetrain)
        );
        */

        GeneralAuto = new SequentialCommandGroup(
            new ModifiedAim(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            new TurnDegrees(180 - drivetrain.getHeading(), drivetrain),
            new AutoPickUp(intake, drivetrain, 1 /* MOVE OUT DISTANCE CHANGE */),
            new TurnDegrees(180, drivetrain),
            new ModifiedAim(drivetrain, limelight),
            new ModifiedRange(drivetrain, limelight, 1 /* MOVE TO BORDER CHANGE */),
            new AutoShootBall(launcher, feeder, limelight),
            new TurnDegrees(-drivetrain.getHeading(), drivetrain),
            new DriveStraight(-1, drivetrain)
        );

        ModifiedGeneralAuto = new SequentialCommandGroup(
            new AutoShootBall(launcher, feeder, limelight),
            new DriveStraight(2.5, drivetrain)
        );

    }
}