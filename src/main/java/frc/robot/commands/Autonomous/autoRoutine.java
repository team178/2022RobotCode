package frc.robot.commands.Autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.DriveTrain.DriveStraight;
import frc.robot.commands.DriveTrain.TurnDegrees;
import frc.robot.commands.Intake.PickUp;
import frc.robot.commands.Launcher.shootBall;
import frc.robot.commands.Limelight.aimRange;
import frc.robot.commands.climber.LowerMast;
import frc.robot.commands.climber.RaiseMast;
import frc.robot.commands.climber.ToggleHook;
import frc.robot.commands.climber.TomahawkDown;
import frc.robot.commands.climber.TomahawkUp;
import frc.robot.commands.ArduinoLights.Climb;
import frc.robot.commands.ArduinoLights.SendNormal;
import frc.robot.commands.ArduinoLights.Shoot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.LimeLight;
import frc.robot.subsystems.Turret.Intake;
import frc.robot.subsystems.Turret.Launcher;
import frc.robot.subsystems.Climber;

public class AutoRoutine extends SequentialCommandGroup {
    
    public AutoRoutine(DriveTrain drivetrain, Intake intake, Launcher launcher, Climber climber, Arduino arduino, LimeLight limelight){
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
            new aimRange(drivetrain, limelight),

            //add launcher command to shoot ball
            new shootBall(launcher)
            
            //add arduino commands
            new ArduinoClimb(arduino, climb);
            new ArduinoSendNormal(arduino, sendnormal);
            new ArduinoShoot(arduino, shoot);
            
        );
    }
}
