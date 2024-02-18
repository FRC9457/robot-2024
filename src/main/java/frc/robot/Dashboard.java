// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveBaseSubsystem;

/** Add your docs here. */
public class Dashboard {
    private DriveBaseSubsystem drivebaseSubsystem;
    public GenericEntry rateLimitEntry;

    public Dashboard(DriveBaseSubsystem drivebaseSubsystem) {
        this.drivebaseSubsystem = drivebaseSubsystem;

    }

    public void shuffleboardSetup() {
        Shuffleboard.getTab("SmartDashboard").add("gyro", drivebaseSubsystem.gyro).withWidget(BuiltInWidgets.kGyro);
        rateLimitEntry = Shuffleboard.getTab("SmartDashboard").add("Ratelimit",2.0).withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min",0,"max", 10)).getEntry();
    }
}