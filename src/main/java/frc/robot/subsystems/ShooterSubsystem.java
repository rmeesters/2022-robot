/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.*;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class ShooterSubsystem extends SubsystemBase {
  // motor controllers
  WPI_TalonFX fxShooter = new WPI_TalonFX(Constants.fxShooter);
  CANSparkMax maxAdjuster = new CANSparkMax(Constants.maxAdjuster, MotorType.kBrushless);
  
  public SparkMaxPIDController maxAdjusterPID = maxAdjuster.getPIDController();
  public RelativeEncoder maxAdjusterEncoder = maxAdjuster.getEncoder();
  // public SparkMaxPIDController maxAdjusterPID = new SparkMaxPIDController(maxAdjuster);
  // public RelativeEncoder maxAdjusterEncoder = new RelativeEncoder(maxAdjuster);
  // variables TODO tune via instrustions at:
  // https://www.chiefdelphi.com/t/talon-srx-velocity-control/148337/3
  public int timeOutMs = 30;
  public int loopIDX = 0;
  public double f = .053; // tune these values
  public double p = .3; // p value is the measurement of motor aggresiveness 
  public double i = 0;
  public double d = 0;
  public double sparkP = .1;
  public double sparkI = 0;
  public double sparkD = 0;
  public double sparkRamp = 0;

  public ShooterSubsystem() {
    //adjuster pid
    maxAdjusterPID.setFeedbackDevice(maxAdjusterEncoder);
    maxAdjusterPID.setP(sparkP);
    maxAdjusterPID.setI(sparkI);
    maxAdjusterPID.setD(sparkD);
    maxAdjusterPID.setOutputRange(-.4, .4);
    maxAdjuster.setClosedLoopRampRate(sparkRamp);

    // reset to factory default
    fxShooter.configFactoryDefault();

    // config feedback sensor for PID
    fxShooter.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxShooter.configNominalOutputForward(0, timeOutMs);
    fxShooter.configNominalOutputReverse(0, timeOutMs);
    fxShooter.configPeakOutputForward(1, timeOutMs);
    fxShooter.configPeakOutputReverse(-1, timeOutMs);
    fxShooter.setInverted(true);
    fxShooter.setSensorPhase(true);
    // config P,I,D,F values
    fxShooter.config_kF(loopIDX, f, timeOutMs);
    fxShooter.config_kP(loopIDX, p, timeOutMs);
    fxShooter.config_kI(loopIDX, i, timeOutMs);
    fxShooter.config_kD(loopIDX, d, timeOutMs);

  }

  public void spinToRPM(int targetRPM) {
    // sets shooter to target rpm
    fxShooter.set(ControlMode.Velocity, targetRPM / .292);
  }

  public void setSpeed(double speed) {
    fxShooter.set(ControlMode.PercentOutput, speed);
  }
  
  public double currentRPM() {
    // current rpm = rpm of motor x gear ratio
    double motorRPM = fxShooter.getSelectedSensorVelocity() * .292;
    double gearRatio = 1;
    return motorRPM * gearRatio;
  }
  public void adjust(double position) {    
   maxAdjusterPID.setReference(position, CANSparkMax.ControlType.kPosition);
  }
  
  public double getAdjusterPosition() {
    return maxAdjusterEncoder.getPosition();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
