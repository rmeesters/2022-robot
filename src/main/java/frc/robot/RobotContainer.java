/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.*;
import frc.robot.subsystems.*;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  public static ShooterSubsystem shooter = new ShooterSubsystem();
  public static ConvayerIntakeSubsystem convayerIntake = new ConvayerIntakeSubsystem();
  // public static RotaterSubsystem rotater = new RotaterSubsystem();
  public static ClimberSubsystem climber = new ClimberSubsystem();
  public static DriveSubsystem drive = new DriveSubsystem();
  // public static VisionSubsystem vision = new VisionSubsystem();
    

  private final Command simpleShootAuto = new ShootAuto();

  SendableChooser<Command> chooser = new SendableChooser<>();
  // joysticks
  Joystick _joy = new Joystick(0);
  Joystick _joy2 = new Joystick(1);
  // joystick buttons

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
      chooser.setDefaultOption("Simple Shoot", simpleShootAuto);
      chooser.addOption("Simple Shoot", simpleShootAuto);
      SmartDashboard.putData("autos",chooser);
      Shuffleboard.getTab("Autonomous").add(chooser);
    // Configure the button   x bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    //Zeros Encoder
    new JoystickButton(_joy, Constants.SQUARE).whenPressed(new SetClimberEncoderPosition(0));
    
    // hold to drive convayer and shooter to shoot balls when done will drop the
    // adjuster back to 0
    new JoystickButton(_joy, Constants.R2).whileHeld(new ShootBalls(Constants.convayer1Speed,Constants.convayer2Speed*0.5));

    new JoystickButton(_joy, Constants.X).whileHeld(new ShootBalls2(Constants.convayer1Speed,Constants.convayer2Speed*0.5));

     //intake button 
    new JoystickButton(_joy, Constants.L2).whileHeld(new Intake(Constants.convayer1Speed*.5,Constants.convayer2Speed*.5));
    
    //reverse intake
    new JoystickButton(_joy, Constants.L1).whileHeld(new ReverseIntake(-Constants.convayer1Speed,-Constants.convayer2Speed));

     //unwinds winch 
     new JoystickButton(_joy2, Constants.L2).whileHeld(new DriveClimberWinch(-0.5,false));
     
     //winds up winch
     new JoystickButton(_joy2, Constants.L1).whileHeld(new DriveClimberWinch(0.5,true));

    //bring winch to bottom - temporarily disabled
    new JoystickButton(_joy, Constants.R1).whenReleased(new BringClimberDown(5000));

     //bring winch to top - temporarily disabled
     new JoystickButton(_joy, Constants.R1).whileHeld(new ClimberUp(140000));
     //new JoystickButton(_joy, Constants)

      // ServoLock
      new JoystickButton(_joy, Constants.OPT).whileHeld(new ServoTest());

      // ServoUnlock
      new JoystickButton(_joy, Constants.SHARE).whileHeld(new ServoUnlock());

      // ServoLock
      new JoystickButton(_joy2, Constants.OPT).whileHeld(new ServoTest());

      // ServoUnlock
      new JoystickButton(_joy2, Constants.SHARE).whileHeld(new ServoUnlock());

      //Drive Pivot Forward
      new JoystickButton(_joy2, Constants.R1).whileHeld(new DrivePivot(0.25));

      //Drive Pivot Backwards
      new JoystickButton(_joy2, Constants.R2).whileHeld(new DrivePivot(-0.25));

      //Unlock Claw
      new JoystickButton(_joy2, Constants.SQUARE).whileHeld(new UnlockClaw(5500));

      

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
   // return chooser.getSelected();
   return simpleShootAuto;
  }
}
