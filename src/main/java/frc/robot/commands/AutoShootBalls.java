/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.*;
import edu.wpi.first.wpilibj.Timer;
public class AutoShootBalls extends CommandBase {
  Timer timer = new Timer();
  double convayer1Speed;
  double convayer2Speed;
  public AutoShootBalls(double convayer1SpeedArg,double convayer2SpeedArg) {
    convayer1Speed = convayer1SpeedArg;
    convayer2Speed = convayer2SpeedArg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.shooter.spinToRPM(Constants.shooterRPM2);
    RobotContainer.convayerIntake.spinConvayer1(convayer1Speed);
    RobotContainer.convayerIntake.spinConvayer2(convayer2Speed);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    timer.reset();
    RobotContainer.shooter.setSpeed(0);
    RobotContainer.convayerIntake.spinConvayer1(0);
    RobotContainer.convayerIntake.spinConvayer2(0);

  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(timer.get() >= 2) {
    return true;
    }
    else {
      return false;
    }
  }
}
