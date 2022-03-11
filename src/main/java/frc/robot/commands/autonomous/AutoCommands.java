package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.drivetrain.DriveStraight;
import frc.robot.commands.limelight.AimRange;
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

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, Feeder feeder, LimeLight limelight) {
        /*
            PathWeaver Test Code, currently deprecated because we couldn't get PathWeaver to work
            
            
        */ 
        LeftAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using limelight, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(2.1774, drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        MiddleAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using limelight, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(2.1226, drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        RightAuto = new SequentialCommandGroup(
            // shoot ball & save original rotation in a var
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher, feeder, limelight),
            // calibrate the robot using limelight, turn 180 degrees, drive straight into ball
            new TurnDegrees(180, drivetrain),
            new DriveStraight(2.0137, drivetrain),
            // pick up ball
            new AutoPickUp(intake, drivetrain, 0.5842),
            new TurnDegrees(180, drivetrain),
            // shoot ball, autonomous ends
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher, feeder, limelight)
        );
        
        // Use for first competition
        GeneralAuto = new SequentialCommandGroup(
            new AimRange(drivetrain, limelight, 1.2192),
            new AutoShootBall(launcher,feeder,limelight),
            new DriveStraight(-2.54, drivetrain)
        );
    }
}