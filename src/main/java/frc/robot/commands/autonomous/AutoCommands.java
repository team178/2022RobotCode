package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.intake.AutoPickUp;
import frc.robot.commands.launcher.FireBall;
import frc.robot.commands.drivetrain.DriveStraight;
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
        // PathWeaver Test Code (DO NOT USE, CREATE A FILE CALLED robotInit)
        /*
        String trajectoryJSON = "paths/YourPath.wpilib.json"; // replace YourPath with the path name
        Trajectory trajectory = new Trajectory();

        @Override
        public void robotInit() {
            try {
                Path trajectoryPath = Filesystem.getDeployDirectory().toPath().resolve(trajectoryJSON);
                trajectory = TrajectoryUtil.fromPathweaverJson(trajectoryPath); 
            } catch (IOException ex) {
                DriverStation.reportError("Unable to open trajectory: " + trajectoryJSON, ex.getStackTrace());
            }
        }
        */

        GeneralAuto = new SequentialCommandGroup(
            new ModifiedAim(drivetrain, limelight),
            new FireBall(launcher, feeder, intake, -0.65),
            new TurnDegrees(180 - drivetrain.getHeading(), drivetrain),
            new AutoPickUp(intake, drivetrain, 1.634 /* MOVE OUT DISTANCE */),
            new TurnDegrees(180, drivetrain),
            new ModifiedAim(drivetrain, limelight),
            new ModifiedRange(drivetrain, limelight, 0.911 /* MOVE TO BORDER */),
            new FireBall(launcher, feeder, intake, -0.65),
            new TurnDegrees(-drivetrain.getHeading(), drivetrain),
            new DriveStraight(-1, drivetrain)
        );

        ModifiedGeneralAuto = new SequentialCommandGroup(
            new FireBall(launcher, feeder, intake, -0.65),
            new DriveStraight(-2.5, drivetrain)
        );

    }
}