// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;

public class BringClimberUp extends CommandBase {
  int position;
  Joystick _joy = new Joystick(0);
  public BringClimberUp(int positionArg) {
    this.position = positionArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    //RobotContainer.climber.setLServoAngle(0);
    //RobotContainer.climber.setRServoAngle(0);
    //RobotContainer.climber.goWinchToPosition(-10,true);
    RobotContainer.climber.goWinchToPosition(position,true);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //RobotContainer.climber.goWinchToPosition(position,false);
    RobotContainer.climber.driveWinch(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if (RobotContainer.climber.getLWinchPosition() < position+200 & RobotContainer.climber.getLWinchPosition() > position-200) {
      return true;
    }
    else {
      return false;
    }
  }
}