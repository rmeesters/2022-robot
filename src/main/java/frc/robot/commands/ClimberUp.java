// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class ClimberUp extends SequentialCommandGroup {
  /** Creates a new ClimberUp. */
  int position;
  public ClimberUp(int positionArg) {
    this.position = positionArg;
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(new ServoToPos(0), new DriveClimberWinchToPosition(-500), new BringClimberUp(position));
  }
  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    //RobotContainer.climber.goWinchToPosition(position,false);
    RobotContainer.climber.driveWinch(0);
    //RobotContainer.climber.setLServoAngle(95);
    //RobotContainer.climber.setRServoAngle(105);
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

   
    if (RobotContainer.climber.getLWinchPosition() > position-2000 | RobotContainer.climber.getRWinchPosition() > position-2000) {
      return true;
    }
    else {
      return false;
    }
  }
}