package frc.robot.commands.autonomous;

import frc.robot.commands.*;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import frc.robot.commands.launcher.ShootBall;
import frc.robot.commands.limelight.AimRange;
import frc.robot.commands.arduinolights.Climb;
import frc.robot.commands.arduinolights.SendNormal;
import frc.robot.commands.arduinolights.Shoot;
import frc.robot.commands.climber.LowerMast;
import frc.robot.commands.climber.RaiseMast;
import frc.robot.commands.climber.ToggleHook;
import frc.robot.commands.climber.TomahawkDown;
import frc.robot.commands.climber.TomahawkUp;
import frc.robot.commands.drivetrain.DriveStraight;
import frc.robot.commands.drivetrain.TurnDegrees;
import frc.robot.commands.intake.PickUp;

import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.turret.Intake;
import frc.robot.subsystems.turret.Launcher;
import frc.robot.subsystems.ArduinoLights;
import frc.robot.subsystems.Climber;

public class AutoRoutine extends SequentialCommandGroup {
    // Paths
    //! Not sure what you were trying to do here, these contstructors aren't quite right
    public static SequentialCommandGroup BasicLeftAuto;
    public static SequentialCommandGroup BasicMiddleAuto;
    public static SequentialCommandGroup BasicRightAuto;
    
    public AutoRoutine(DriveTrain drivetrain, Intake intake, Launcher launcher, Climber climber, ArduinoLights arduino, LimeLight limelight) {
        BasicLeftAuto = new SequentialCommandGroup(new DriveStraight(-.5, drivetrain));
        BasicMiddleAuto = new SequentialCommandGroup(new DriveStraight(-.5, 9), new WaitCommand(0.2));
        BasicRightAuto = new SequentialCommandGroup(new DriveStraight(1, -3.116));
        addCommands(
            //add commands for the robot to drive (More commands to driveTrain will be added once PathFinder is coded)
            new DriveStraight(10, drivetrain),
            new TurnDegrees(.3, 90, drivetrain),
            
            //add commands for climber
            new LowerMast(climber),
            new RaiseMast(climber),
            new ToggleHook(climber),
            new TomahawkDown(climber),
            new TomahawkUp(climber), 

            //add intake command to pick up ball
            new PickUp(intake),

            //add limelight command to get in range and aim
            new AimRange(drivetrain, limelight),

            //add launcher command to shoot ball
            new ShootBall(launcher),
            
            //add arduino commands
            new Climb(arduino),
            new SendNormal(arduino),
            new Shoot(arduino)
        );
    }
}
