/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
//import frc.robot.Robot;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;

public class DriveClimberWinchToPosition extends CommandBase {
  double position;
  Joystick _joy = new Joystick(1);
  public DriveClimberWinchToPosition(double positionArg) {
    position = positionArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climber.goWinchToPosition(position,true);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.climber.driveWinch(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(_joy.getRawButton(Constants.X)|_joy.getRawButton(Constants.TRIANGLE)) {
      return true;
    }
    else if (RobotContainer.climber.getLWinchPosition() < -400 | RobotContainer.climber.getRWinchPosition() < -400) {
      return true; 
    }
    else {
      return false;
    }
  }
}
