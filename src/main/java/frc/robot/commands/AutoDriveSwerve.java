/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class AutoDriveSwerve extends CommandBase {
  double FWD, STR, RCW, distance, speed;

  public AutoDriveSwerve(double FWDArg, double STRArg, double RCWArg, double distanceArg, double speedArg) {
    FWD = FWDArg;
    STR = STRArg;
    RCW = RCWArg;
    distance = distanceArg;
    speed = speedArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.drive.driveNeeded(true);
    RobotContainer.drive.zeroDrive();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.drive.driveWithValues(FWD, STR, RCW, distance, speed);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.drive.driveNeeded(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    
    if (RobotContainer.drive.getDriveEncoder() > -distance - 100 & RobotContainer.drive.getDriveEncoder() < -distance + 100) {
      return true;
    } else {
      return false;
    }
  }
}
