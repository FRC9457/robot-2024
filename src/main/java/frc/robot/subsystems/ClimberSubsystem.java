// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ClimberSubsystem extends SubsystemBase {
  public CANSparkMax leftClimberMotor;
  public CANSparkMax rightClimberMotor;
  private SimDeviceSim[] simDevices = new SimDeviceSim[2];

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    leftClimberMotor = new CANSparkMax(Constants.ClimberConstants.leftClimberMotor, MotorType.kBrushless);
    rightClimberMotor = new CANSparkMax(Constants.ClimberConstants.rightClimberMotor, MotorType.kBrushless);

    if (RobotBase.isSimulation()) {
      simulationInit();
    }
  }

  public void runClimber(double speed) {
    leftClimberMotor.set(speed);
    rightClimberMotor.set(speed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void simulationInit() {
    REVPhysicsSim physicsSim = REVPhysicsSim.getInstance();
    simDevices[0] = new SimDeviceSim("SPARK MAX [7]");
    simDevices[1] = new SimDeviceSim("SPARK MAX [9]");
    physicsSim.addSparkMax(leftClimberMotor, DCMotor.getNEO(1));
    physicsSim.addSparkMax(rightClimberMotor, DCMotor.getNEO(1));
  }

  @Override
  public void simulationPeriodic() {
    simDevices[0].getDouble("Velocity").set(leftClimberMotor.get());
    simDevices[1].getDouble("Velocity").set(rightClimberMotor.get());
  }
}
