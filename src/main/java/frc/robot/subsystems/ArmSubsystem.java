// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
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

  // arm motors motor cotroller group
  private MotorControllerGroup armMotorGroup;

  private DigitalInput itemSensor;

  // arm position presets
  private double posFloor = 0;
  private double posConveyor = 0;
  private double posHP = 0;
  private double posNode1 = 0;
  private double posNode2 = 0;
  private double posNode3 = 0;

  // adjustment # between cone nodes and cube nodes
  private double nodeVar;

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

    armMotorGroup = new MotorControllerGroup(armMotorLeft, armMotorRight);
    armMotorRight.follow(armMotorLeft, true);

    // sensor
    itemSensor = new DigitalInput(Constants.DIO.ITEM_SENSOR);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  // arm run at low speed
  public void ArmRun() {
    armMotorGroup.set(0.1);
  }

  // arm stop
  public void ArmStop() {
    armMotorGroup.set(0.);
  }

  // arm extension
  public void ArmExtend() {
    armPistonLeft.set(true);
    armPistonRight.set(true);
  }

  // arm retraction
  public void ArmRetract() {
    armPistonLeft.set(false);
    armPistonRight.set(false);
  }

  // grabber retraction
  public void GrabberClose() {
    grabberPistonLeft.set(true);
    grabberPistonRight.set(true);
  }

  // grabber extension
  public void GrabberOpen() {
    grabberPistonLeft.set(false);
    grabberPistonRight.set(false);
  }

  // grabber latch lift
  public void GrabberLift() {
    if(!getItemDIO()) {
      grabberPistonLatch.set(true);
    }
  }

  // grabber latch shut
  public void GrabberShut() {
    if(getItemDIO()) {
      grabberPistonLatch.set(false);
    }
  }

  // arm joystick input
  public void ArmJoystick(double speed) {
    armMotorGroup.set(speed);
  }

  // arm position to floor
  public void ArmPosFloor() {
    ArmExtend();
    GrabberOpen();
    if(Math.abs(armEncoderLeft.getPosition() - posFloor) > 50) {
      if(armEncoderLeft.getPosition() > 0) {
        armMotorGroup.set(-0.3);
      } else if(armEncoderLeft.getPosition() < 0) {
        armMotorGroup.set(0.3);
      }
    }
  }

  public void ArmPosConveyor() {
    ArmRetract();
    GrabberOpen();
    if(Math.abs(armEncoderLeft.getPosition() - posConveyor) > 50) {
      if(armEncoderLeft.getPosition() > 0) {
        armMotorGroup.set(-0.3);
      } else if(armEncoderLeft.getPosition() < 0) {
        armMotorGroup.set(0.3);
      }
    }
  }

  public void ArmPosHumanPlayer() {
    ArmRetract();
    GrabberOpen();
    GrabberLift();
    if(Math.abs(armEncoderLeft.getPosition() - posHP) > 50) {
      if(armEncoderLeft.getPosition() > 0) {
        armMotorGroup.set(-0.3);
      } else if(armEncoderLeft.getPosition() < 0) {
        armMotorGroup.set(0.3);
      }
    }
  }

  public void ArmPosNode1() {
    ArmExtend();
    if(Math.abs(armEncoderLeft.getPosition() - posNode1 + nodeVar) > 50) {
      if(armEncoderLeft.getPosition() > 0) {
        armMotorGroup.set(-0.3);
      } else if(armEncoderLeft.getPosition() < 0) {
        armMotorGroup.set(0.3);
      }
    }
  }

  public void ArmPosNode2() {
    ArmExtend();
    if(Math.abs(armEncoderLeft.getPosition() - posNode2 + nodeVar) > 50) {
      if(armEncoderLeft.getPosition() > 0) {
        armMotorGroup.set(-0.3);
      } else if(armEncoderLeft.getPosition() < 0) {
        armMotorGroup.set(0.3);
      }
    }
  }

  public void ArmPosNode3() {
    ArmExtend();
    if(Math.abs(armEncoderLeft.getPosition() - posNode3 + nodeVar) > 50) {
      if(armEncoderLeft.getPosition() > 0) {
        armMotorGroup.set(-0.3);
      } else if(armEncoderLeft.getPosition() < 0) {
        armMotorGroup.set(0.3);
      }
    }
  }

  public void nodeHold() {
    nodeVar = 50;
  }

  public void nodeRelease() {
    nodeVar = 0;
  }

  public boolean getItemDIO() {
    if(itemSensor.get()) {
      return true;
    } else {
      return false;
    }
  }
}
