// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ShooterSubsystem extends SubsystemBase {
  public CANSparkMax MotorA;
  public CANSparkMax MotorB;
  private SimDeviceSim[] simDevices = new SimDeviceSim[2];

  /** Creates a new ShooterSubsystem. */
  public ShooterSubsystem() {
    MotorA = new CANSparkMax(Constants.ShooterConstants.MotorA, MotorType.kBrushless);
    MotorB = new CANSparkMax(Constants.ShooterConstants.MotorB, MotorType.kBrushless);
    MotorA.follow(MotorB);
    if (RobotBase.isSimulation()) {
      simulationInit();
    }

  }

  public void runshooter(double speed) {
    MotorB.set(speed);
  }

  private void simulationInit() {
    REVPhysicsSim physicsSim = REVPhysicsSim.getInstance();
    simDevices[0] = new SimDeviceSim("SPARK MAX [5]");
    simDevices[1] = new SimDeviceSim("SPARK MAX [6]");
    physicsSim.addSparkMax(MotorA, DCMotor.getNEO(1));
    physicsSim.addSparkMax(MotorB, DCMotor.getNEO(1));

  }

  @Override
  public void simulationPeriodic() {
    simDevices[0].getDouble("Velocity").set(MotorB.get());
    simDevices[1].getDouble("Velocity").set(MotorB.get());
  }

}
