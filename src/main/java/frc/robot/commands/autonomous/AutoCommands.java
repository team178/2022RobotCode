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
    public static SequentialCommandGroup LeftAuto;
    public static SequentialCommandGroup MiddleAuto;
    public static SequentialCommandGroup RightAuto;

    //public static SequentialCommandGroup AutoClimb;

    public static void init(DriveTrain drivetrain, Intake intake, Launcher launcher, Feeder feeder, ArduinoLights lights, LimeLight limelight) {
        // TO-DO: need to add pathing commands in group
        LeftAuto = new SequentialCommandGroup(
            //this routine is Done
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights),
            new AutoDrive(1, 66.75 /* inches?? */, drivetrain),
            new TurnDegrees(1, 90, drivetrain),
            new AutoDrive(1, 10.95 /* inches?? */, drivetrain),
            new TurnDegrees(1, -52, drivetrain),
            new AutoDrive(1, 23 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)

        );
        
        MiddleAuto = new SequentialCommandGroup(
            //Pathing commands
            new AutoDrive(1, 84.53 /* inches */, drivetrain), 
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );
        
        RightAuto = new SequentialCommandGroup(
            new AutoDrive(1, 98.17 /* inches?? */, drivetrain),
            new TurnDegrees(1, 90, drivetrain),
            new AutoDrive(1, 28.89 /* inches?? */, drivetrain),
            new AutoPickUp(intake),
            new AimRange(drivetrain, limelight),
            new AutoShootBall(feeder, lights)
        );
    }
}
