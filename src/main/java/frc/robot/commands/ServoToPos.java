// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;


public class ServoToPos extends CommandBase {
  /** Creates a new ServoToPos. */
  int position;
  public ServoToPos(int positionArg) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.position = positionArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climber.setLServoAngle(position);
    RobotContainer.climber.setRServoAngle(position);
  }
  
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(RobotContainer.climber.getLServoPosition() > position - 0.5 && RobotContainer.climber.getLServoPosition() < position + 0.5 
    && RobotContainer.climber.getRServoPosition() > position - 0.5 && RobotContainer.climber.getRServoPosition() < position + 0.5)
    {
      return true;
    }
    else {
      return false;
    }
  }
}
