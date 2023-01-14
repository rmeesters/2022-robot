/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
//import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;

public class UnlockClaw extends CommandBase {
  int position;
  Joystick _joy = new Joystick(0);

  public UnlockClaw(int positionArg) {
    this.position = positionArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climber.moveCamToPosition(position,true);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.climber.moveCamToPosition(0,true);
    //RobotContainer.climber.driveWinch(0);
    //new SetClimberEncoderPosition(0);
    
  }

   // Returns true when the command should end.
   @Override
   public boolean isFinished() {
     return false;
   }
 
}