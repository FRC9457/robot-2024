// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.RunClimberCommand;
import frc.robot.commands.RunShooterCommand;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveBaseSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

import java.util.EnumSet;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEvent;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public final DriveBaseSubsystem driveSubsystem = new DriveBaseSubsystem();
  public final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController = new CommandXboxController(
      OperatorConstants.kDriverControllerPort);
      
  public final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  private final Dashboard dashboard = new Dashboard(driveSubsystem);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the trigger bindings
    dashboard.shuffleboardSetup();
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@linkyyyyyyyyw
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is
    // pressed,
    // cancelling on release.
    DriveCommand driveCommand = new DriveCommand(driveSubsystem,
        m_driverController::getLeftY,
        m_driverController::getRightX); 

    NetworkTableInstance.getDefault()
        .addListener(
            dashboard.rateLimitEntry,
            EnumSet.of(NetworkTableEvent.Kind.kValueAll),
            driveCommand::updateRateLimiter);
    driveSubsystem.setDefaultCommand(
        driveCommand);

    shooterSubsystem.setDefaultCommand(new RunShooterCommand(shooterSubsystem, () -> 0));
    climberSubsystem.setDefaultCommand(new RunClimberCommand(climberSubsystem, () -> 0));
    m_driverController.rightTrigger().whileTrue(new RunClimberCommand(climberSubsystem, () -> 0.1));
    m_driverController.leftTrigger().whileTrue(new RunClimberCommand(climberSubsystem, () -> -0.1));
    m_driverController.b()
        .whileTrue(new RunShooterCommand(shooterSubsystem, () -> dashboard.shooterIntakeSpeedEntry.getDouble(-0.5)));
    m_driverController.x()
        .whileTrue(new RunShooterCommand(shooterSubsystem, () -> dashboard.shooterAmpSpeedEntry.getDouble(0.5)));
    m_driverController.a()
        .whileTrue(new RunShooterCommand(shooterSubsystem, () -> dashboard.shooterSpeakerSpeedEntry.getDouble(1)));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.exampleAuto(driveSubsystem);
  }
}
