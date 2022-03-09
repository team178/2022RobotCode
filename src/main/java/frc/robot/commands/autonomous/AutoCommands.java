package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.drivetrain.AutoDrive;
import frc.robot.commands.drivetrain.DriveStraight;
import frc.robot.commands.limelight.AimRange;
import frc.robot.subsystems.ArduinoLights;
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

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, Feeder feeder, ArduinoLights lights, LimeLight limelight) {
        // TO-DO: need to add pathing commands in group
        LeftAuto = new SequentialCommandGroup(
            //this routine is Done
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            new DriveStraight(1.69545, drivetrain),
            new TurnDegrees(90, drivetrain),
            new DriveStraight(0.27813, drivetrain),
            new TurnDegrees(-52, drivetrain),
            new AutoPickUp(intake, drivetrain, 0.5842),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        MiddleAuto = new SequentialCommandGroup(
            //code using new idea format(is it correct?)
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            //turn back to original pos
            new TurnDegrees(180, drivetrain),
            new DriveStraight(1.69545 /* fix distance */, drivetrain),
            new AutoPickUp(intake, drivetrain, 0.5842),
            //move bacK?
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        RightAuto = new SequentialCommandGroup(
            new DriveStraight(2.493518, drivetrain),
            new TurnDegrees(90, drivetrain),
            new AutoPickUp(intake, drivetrain, 0.733806),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );
    }
}
