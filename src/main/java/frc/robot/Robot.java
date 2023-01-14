/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.*;
// import frc.robot.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  Joystick _joy = new Joystick(0);

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    RobotContainer.climber.setRServoAngle(105);
    RobotContainer.climber.setLServoAngle(95);
    new SetClimberEncoderPosition(0);
    new SetClawEncoderPosition(0);
    CameraServer.startAutomaticCapture();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // SmartDashboard.putBoolean("servo alive", RobotContainer.climber.getAlive());    
    SmartDashboard.putNumber("Left climber pos", RobotContainer.climber.getLWinchPosition());
    SmartDashboard.putNumber("Right climber pos", RobotContainer.climber.getRWinchPosition());
    SmartDashboard.putNumber("adjuster pos", RobotContainer.shooter.getAdjusterPosition());
    SmartDashboard.putNumber("Claw Encoder Position", RobotContainer.climber.getClawPosition());
    CommandScheduler.getInstance().run();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    RobotContainer.shooter.adjust(Constants.adjusterHomePos);
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    SmartDashboard.putNumber("Shooter RPM", RobotContainer.shooter.currentRPM());

  }

  @Override
  public void teleopInit() {
   // RobotContainer.drive.driveNeeded(false);
    RobotContainer.shooter.adjust(Constants.adjusterHomePos);

    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {

    SmartDashboard.putNumber("Shooter RPM", RobotContainer.shooter.currentRPM());
    SmartDashboard.putNumber("Left servo Angle", RobotContainer.climber.getLServoPosition());
    SmartDashboard.putNumber("Right servo Angle", RobotContainer.climber.getRServoPosition());

  //  if(RobotContainer.drive.driveNeeded() == false) {
    RobotContainer.drive.driveWithJoystick(_joy,.5);
    }
//  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
