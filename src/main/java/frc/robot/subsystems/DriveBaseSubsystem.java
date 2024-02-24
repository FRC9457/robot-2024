// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.simulation.SimDeviceSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveBaseSubsystem extends SubsystemBase {
    public CANSparkMax leftMotorA;
    public CANSparkMax leftMotorB;
    public CANSparkMax rightMotorA;
    public CANSparkMax rightMotorB;
    DifferentialDrive robotDrive;
    private SimDeviceSim[] simDevices = new SimDeviceSim[4];
    public AHRS gyro = new AHRS(SPI.Port.kMXP);

    public DriveBaseSubsystem() {
        if (RobotBase.isSimulation()) {
            leftMotorA = new CANSparkMax(Constants.DriveBaseConstants.LeftMotorA, MotorType.kBrushless);
            leftMotorB = new CANSparkMax(Constants.DriveBaseConstants.LeftMotorB, MotorType.kBrushless);
            rightMotorA = new CANSparkMax(Constants.DriveBaseConstants.RightMotorA, MotorType.kBrushless);
            rightMotorB = new CANSparkMax(Constants.DriveBaseConstants.RightMotorB, MotorType.kBrushless);
            simulationInit();
        } else {
            leftMotorA = new CANSparkMax(Constants.DriveBaseConstants.LeftMotorA, MotorType.kBrushed);
            leftMotorB = new CANSparkMax(Constants.DriveBaseConstants.LeftMotorB, MotorType.kBrushed);
            rightMotorA = new CANSparkMax(Constants.DriveBaseConstants.RightMotorA, MotorType.kBrushed);
            rightMotorB = new CANSparkMax(Constants.DriveBaseConstants.RightMotorB, MotorType.kBrushed);
        }
        rightMotorB.setInverted(true);
        rightMotorB.burnFlash();
        rightMotorA.setInverted(true);
        rightMotorA.burnFlash();
        leftMotorA.follow(leftMotorB);
        rightMotorA.follow(rightMotorB);
        robotDrive = new DifferentialDrive(leftMotorB, rightMotorB);
    }

    public void drive(double speed, double rotation) {
        robotDrive.arcadeDrive(speed, rotation);
    }

    private void simulationInit() {
        REVPhysicsSim physicsSim = REVPhysicsSim.getInstance();
        simDevices[0] = new SimDeviceSim("SPARK MAX [8]");
        simDevices[1] = new SimDeviceSim("SPARK MAX [2]");
        simDevices[2] = new SimDeviceSim("SPARK MAX [3]");
        simDevices[3] = new SimDeviceSim("SPARK MAX [4]");
        physicsSim.addSparkMax(leftMotorA, DCMotor.getNEO(1));
        physicsSim.addSparkMax(leftMotorB, DCMotor.getNEO(1));
        physicsSim.addSparkMax(rightMotorA, DCMotor.getNEO(1));
        physicsSim.addSparkMax(rightMotorB, DCMotor.getNEO(1));
    }

    @Override
    public void simulationPeriodic() {
        simDevices[0].getDouble("Velocity").set(leftMotorB.get());
        simDevices[1].getDouble("Velocity").set(leftMotorB.get());
        simDevices[2].getDouble("Velocity").set(-rightMotorB.get());
        simDevices[3].getDouble("Velocity").set(-rightMotorB.get());
    }

}
