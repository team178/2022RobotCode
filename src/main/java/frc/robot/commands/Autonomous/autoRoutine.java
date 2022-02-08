package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveTrain.DriveStraight;
import frc.robot.commands.DriveTrain.TurnDegrees;
import frc.robot.commands.Intake.pickUp;
import frc.robot.commands.Launcher.shootBall;
import frc.robot.commands.Limelight.aimRange;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Turret.Intake;
import frc.robot.subsystems.Turret.Launcher;

public class autoRoutine extends SequentialCommandGroup {
    
    public autoRoutine(DriveTrain drivetrain, Intake intake, Launcher launcher, LimeLight limelight){
        addCommands(
            //add commands for the robot to drive to ball (Someone can code this out)
            new DriveStraight(10, drivetrain),
            new TurnDegrees(.3, 90, drivetrain),

            //add intake command to pick up ball
            new pickUp(intake),

            //add limelight command to get in range and aim
            new aimRange(drivetrain, limelight),

            //add launcher command to shoot ball
            new shootBall(launcher)
        );
    }
}
