package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.TurnDegrees;
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
    public static SequentialCommandGroup RedLeftAuto;
    public static SequentialCommandGroup RedMiddleAuto;
    public static SequentialCommandGroup RedRightAuto;
    public static SequentialCommandGroup BlueLeftAuto;
    public static SequentialCommandGroup BlueMiddleAuto; 
    public static SequentialCommandGroup BlueRightAuto; 
    public static SequentialCommandGroup GeneralAuto;

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, Feeder feeder, ArduinoLights lights, LimeLight limelight) {
        /*
            PathWeaver Test Code, currently deprecated because we couldn't get PathWeaver to work

            
        */ 
        RedLeftAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using original var, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(85.7263, /* fix distance */ drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        RedMiddleAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using original var, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(83.5703, /* fix distance */ drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        RedRightAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using original var, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(79.259, /* fix distance */ drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );

        BlueLeftAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using original var, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(87.641314, /* fix distance */ drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );

        BlueMiddleAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using original var, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(85.906926, /* fix distance */ drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );

        BlueRightAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using original var, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(76.896034, /* fix distance */ drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher, feeder, limelight)
        );

        // Use for first competition
        GeneralAuto = new SequentialCommandGroup(
            new AimRange(drivetrain, limelight),
            new AutoShootBall(launcher,feeder,limelight),
            new DriveStraight(-2.54, drivetrain)
        );
    }
}
