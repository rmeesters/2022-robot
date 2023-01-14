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

public class ShootBalls extends CommandBase {
  Timer timer = new Timer();
  double speed;
  double speed2;
  public ShootBalls(double speedArg, double speed2Arg) {
    speed = speedArg;
    speed2 = speed2Arg;
    addRequirements(RobotContainer.shooter);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.reset();
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.shooter.spinToRPM(Constants.shooterRPMHigh);
    if(RobotContainer.shooter.currentRPM() > Constants.shooterRPMHigh) {
    RobotContainer.convayerIntake.spinConvayer1(speed);
    RobotContainer.convayerIntake.spinConvayer2(speed2);
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    timer.stop();
    RobotContainer.shooter.setSpeed(0);
    RobotContainer.convayerIntake.spinConvayer1(0);
    RobotContainer.convayerIntake.spinConvayer2(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
