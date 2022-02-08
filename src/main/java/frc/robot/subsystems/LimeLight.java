// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
     * @return the horizontal offset from crosshair to target (-27 to +27 degrees)
     */
    public double getHorizontalDegToTarget() {
        return table.getEntry("tx").getDouble(0);
    }

    /**
     * @return vertical offset from crosshair to target (-20.5 to +20.5 degrees)
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

    private void resetPipelineLatency() {
        table.getEntry("tl").setValue(0.0);
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

    /** The log method puts interesting information to the SmartDashboard. */
    public void log() {
        SmartDashboard.putNumber("LimelightX", getHorizontalDegToTarget());
        SmartDashboard.putNumber("LimelightY", getVerticalDegToTarget());
        SmartDashboard.putNumber("LimelightArea", getTargetArea());
    } 

    @Override
    public void periodic() {
        log();
    }

}
