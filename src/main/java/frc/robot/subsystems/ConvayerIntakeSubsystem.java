/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.Rev2mDistanceSensor.*;

import frc.robot.Constants;
import com.revrobotics.*;
import com.revrobotics.Rev2mDistanceSensor;
// import com.ctre.phoenix.motorcontrol.ControlMode;


public class ConvayerIntakeSubsystem extends SubsystemBase {
  //CANSparkMax maxIntake = new CANSparkMax(Constants.maxIntake, MotorType.kBrushless);
  CANSparkMax maxConvayer1 = new CANSparkMax(Constants.maxConvayer1, MotorType.kBrushless);
  CANSparkMax maxConvayer2 = new CANSparkMax(Constants.maxConvayer2, MotorType.kBrushless);
  Rev2mDistanceSensor distanceSensor;
  
  public SparkMaxPIDController maxConvayer2PID = maxConvayer2.getPIDController();
  public RelativeEncoder maxConvayer2Encoder = maxConvayer2.getEncoder();
  // public SparkMaxPIDController maxConvayer2PID = new SparkMaxPIDController(maxConvayer2);
  // public RelativeEncoder manCanvayer2Encoder = new RelativeEncoder(maxConvayer2);
  public double sparkP = .1;
  public double sparkI = 0;
  public double sparkD = 0;
  public double sparkRamp = 0;
  boolean hold = false;
  public ConvayerIntakeSubsystem() {

    maxConvayer2PID.setFeedbackDevice(maxConvayer2Encoder);
    maxConvayer2PID.setP(sparkP);
    maxConvayer2PID.setI(sparkI);
    maxConvayer2PID.setD(sparkD);
    maxConvayer2PID.setOutputRange(-.4, .4);
    maxConvayer2.setClosedLoopRampRate(sparkRamp);
    maxConvayer1.setSmartCurrentLimit(30);
    maxConvayer2.setSmartCurrentLimit(30);
    distanceSensor = new Rev2mDistanceSensor(Port.kOnboard);
    distanceSensor.setAutomaticMode(true);
    distanceSensor.setEnabled(true);
    distanceSensor.setRangeProfile(RangeProfile.kHighSpeed);
    maxConvayer1.burnFlash();
    maxConvayer2.burnFlash();
  }

  public void spinAll(double speed) {
    //maxIntake.set(speed);
    maxConvayer1.set(speed);
    maxConvayer2.set(speed);
  }

  //public void spinIntake(double speed) {
    //maxIntake.set(speed);
  //}

  public void spinConvayer1(double speed) {
    maxConvayer1.set(speed);
  }

  public void spinConvayer2(double speed) {
    maxConvayer2.set(speed);
  }
  public double getEncoderPosition() {
    return maxConvayer2Encoder.getPosition();
  }
  public void holdPotition(double position) {
    SmartDashboard.putNumber("3453242", 4326567);
    if(hold) {
    maxConvayer2PID.setReference(position, CANSparkMax.ControlType.kPosition);
    }
    else {
      maxConvayer2.set(0);
    }
  }
  public void stopHold() {
    hold = false;
  }
  public void startHold() {
    hold = true;
  }
  public boolean ballLoaded() {
    if (distanceSensor.isRangeValid() && distanceSensor.getRange() < 6) {
      return true;
    } else {
      return false;
    }
  }

  public void holdPosition(double position) {

  }

  public double getSensorRange() {
    if (distanceSensor.isRangeValid()) {
      return distanceSensor.getRange();
    } else {
      return 1;
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
