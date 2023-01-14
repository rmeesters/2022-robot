/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;

public class ReverseIntake extends CommandBase {
  double speed;
  double speed2;
double position;
  public ReverseIntake(double speedArg, double speed2Arg) {
    speed = speedArg;
    speed2  = speed2Arg;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.convayerIntake.stopHold(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
      RobotContainer.convayerIntake.spinConvayer2(speed2);
      RobotContainer.convayerIntake.spinConvayer1(speed);
      //RobotContainer.convayerIntake.spinIntake(speed);
    }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    /*SmartDashboard.putNumber("hiog", 89);
    position = RobotContainer.shooter.getAdjusterPosition();
    RobotContainer.convayerIntake.startHold();
    RobotContainer.convayerIntake.holdPosition(position);*/
    RobotContainer.convayerIntake.spinAll(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
