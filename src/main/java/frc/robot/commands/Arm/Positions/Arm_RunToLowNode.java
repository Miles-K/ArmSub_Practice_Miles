// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.Arm.Positions;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

public class Arm_RunToLowNode extends CommandBase {
  /** Creates a new Arm_RunToHighNode. */
  private ArmSubsystem armSubsystem;
  private boolean nodeSwitch;
  public Arm_RunToLowNode(ArmSubsystem armSubsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.armSubsystem = armSubsystem;
    addRequirements(armSubsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    armSubsystem.Arm_ExtendBase();
    nodeSwitch = armSubsystem.getNodeSwitch();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(nodeSwitch) {
      armSubsystem.Arm_RunToPosition(Constants.ARM.LOW_CUBE_POSITION);
    } else {
      armSubsystem.Arm_RunToPosition(Constants.ARM.LOW_CONE_POSITION);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    armSubsystem.Arm_Stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}