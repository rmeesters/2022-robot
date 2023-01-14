/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Servo;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ClimberSubsystem extends SubsystemBase {
  Servo Rlock = new Servo(9);
  Servo Llock = new Servo(8);
  WPI_TalonFX fxLClimberWinch = new WPI_TalonFX(Constants.fxLClimberWinch);
  WPI_TalonFX fxRClimberWinch = new WPI_TalonFX(Constants.fxRClimberWinch);
  WPI_TalonFX fxClimberS2Claw = new WPI_TalonFX(Constants.fxClimberS2Claw);
  WPI_TalonFX fxClimberS2Pivot = new WPI_TalonFX(Constants.fxClimberS2Pivot);
  CANSparkMax maxClimberDrive = new CANSparkMax(Constants.maxClimberDrive, MotorType.kBrushless);

  public SparkMaxPIDController maxClimberDrivePID = maxClimberDrive.getPIDController();
  public RelativeEncoder maxClimberDriveEncoder = maxClimberDrive.getEncoder();
  // public SparkMaxPIDController maxClimberDrivePID = new SparkMaxPIDController(maxClimberDrive);
  // public RelativeEncoder maxClimberDriveEncoder = new RelativeEncoder(maxClimberDrive);
  public double P = .02;
  public double I = 0;
  public double D = 0;

  public int timeOutMs = 30;
  public int loopIDX = 0;
  public double p = .5;
  public double i = 0;
  public double d = 0;
  public ClimberSubsystem() {
    // rotater pid
    maxClimberDrive.setSmartCurrentLimit(50);
    maxClimberDrivePID.setFeedbackDevice(maxClimberDriveEncoder);
    maxClimberDrivePID.setP(P);
    maxClimberDrivePID.setI(I);
    maxClimberDrivePID.setD(D);
    maxClimberDrivePID.setOutputRange(-1, 1);

    //Climber Defaults 
    fxLClimberWinch.configFactoryDefault();
    fxRClimberWinch.configFactoryDefault();
    fxClimberS2Pivot.configFactoryDefault();
    fxClimberS2Claw.configFactoryDefault();
    fxLClimberWinch.setSelectedSensorPosition(0);
    fxRClimberWinch.setSelectedSensorPosition(0);    
    fxClimberS2Pivot.setSelectedSensorPosition(0);
    fxClimberS2Claw.setSelectedSensorPosition(0);
    fxLClimberWinch.setNeutralMode(NeutralMode.Brake);
    fxRClimberWinch.setNeutralMode(NeutralMode.Brake);
    fxClimberS2Pivot.setNeutralMode(NeutralMode.Brake);
    fxClimberS2Claw.setNeutralMode(NeutralMode.Brake);

    // config feedback sensor for PID
    fxLClimberWinch.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs Left Winch
    fxLClimberWinch.configNominalOutputForward(0, timeOutMs);
    fxLClimberWinch.configNominalOutputReverse(0, timeOutMs);
    fxLClimberWinch.configPeakOutputForward(.5, timeOutMs); // was .25
    fxLClimberWinch.configPeakOutputReverse(-.5, timeOutMs);
    fxLClimberWinch.setInverted(true);
    fxLClimberWinch.setSensorPhase(true);

    // Config peak and nominal outputs Right Winch
    fxRClimberWinch.configNominalOutputForward(0, timeOutMs);
    fxRClimberWinch.configNominalOutputReverse(0, timeOutMs);
    fxRClimberWinch.configPeakOutputForward(.5, timeOutMs); 
    fxRClimberWinch.configPeakOutputReverse(-.5, timeOutMs); 
    fxRClimberWinch.setInverted(true);
    fxRClimberWinch.setSensorPhase(true);

    // Config peak and nominal outputs Second Stage Claw
    fxClimberS2Claw.configNominalOutputForward(0, timeOutMs);
    fxClimberS2Claw.configNominalOutputReverse(0, timeOutMs);
    fxClimberS2Claw.configPeakOutputForward(.5, timeOutMs); // was .25
    fxClimberS2Claw.configPeakOutputReverse(-.5, timeOutMs);
    fxClimberS2Claw.setInverted(true);
    fxClimberS2Claw.setSensorPhase(true);

    // Config peak and nominal outputs Second Stage Pivot
    fxClimberS2Pivot.configNominalOutputForward(0, timeOutMs);
    fxClimberS2Pivot.configNominalOutputReverse(0, timeOutMs);
    fxClimberS2Pivot.configPeakOutputForward(.5, timeOutMs); // was .25
    fxClimberS2Pivot.configPeakOutputReverse(-.5, timeOutMs);
    fxClimberS2Pivot.setInverted(true);
    fxClimberS2Pivot.setSensorPhase(true);

    
    // config P,I,D,F values Left Winch
    fxLClimberWinch.config_kP(loopIDX, p, timeOutMs);
    fxLClimberWinch.config_kI(loopIDX, i, timeOutMs);
    fxLClimberWinch.config_kD(loopIDX, d, timeOutMs);

    // config P,I,D,F values Right Winch
    fxRClimberWinch.config_kP(loopIDX, p, timeOutMs);
    fxRClimberWinch.config_kI(loopIDX, i, timeOutMs);
    fxRClimberWinch.config_kD(loopIDX, d, timeOutMs);

    // config P,I,D,F values Second Stage Claw
    fxClimberS2Claw.config_kP(loopIDX, p, timeOutMs);
    fxClimberS2Claw.config_kI(loopIDX, i, timeOutMs);
    fxClimberS2Claw.config_kD(loopIDX, d, timeOutMs);

    // config P,I,D,F values Second Stage Pivot
    fxClimberS2Pivot.config_kP(loopIDX, p, timeOutMs);
    fxClimberS2Pivot.config_kI(loopIDX, i, timeOutMs);
    fxClimberS2Pivot.config_kD(loopIDX, d, timeOutMs);

    Llock.setBounds(2, 2, 1.5, 1, 1);
    Rlock.setBounds(2, 2, 1.5, 1, 1);


  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void driveWinch(double speed) {
   fxLClimberWinch.set(ControlMode.PercentOutput, -speed);
   fxRClimberWinch.set(ControlMode.PercentOutput, speed);
  }

  public void drivePivot(double speed) {
    fxClimberS2Pivot.set(ControlMode.PercentOutput, speed);
   }

  public void driveDrive(double speed) {
    maxClimberDrive.set(speed);
  }
  public void goWinchToPosition(double position,boolean on) {
    if(on) {
    fxLClimberWinch.set(ControlMode.Position, -position);
    fxRClimberWinch.set(ControlMode.Position, position);
    } 
    else{
      fxLClimberWinch.set(ControlMode.PercentOutput,0);
      fxRClimberWinch.set(ControlMode.PercentOutput,0);
    }
  }

  public void moveCamToPosition(double position,boolean on) {
    if(on) {
      fxClimberS2Claw.set(ControlMode.Position, position); //Change to Claw later
    } 
    else{
      fxClimberS2Claw.set(ControlMode.PercentOutput,0); // Change to Claw later
    }
  }

  public void setLServoAngle(double angle) {
    Llock.setAngle(angle);
  }
  public void setRServoAngle(double angle) {
    Rlock.setAngle(angle);
  }
  public double getLServoPosition() {
    return Llock.getAngle();
  }
  public double getRServoPosition() {
    return Llock.getAngle();
  }
  //public boolean getAlive() {
  //  return     lock.isAlive();
  // }
  public double getLWinchPosition() {
    return fxLClimberWinch.getSelectedSensorPosition();
  }
  public double getRWinchPosition() {
    return fxRClimberWinch.getSelectedSensorPosition();
  }
  public double getClawPosition() {
    return fxClimberS2Claw.getSelectedSensorPosition(); //Change to Claw later
  }

public void setEncoderPosition(double Encoder){

fxLClimberWinch.setSelectedSensorPosition(Encoder);
fxRClimberWinch.setSelectedSensorPosition(Encoder);
}

public void setClawEncoderPosition(double Encoder){

  fxClimberS2Claw.setSelectedSensorPosition(Encoder); //Change to Claw later
  }
}
