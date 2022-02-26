package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

// not sure if this actually works
public class AutoDrive extends CommandBase {
    private DriveTrain drivetrain;
    private double speed;
    private double distance;
    
    public AutoDrive(double speed, double distance, DriveTrain drivetrain) {
        this.drivetrain = drivetrain;
        this.speed = speed;
        this.distance = distance;
    }
      // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        drivetrain.reset();
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        drivetrain.tankDrive(speed, -speed);
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDrive(0, 0);
        drivetrain.reset();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(Math.abs(distance) - Math.abs(drivetrain.rightPosition.getAsDouble())) < 0.01;
    }
}
