// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */

  // arm extension pistons
  private Solenoid armPistonLeft;
  private Solenoid armPistonRight;

  // grabber pistons
  private Solenoid grabberPistonLeft;
  private Solenoid grabberPistonRight;
  private Solenoid grabberPistonLatch;

  // arm maneuvering motors
  private CANSparkMax armMotorLeft;
  private CANSparkMax armMotorRight;

  // arm encoders
  private RelativeEncoder armEncoderLeft;
  private RelativeEncoder armEncoderRight;

  private DigitalInput holdSensor;

  //    arm position presets
  //
  // private double posFloor = 0;
  // private double posConveyor = 0;
  // private double posHP = 0;
  // private double posNode1 = 0;
  // private double posNode2 = 0;
  // private double posNode3 = 0;

  private SparkMaxPIDController PIDControllerArm;

  public ArmSubsystem() {


    // initialization
    // arm & grabber
    armPistonLeft = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.ARM_PISTON_LEFT);
    armPistonRight = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.ARM_PISTON_RIGHT);

    grabberPistonLeft = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.GRABBER_PISTON_LEFT);
    grabberPistonRight = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.GRABBER_PISTON_RIGHT);
    grabberPistonLatch = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.GRABBER_PISTON_LATCH);

    armMotorLeft = new CANSparkMax(Constants.CAN.ARM_MOTOR_LEFT, MotorType.kBrushless);
    armMotorRight = new CANSparkMax(Constants.CAN.ARM_MOTOR_RIGHT, MotorType.kBrushless);

    armEncoderLeft = armMotorLeft.getEncoder();
    armEncoderRight = armMotorRight.getEncoder();

    armMotorRight.follow(armMotorLeft, true);

    // sensor
    holdSensor = new DigitalInput(Constants.DIO.ITEM_SENSOR);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Telemetry - SmartDashboard

  }

  // arm stop
  public void Arm_Stop() {
    armMotorLeft.set(0.);
  }

  // arm extension
  public void Arm_Extend() {
    armPistonLeft.set(true);
    armPistonRight.set(true);
  }

  // arm retraction
  public void Arm_Retract() {
    armPistonLeft.set(false);
    armPistonRight.set(false);
  }

  // arm piston toggle
  // public void ArmToggle() {
  //   if(!armPistonLeft.get()) {
  //     ArmExtend();
  //   } else {
  //     ArmRetract();
  //   }
  // }

  // grabber retraction
  public void Grabber_Close() {
    grabberPistonLeft.set(true);
    grabberPistonRight.set(true);
  }

  // grabber extension
  public void Grabber_Open() {
    grabberPistonLeft.set(false);
    grabberPistonRight.set(false);
  }

  // grabber claw position toggle
  // public void Grabber_Toggle() {
  //   if(!grabberPistonLeft.get()) {
  //     GrabberClose();
  //   } else {
  //     GrabberOpen();
  //   }
  // }

  // grabber latch lift
  public void Grabber_Up() {
    if(!getHoldSensor()) {
      grabberPistonLatch.set(true);
    }
  }

  // grabber latch shut
  public void Grabber_Down() {
    if(getHoldSensor()) {
      grabberPistonLatch.set(false);
    }
  }

    // grabber claw position toggle
  // public void GrabberLatchToggle() {
  //   if(!grabberPistonLatch.get()) {
  //     GrabberUp();
  //   } else {
  //     GrabberDown();
  //   }
  // }

  // arm joystick input
  public void Arm_Joystick(double speed) {
    armMotorLeft.set(speed);
  }

  // arm position to floor
  public void Arm_GoToFloorPosition() {
    Arm_Extend();
    Grabber_Open();
    // pid to floor position
  }

  //arm position to conveyor
  public void Arm_GoToConveyorPosition() {
    Arm_Retract();
    Grabber_Open();
    // pid to conveyor position
  }

  //arm position to HP Shelf
  public void Arm_GoToShelfPosition() {
    Arm_Retract();
    Grabber_Open();
    Grabber_Up();
    // pid to shelf position
  }

  //arm position to Low Node
  public void Arm_GoToLowNode() {
    Arm_Extend();
    // pid to Low Node position
  }

  //arm position to Medium Node
  public void Arm_GoToMediumNode() {
    Arm_Extend();
    // pid to Medium Node position
  }

  //arm position to High Node
  public void Arm_GoToHighNode() {
    Arm_Extend();
    // pid to High Node position
  }

  public boolean getHoldSensor() {
    return holdSensor.get();
  }
}
