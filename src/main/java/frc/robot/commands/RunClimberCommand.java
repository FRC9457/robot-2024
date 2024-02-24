// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class RunClimberCommand extends Command {
  /** Creates a new RunClimberCommand. */

  private ClimberSubsystem climberSubsystem;
  private DoubleSupplier speedSupplier;

  public RunClimberCommand(ClimberSubsystem climberSubsystem, DoubleSupplier speedSupplier) {
    this.climberSubsystem = climberSubsystem;
    this.speedSupplier = speedSupplier;
    addRequirements(climberSubsystem);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    climberSubsystem.runClimber(speedSupplier.getAsDouble());
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
