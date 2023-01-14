package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ServoUnlock extends CommandBase {

  int position;
  Joystick _joy = new Joystick(0);

  public ServoUnlock() {
    
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotContainer.climber.setRServoAngle(0);
    RobotContainer.climber.setLServoAngle(0);  
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotContainer.climber.setRServoAngle(0);
    RobotContainer.climber.setLServoAngle(0);

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotContainer.climber.setRServoAngle(0);
    RobotContainer.climber.setLServoAngle(0);
    SmartDashboard.putNumber("Servo Position", RobotContainer.climber.getLServoPosition());

    }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    if(_joy.getRawButton(Constants.L1)|_joy.getRawButton(Constants.X)|_joy.getRawButton(Constants.SQUARE)) {
      return true;
    }
      else {
        return false;
    }
  }
}