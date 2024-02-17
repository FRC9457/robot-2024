// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

/** Add your docs here. */
public class RunShooterCommand extends Command {
    private ShooterSubsystem shooterSubsystem;
    private DoubleSupplier speedSupplier;

    public RunShooterCommand(ShooterSubsystem shooterSubsystem, DoubleSupplier speedSupplier) {
        this.shooterSubsystem = shooterSubsystem;
        this.speedSupplier = speedSupplier;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {
        shooterSubsystem.runshooter(speedSupplier.getAsDouble());

    }
}
