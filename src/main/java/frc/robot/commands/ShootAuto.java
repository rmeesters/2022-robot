/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootAuto extends SequentialCommandGroup {
  /**
   * Creates a new DriveFowardAndShootAuto.
   */
  public ShootAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new AutoSpinShooter(Constants.shooterRPMAuto), new AutoShootBalls(Constants.convayer1Speed,Constants.convayer2Speed)/*,
      new AutoDriveSwerve(-1, 0, 0, -100000,.3)*/); // Need to adjust for what we need to do. 
  }
}

