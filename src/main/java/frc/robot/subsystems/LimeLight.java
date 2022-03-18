// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.LimeLightConstants;

public class LimeLight extends SubsystemBase {
    
    private NetworkTable table;

    private boolean isConnected = false;

    public LimeLight() {
        table = NetworkTableInstance.getDefault().getTable("limelight");
    }
    
    public LimeLight(String name) {
        table = NetworkTableInstance.getDefault().getTable(name);
    }

    public LimeLight(NetworkTable table) {
        this.table = table;
    }

    public boolean isConnected() {
        return isConnected;
    }

    /**
     * @return whether or not the limelight has any valid targets
     */
    public boolean isTargetFound() {
        return !(table.getEntry("tv").getDouble(0) == 0f); // changed this to "not" as it was returning opposite
    }

    /**
     * @return the horizontal offset from crosshair to target (-29.8 to +29.8 degrees)
     */
    public double getHorizontalDegToTarget() {
        return table.getEntry("tx").getDouble(0);
    }

    /**
     * @return vertical offset from crosshair to target (-24.85 to +24.85 degrees)
     */
    public double getVerticalDegToTarget() {
        return table.getEntry("ty").getDouble(0);
    }

    /**
     * @return target area (0% to 100% of image)
     */
    public double getTargetArea() {
        return table.getEntry("ta").getDouble(0);
    }

    /**
     * @return skew or rotation (-90 to 0 degrees)
     */
    public double getSkewRotation() {
        return table.getEntry("ts").getDouble(0);
    }

    /**
     * @return current pipeline’s latency contribution (ms). Add at least 11ms for image capture latency.
     */
    public double getPipelineLatency() {
        return table.getEntry("tl").getDouble(0);
    }

    /**
     * @param pipeline sets the limelight’s current pipeline
     */
    public void setPipeline(int pipeline) {
        if (pipeline < 0) {
            pipeline = 0;
            throw new IllegalArgumentException("Pipeline can not be less than zero");
        } else if(pipeline > 9) {
            pipeline = 9;
            throw new IllegalArgumentException("Pipeline can not be greater than nine");
        }
        table.getEntry("pipeline").setValue(pipeline);
    }

    /**
     * @return current pipeline of limelight
     */
    public double getPipeline() {
        return table.getEntry("pipeline").getDouble(0.0);
    }


    /**
     * See diagram https://docs.limelightvision.io/en/latest/_images/DistanceEstimation.jpg
     * @return distance from target in meters
    */
    public double estimateDistance(){
        double angleToTargetDegree = LimeLightConstants.kMountAngle + getVerticalDegToTarget();
        double angleToTargetRadians = angleToTargetDegree * (Math.PI / 180);

        double distanceToTarget = (LimeLightConstants.kTargetHeight - LimeLightConstants.kLensHeight) / Math.tan(angleToTargetRadians);
        return distanceToTarget;
    }

    /**
     * Credit to Vanden for Algorithm
     * @return velocity we would need at a given distance
     */
    public double calculateLauncherVelocity(){
        double verticalDistance = LauncherConstants.kLaunchHeight - LimeLightConstants.kTargetHeight;

        double numCalc = Math.pow(estimateDistance(), 2) * 9.81;
        double denCalc = (2*Math.cos(LauncherConstants.kLaunchAngleRadians) * 
            ((estimateDistance() * Math.sin(LauncherConstants.kLaunchAngleRadians)) + 
                (verticalDistance * Math.cos(LauncherConstants.kLaunchAngleRadians))));

        return Math.sqrt(numCalc / denCalc);
    }

    /**
     * 
     * @return whether or not velocity needed is possible
     */
    public boolean isVelocityPossible(){
        return ((calculateLauncherVelocity() > LauncherConstants.kMaxVelocity) ? false : true);
    }

    public boolean isTargetExistant(){
        return ((getHorizontalDegToTarget() != 0) ? true : false);
    }

    public boolean isDistancePossible(){
        return((estimateDistance() < 1.16 && estimateDistance() > 1.32) ? true : false);
    }

    /**
     * 
     * @return whether or not shooting angle is possible
     */
    public boolean isAnglePossible(){
        return ((Math.abs(getHorizontalDegToTarget()) > -.5 || Math.abs(getHorizontalDegToTarget()) < .5) ? false : true); // 5 Represents a degree tolerance for shooting
    }

    /** The log method puts interesting information to the SmartDashboard. */
    public void log() {
        SmartDashboard.putNumber("LimelightX", getHorizontalDegToTarget());
        SmartDashboard.putNumber("LimelightY", getVerticalDegToTarget());
        SmartDashboard.putNumber("LimelightArea", getTargetArea());
        SmartDashboard.putNumber("Limelight Distance", estimateDistance());
        SmartDashboard.putBoolean("Good Angle", isAnglePossible());
        SmartDashboard.putBoolean("Good Distance", isDistancePossible());
        SmartDashboard.putBoolean("Target Found", isTargetExistant());
        //SmartDashboard.putBoolean("Is Scorable", isVelocityPossible() && isAnglePossible());
    } 

    @Override
    public void periodic() {
        log();
    }
}
