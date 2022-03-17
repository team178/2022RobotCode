package frc.robot;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTableEntry;

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    public static final int kLeftMotor1Port = 1;
    public static final int kLeftMotor2Port = 3;
    public static final int kRightMotor1Port = 2;
    public static final int kRightMotor2Port = 4;

    public static final int kEncoderCPR = 4096;
    public static final double kWheelDiameterMeters = Units.inchesToMeters(6);
    public static final double kEncoderDistancePerPulse =
        // Assumes the encoders are directly mounted on the wheel shafts
        (kWheelDiameterMeters * Math.PI) / (double) kEncoderCPR;

    public static final double kTurnP = 1;
    public static final double kTurnI = 0;
    public static final double kTurnD = 0;

    public static final double kMaxTurnRateDegPerS = 100;
    public static final double kMaxTurnAccelerationDegPerSSquared = 300;

    public static final double kTurnToleranceDeg = 5;
    public static final double kTurnRateToleranceDegPerS = 10; // degrees per second
  }

  public static final class OIConstants {
    public static final int kJoystickPort = 0;
    public static final int kControllerPort = 1;

    public static NetworkTableEntry kXAxisSpeedMult;
    public static NetworkTableEntry kZAxisSpeedMult;

    public static NetworkTableEntry kSlowXAxisSpeedMult;
    public static NetworkTableEntry kSlowZAxisSpeedMult;
  }
  
  public static final class IntakeConstants{
    public static final int kMotorPort = 7;
    
    public static final int kForwardPort = 0;
    public static final int kRevPort = 1;
    public static final int kBouncerPortFOR = 2;
    public static final int kBouncerPortREV = 3;

    public static final int kLimitSwitchPort = 3;
  }
  
  public static final class LauncherConstants{
    public static final int kFlyWheelMotorPort1 = 10;
    public static final int kFlyWheelMotorPort2 = 9;
    public static final int kFeedWheelMotorPort = 8;
    
    public static double kMaxRPM = 5700; // just a guess, will need to be tuned
    
    public static double kP = 6E-5;
    public static double kI = 0;
    public static double kD = 0;
    public static double kIz = 0;
    public static double kFF = 0.000015;
    public static double kMaxOutput = 1;
    public static double kMinOutput = 0;

    public static NetworkTableEntry kLauncherSpeed; // Used for testing purposes
    
    public static double kLaunchAngleDegrees;
      public static double kLaunchAngleRadians = Math.toRadians(kLaunchAngleDegrees);

    public static double kLaunchHeight; 
    
    public static double kFlyWheelRadius; 
    
    public static double kMaxVelocity;
  }

  public static final class LimeLightConstants{
    public static final double kTargetHeight = 2.4384;
    
    public static final double kMountAngle = 35;
    public static final double kLensHeight = .737; 
  }
  
  public static final class ClimberConstants{
    public static final int kClimberMotorPort = 5;
    public static final int kExtendingArmWinchPort = 6; 

    public static final int kHookSolenoidPortFWD = 4;
    public static final int kHookSolenoidPortREV = 5;

    public static final int kHighLimitSwitchPort = 0;
    public static final int kLowLimitSwitchPort = 1;

  }

  public static final class Characterization{
    /*note to self(Rithvik): change the B and zeta values to arbitrary dummy values for now, 
    and make sure to put in the real and reasonable values later*/

    public static final double ksVolts = 0.895;
    public static final double kvVoltSecondsPerMeter = 6.73;
    public static final double kaVoltSecondsSquaredPerMeter = 0.0136;

    public static final double kPDriveVel = 0.005;

    //public static final DifferentialDriveKinematics kDriveKinematics =
        //new DifferentialDriveKinematics(kTrackwidthMeters);

    //public static final SimpleMotorFeedforward m_feedforward = new SimpleMotorFeedforward(ksVolts, kvVoltSecondsPerMeter, kaVoltSecondsSquaredPerMeter);
    //public static final PIDController m_leftPIDController = new PIDController(DriveConstants.kPDriveVel, 0, 0);
    //public static final PIDController m_rightPIDController = new PIDController(DriveConstants.kPDriveVel, 0, 0);

    public static final double kMaxSpeedMetersPerSecond = 0.8;
    public static final double kMaxAccelerationMetersPerSecondSquared = 0.8;

    // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

  

  }

}
