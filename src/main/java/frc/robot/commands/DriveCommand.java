// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveBaseSubsystem;

public class DriveCommand extends Command {
  private DriveBaseSubsystem driveBaseSubsystem;
  private DoubleSupplier speedSupplier;
  private DoubleSupplier rotationSupplier;

  /** Creates a new DriveCommand. */
  public DriveCommand(DriveBaseSubsystem driveBaseSubsystem, DoubleSupplier speedSupplier,
      DoubleSupplier rotationSupplier) {
    this.driveBaseSubsystem = driveBaseSubsystem;
    this.speedSupplier = speedSupplier;
    this.rotationSupplier = rotationSupplier;
    addRequirements(driveBaseSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveBaseSubsystem.drive(speedSupplier.getAsDouble(), rotationSupplier.getAsDouble());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
