// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class ArmSubsystem extends SubsystemBase {
  /** Creates a new ArmSubsystem. */

  // arm extension pistons
  private Solenoid armPistonLeft = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.ARM_PISTON_LEFT);
  private Solenoid armPistonRight = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.ARM_PISTON_RIGHT);

  // grabber pistons
  private Solenoid grabberPistonLeft = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.GRABBER_PISTON_LEFT);
  private Solenoid grabberPistonRight = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.GRABBER_PISTON_RIGHT);
  private Solenoid grabberPistonLatch = new Solenoid(PneumaticsModuleType.REVPH, Constants.PCM.GRABBER_PISTON_LATCH);

  // arm maneuvering motors
  private CANSparkMax leftArmMotor = new CANSparkMax(Constants.CAN.ARM_MOTOR_LEFT, MotorType.kBrushless);
  private CANSparkMax rightArmMotor = new CANSparkMax(Constants.CAN.ARM_MOTOR_RIGHT, MotorType.kBrushless);

  // arm encoders
  private RelativeEncoder armEncoderLeft = leftArmMotor.getEncoder();
  private RelativeEncoder armEncoderRight = rightArmMotor.getEncoder();

  // sensors
  private DigitalInput holdSensor = new DigitalInput(Constants.DIO.HOLD_SENSOR);
  private DigitalInput armLimitSwitch = new DigitalInput(Constants.DIO.LIMIT_SWITCH);

  //    arm position presets
  //
  private double armSetpoint = 0;
  // private double nodeAdjust = 50;
  // private double posFloor = 0;
  // private double posConveyor = 0;
  // private double posHP = 0;
  // private double posNode1 = 0;
  // private double posNode2 = 0;
  // private double posNode3 = 0;

  private boolean nodeSwitch = false;

  private SparkMaxPIDController pidControllerArm = leftArmMotor.getPIDController();

  public ArmSubsystem() {

    leftArmMotor.setIdleMode(IdleMode.kBrake);
    rightArmMotor.setIdleMode(IdleMode.kBrake);
    rightArmMotor.follow(leftArmMotor, true);

    pidControllerArm.setP(Constants.ARM.KP);
    pidControllerArm.setI(Constants.ARM.KI);
    pidControllerArm.setD(Constants.ARM.KD);
    pidControllerArm.setFF(Constants.ARM.KFF);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    // Telemetry - SmartDashboard
    // P
    // I
    // D
    // FF
    // arm pos state
    // arm pos encoder
    // grabber closed/open
    // grabber up/down
    // arm retracted/extended
  }

  // arm stop
  public void Arm_Stop() {
    leftArmMotor.set(0.);
  }

  // arm extension
  public void Arm_ExtendBase() {
    armPistonLeft.set(true);
    armPistonRight.set(true);
  }

  // arm retraction
  public void Arm_RetractBase() {
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
    leftArmMotor.set(speed);
  }

  // arm to position
  public void Arm_RunToPosition(double armSetpoint) {
    this.armSetpoint = armSetpoint;
    pidControllerArm.setReference(armSetpoint, ControlType.kPosition);
    // pid to position
  }

  public void Arm_NodeSwitch() {
    nodeSwitch = true;
  }

  public void Arm_NodeNoSwitch() {
    nodeSwitch = false;
  }

  public boolean getNodeSwitch() {
    return nodeSwitch;
  }

  public boolean getHoldSensor() {
    return holdSensor.get();
  }
}
