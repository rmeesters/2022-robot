/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.*;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;


public class DriveSubsystem extends SubsystemBase {
  /**
   * Creates a new DriveSubsystem.
   */

  AHRS ahrs;
  // Joystick _bakcupJoy = new Joystick(1);
  double radians, angle, temp, A2, B2, R, A, B, C, D, ws1, ws2, ws3, ws4, wa1, wa2, wa3, wa4, max, currentAngle,
      rotationAmmount, FWD, STR, RCW, currentAngle2, currentAngle3, currentAngle4, rotationAmmount2, rotationAmmount3,
      rotationAmmount4, offsetangle, disfromtarget, P, I, D2, angleConstant,driveEncoderRelitivePosition; // defining variables
  boolean needed;
  // defining motor controlers and encoders
  WPI_TalonFX fxFLdrive = new WPI_TalonFX(Constants.fxFLdrive);
  WPI_TalonFX fxFRdrive = new WPI_TalonFX(Constants.fxFRdrive);
  WPI_TalonFX fxBLdrive = new WPI_TalonFX(Constants.fxBLdrive);
  WPI_TalonFX fxBRdrive = new WPI_TalonFX(Constants.fxBRdrive);
  WPI_TalonFX fxFLswerve = new WPI_TalonFX(Constants.fxFLswerve);
  WPI_TalonFX fxFRswerve = new WPI_TalonFX(Constants.fxFRswerve);
  WPI_TalonFX fxBLswerve = new WPI_TalonFX(Constants.fxBLswerve);
  WPI_TalonFX fxBRswerve = new WPI_TalonFX(Constants.fxBRswerve);

  // defining motor controlers and encoders

  public int timeOutMs = 30;
  public int loopIDX = 0;
  public double f = 0;
  public double pSwerve = .4; //was 1
  public double pDrive = 0.4; 
  public double i = .0;
  public double d = 0;

    public DriveSubsystem() {
    ahrs = new AHRS(SPI.Port.kMXP);

    fxBLswerve.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5));
    fxFLswerve.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5));
    fxBRswerve.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5));
    fxFRswerve.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 30, 35, 0.5)); 
    
    // Old Code
    // fxBLswerve.configPeakCurrentLimit(30, 30);
    // fxFLswerve.configPeakCurrentLimit(30, 30);
    // fxBRswerve.configPeakCurrentLimit(30, 30);
    // fxFRswerve.configPeakCurrentLimit(30, 30);

    fxBLdrive.setNeutralMode(NeutralMode.Brake);
    fxFLdrive.setNeutralMode(NeutralMode.Brake);
    fxBRdrive.setNeutralMode(NeutralMode.Brake);
    fxFRdrive.setNeutralMode(NeutralMode.Brake);
    fxBLswerve.setNeutralMode(NeutralMode.Brake);
    fxFLswerve.setNeutralMode(NeutralMode.Brake);
    fxBRswerve.setNeutralMode(NeutralMode.Brake);
    fxFRswerve.setNeutralMode(NeutralMode.Brake);
    
    //BL Drive PID Config
    // reset to factory default
    fxBLdrive.configFactoryDefault();

    // config feedback sensor for PID
    fxBLdrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxBLdrive.configNominalOutputForward(0, timeOutMs);
    fxBLdrive.configNominalOutputReverse(0, timeOutMs);
    fxBLdrive.configPeakOutputForward(1, timeOutMs);
    fxBLdrive.configPeakOutputReverse(-1, timeOutMs);
    fxBLdrive.setInverted(false);
    fxBLdrive.setSensorPhase(false);
    // config P,I,D,F values
    fxBLdrive.config_kF(loopIDX, f, timeOutMs);
    fxBLdrive.config_kP(loopIDX, pDrive, timeOutMs);
    fxBLdrive.config_kI(loopIDX, 0, timeOutMs);
    fxBLdrive.config_kD(loopIDX, d, timeOutMs);


    //BR Drive PID Config
    // reset to factory default
    fxBRdrive.configFactoryDefault();

    // config feedback sensor for PID
    fxBRdrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxBRdrive.configNominalOutputForward(0, timeOutMs);
    fxBRdrive.configNominalOutputReverse(0, timeOutMs);
    fxBRdrive.configPeakOutputForward(1, timeOutMs);
    fxBRdrive.configPeakOutputReverse(-1, timeOutMs);
    fxBRdrive.setInverted(false);
    fxBRdrive.setSensorPhase(false);
    // config P,I,D,F values
    fxBRdrive.config_kF(loopIDX, f, timeOutMs);
    fxBRdrive.config_kP(loopIDX, pDrive, timeOutMs);
    fxBRdrive.config_kI(loopIDX, 0, timeOutMs);
    fxBRdrive.config_kD(loopIDX, d, timeOutMs);

    //FR Drive PID Config
    // reset to factory default
    fxFRdrive.configFactoryDefault();

    // config feedback sensor for PID
    fxFRdrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxFRdrive.configNominalOutputForward(0, timeOutMs);
    fxFRdrive.configNominalOutputReverse(0, timeOutMs);
    fxFRdrive.configPeakOutputForward(1, timeOutMs);
    fxFRdrive.configPeakOutputReverse(-1, timeOutMs);
    fxFRdrive.setInverted(false);
    fxFRdrive.setSensorPhase(false);
    // config P,I,D,F values
    fxFRdrive.config_kF(loopIDX, f, timeOutMs);
    fxFRdrive.config_kP(loopIDX, pDrive, timeOutMs);
    fxFRdrive.config_kI(loopIDX, 0, timeOutMs);
    fxFRdrive.config_kD(loopIDX, d, timeOutMs);
    
    //FL Drive PID Config
    // reset to factory default
    fxFLdrive.configFactoryDefault();

    // config feedback sensor for PID
    fxFLdrive.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxFLdrive.configNominalOutputForward(0, timeOutMs);
    fxFLdrive.configNominalOutputReverse(0, timeOutMs);
    fxFLdrive.configPeakOutputForward(1, timeOutMs);
    fxFLdrive.configPeakOutputReverse(-1, timeOutMs);
    fxFLdrive.setInverted(false);
    fxFLdrive.setSensorPhase(false);
    // config P,I,D,F values
    fxFLdrive.config_kF(loopIDX, f, timeOutMs);
    fxFLdrive.config_kP(loopIDX, pDrive, timeOutMs);
    fxFLdrive.config_kI(loopIDX, 0, timeOutMs);
    fxFLdrive.config_kD(loopIDX, d, timeOutMs);

    //BR Swerve PID Config
    // reset to factory default
    fxBRswerve.configFactoryDefault();

    // config feedback sensor for PID
    fxBRswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxBRswerve.configNominalOutputForward(0, timeOutMs);
    fxBRswerve.configNominalOutputReverse(0, timeOutMs);
    fxBRswerve.configPeakOutputForward(1, timeOutMs);
    fxBRswerve.configPeakOutputReverse(-1, timeOutMs);
    fxBRswerve.setInverted(false);
    fxBRswerve.setSensorPhase(false);
    // config P,I,D,F values
    fxBRswerve.config_kF(loopIDX, f, timeOutMs);
    fxBRswerve.config_kP(loopIDX, pSwerve, timeOutMs);
    fxBRswerve.config_kI(loopIDX, I, timeOutMs);
    fxBRswerve.config_kD(loopIDX, D2, timeOutMs);

    /*fxBRswerve.configPeakCurrentLimt(30);
    fxBRswerve.configPeakCurrentDuration(100);
    fxBRswerve.configContinuousCurrentLimit(20);
    fxBRswerve.enableCurrentLimit(true); */
    
    //BL Swerve PID Config
    // reset to factory default
    fxBLswerve.configFactoryDefault();

    // config feedback sensor for PID
    fxBLswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxBLswerve.configNominalOutputForward(0, timeOutMs);
    fxBLswerve.configNominalOutputReverse(0, timeOutMs);
    fxBLswerve.configPeakOutputForward(1, timeOutMs);
    fxBLswerve.configPeakOutputReverse(-1, timeOutMs);
    fxBLswerve.setInverted(false);
    fxBLswerve.setSensorPhase(false);
    // config P,I,D,F values
    fxBLswerve.config_kF(loopIDX, f, timeOutMs);
    fxBLswerve.config_kP(loopIDX, pSwerve, timeOutMs);
    fxBLswerve.config_kI(loopIDX, I, timeOutMs);
    fxBLswerve.config_kD(loopIDX, D2, timeOutMs);

    /*fxBLswerve.configPeakCurrentLimt(30);
    fxBLswerve.configPeakCurrentDuration(100);
    fxBLswerve.configContinuousCurrentLimit(20);
    fxBLswerve.enableCurrentLimit(true); */

    //FR Swerve PID Config
    // reset to factory default
    fxFRswerve.configFactoryDefault();

    // config feedback sensor for PID
    fxFRswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxFRswerve.configNominalOutputForward(0, timeOutMs);
    fxFRswerve.configNominalOutputReverse(0, timeOutMs);
    fxFRswerve.configPeakOutputForward(1, timeOutMs);
    fxFRswerve.configPeakOutputReverse(-1, timeOutMs);
    fxFRswerve.setInverted(false);
    fxFRswerve.setSensorPhase(false);
    // config P,I,D,F values
    fxFRswerve.config_kF(loopIDX, f, timeOutMs);
    fxFRswerve.config_kP(loopIDX, pSwerve, timeOutMs);
    fxFRswerve.config_kI(loopIDX, I, timeOutMs);
    fxFRswerve.config_kD(loopIDX, D2, timeOutMs);

   /* fxFRswerve.configPeakCurrentLimt(30);
    fxFRswerve.configPeakCurrentDuration(100);
    fxFRswerve.configContinuousCurrentLimit(20);
    fxFRswerve.enableCurrentLimit(true); */

    //FL Swerve PID Config
    // reset to factory default
    fxFLswerve.configFactoryDefault();

    // config feedback sensor for PID
    fxFLswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);

    // Config peak and nominal outputs
    fxFLswerve.configNominalOutputForward(0, timeOutMs);
    fxFLswerve.configNominalOutputReverse(0, timeOutMs);
    fxFLswerve.configPeakOutputForward(1, timeOutMs);
    fxFLswerve.configPeakOutputReverse(-1, timeOutMs);
    fxFLswerve.setInverted(false);
    fxFLswerve.setSensorPhase(false);
    // config P,I,D,F values
    fxFLswerve.config_kF(loopIDX, f, timeOutMs);
    fxFLswerve.config_kP(loopIDX, pSwerve, timeOutMs);
    fxFLswerve.config_kI(loopIDX, I, timeOutMs);
    fxFLswerve.config_kD(loopIDX, D2, timeOutMs);

   /* fxFLswerve.configPeakCurrentLimt(30);
    fxFLswerve.configPeakCurrentDuration(100);
    fxFLswerve.configContinuousCurrentLimit(20);
    fxFLswerve.enableCurrentLimit(true); */


    I = 0;
    D2 = 0;
    angleConstant = 56.888888888888888888888;

    
  /* //PID and Encoder stuff cz

  //FL 
  fxFLswerve.configNominalOutputForward(0, timeOutMs);
  fxFLswerve.configNominalOutputReverse(0, timeOutMs);
  fxFLswerve.configPeakOutputForward(1, timeOutMs);
  fxFLswerve.configPeakOutputReverse(-1, timeOutMs);

  fxFLswerve.config_kF(loopIDX, f, timeOutMs);
  fxFLswerve.config_kF(loopIDX, P, timeOutMs); 
  fxFLswerve.config_kP(loopIDX, I, timeOutMs);
  fxFLswerve.config_kI(loopIDX, D2, timeOutMs);

  fxFLswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);
  fxFLswerve.setSensorPhase(true);

  //FR
  fxFRswerve.configNominalOutputForward(0, timeOutMs);
  fxFRswerve.configNominalOutputReverse(0, timeOutMs);
  fxFRswerve.configPeakOutputForward(1, timeOutMs);
  fxFRswerve.configPeakOutputReverse(-1, timeOutMs);

  fxFRswerve.config_kF(loopIDX, f, timeOutMs); 
  fxFRswerve.config_kF(loopIDX, P, timeOutMs); 
  fxFRswerve.config_kP(loopIDX, I, timeOutMs);
  fxFRswerve.config_kI(loopIDX, D2, timeOutMs);

  fxFRswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);
  fxFRswerve.setSensorPhase(true);

  //BL
  fxBLswerve.configNominalOutputForward(0, timeOutMs);
  fxBLswerve.configNominalOutputReverse(0, timeOutMs);
  fxBLswerve.configPeakOutputForward(1, timeOutMs);
  fxBLswerve.configPeakOutputReverse(-1, timeOutMs);

  fxBLswerve.config_kF(loopIDX, f, timeOutMs); 
  fxBLswerve.config_kF(loopIDX, P, timeOutMs); 
  fxBLswerve.config_kP(loopIDX, I, timeOutMs);
  fxBLswerve.config_kI(loopIDX, D2, timeOutMs);

  fxBLswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);
  fxBLswerve.setSensorPhase(true);

  //BR
  fxBRswerve.configNominalOutputForward(0, timeOutMs);
  fxBRswerve.configNominalOutputReverse(0, timeOutMs);
  fxBRswerve.configPeakOutputForward(1, timeOutMs);
  fxBRswerve.configPeakOutputReverse(-1, timeOutMs);

  fxBRswerve.config_kF(loopIDX, f, timeOutMs); 
  fxBRswerve.config_kF(loopIDX, P, timeOutMs); 
  fxBRswerve.config_kP(loopIDX, I, timeOutMs);
  fxBRswerve.config_kI(loopIDX, D2, timeOutMs);

  fxBRswerve.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, loopIDX, timeOutMs);
  fxBRswerve.setSensorPhase(true); */
 
  }

  public void driveWithJoystick(Joystick _joy, double speed) {
    fxBLdrive.configPeakOutputForward(speed, timeOutMs);
    fxBLdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxBRdrive.configPeakOutputForward(speed, timeOutMs);
    fxBRdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxFLdrive.configPeakOutputForward(speed, timeOutMs);
    fxFLdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxFRdrive.configPeakOutputForward(speed, timeOutMs);
    fxFRdrive.configPeakOutputReverse(-speed, timeOutMs);
    /* fxFLswerve.configPeakOutputForward(1, timeOutMs);
    fxFLswerve.configPeakOutputReverse(-1, timeOutMs);
    fxFRswerve.configPeakOutputForward(1, timeOutMs);
    fxFRswerve.configPeakOutputReverse(-1, timeOutMs); */

    //Test only fxBRswerve.set(TalonSRXControlMode.Position, 107765724);     

    angle = -ahrs.getYaw(); // defining variable for gyro
    radians = angle * Math.PI / 180;
    currentAngle = fxBRswerve.getSelectedSensorPosition() / angleConstant; // 17.0666666666666666666
    currentAngle2 = fxBLswerve.getSelectedSensorPosition() / angleConstant; // 39.8222222222222222
    currentAngle3 = fxFLswerve.getSelectedSensorPosition() / angleConstant;
    currentAngle4 = fxFRswerve.getSelectedSensorPosition() / angleConstant;
    // swerve formulas
    FWD = _joy.getRawAxis(1); // was -_joy
    STR = _joy.getRawAxis(0); // was -_joy
    RCW = -_joy.getRawAxis(2); // was _joy
    if (FWD < .04 && FWD > -.04) {
      FWD = 0;
    }
    if (STR < .04 && STR > -.04) {
      STR = 0;
    }
    if (RCW < .04 && RCW > -.04) {
      RCW = 0;
    }
    temp = FWD * Math.cos(radians) + STR * Math.sin(radians);
    STR = -FWD * Math.sin(radians) + STR * Math.cos(radians);
    FWD = temp;
    R = Math.sqrt((Constants.L * Constants.L) + (Constants.W * Constants.W));
    A = STR - RCW * (Constants.L / R);
    B = STR + RCW * (Constants.L / R);
    C = FWD - RCW * (Constants.W / R);
    D = FWD + RCW * (Constants.W / R);
    ws1 = Math.sqrt((B * B) + (C * C));
    ws2 = Math.sqrt((B * B) + (D * D));
    ws3 = Math.sqrt((A * A) + (D * D));
    ws4 = Math.sqrt((A * A) + (C * C));
    wa1 = Math.atan2(B, C) * 180 / Math.PI;
    wa2 = Math.atan2(B, D) * 180 / Math.PI;
    wa3 = Math.atan2(A, D) * 180 / Math.PI;
    wa4 = Math.atan2(A, C) * 180 / Math.PI;
    max = ws1;
    if (ws2 > max) {
      max = ws2;
    }
    if (ws3 > max) {
      max = ws3;
    }
    if (ws4 > max) {
      max = ws4;
    }
    if (max > 1) {
      ws1 /= max;
      ws2 /= max;
      ws3 /= max;
      ws4 /= max;
    }
    // swerve formulas

    rotationAmmount = Math.IEEEremainder(wa1 - currentAngle, 360); // calculating ammount to move wheel
    rotationAmmount2 = Math.IEEEremainder(wa2 - currentAngle2, 360); // calculating ammount to move wheel
    rotationAmmount3 = Math.IEEEremainder(wa3 - currentAngle3, 360); // calculating ammount to move wheel
    rotationAmmount4 = Math.IEEEremainder(wa4 - currentAngle4, 360); // calculating ammount to move wheel

    if (FWD < .05 && FWD > -.05 && STR < .05 && STR > -.05 && RCW < .05 && RCW > -.05) { // not letting the wheels move
                                                                                         // unitll
      // the joystick is pushed %10 down
      fxFLdrive.set(0);
      fxFRdrive.set(0);
      fxBLdrive.set(0);
      fxBRdrive.set(0);
    } else {
      fxBRdrive.set(ws1); // driving drive wheel off the formulas
      fxBLdrive.set(-ws2);
      fxFLdrive.set(-ws3);
      fxFRdrive.set(ws4);
    }
    SmartDashboard.putNumber("bl", fxBLswerve.getSelectedSensorPosition());
    SmartDashboard.putNumber("trying to find", (currentAngle2 + rotationAmmount2) * 79.644444444444444);
    
    if (FWD != 0 | STR != 0 | RCW != 0) {
      SmartDashboard.putNumber("test", 665);
	    // Set the wheel angle to the required position - Update to TalonFXControlMode
      fxBRswerve.set(TalonFXControlMode.Position, (currentAngle + rotationAmmount) * angleConstant); //17.0666666666666
      fxBLswerve.set(TalonFXControlMode.Position, (currentAngle2 + rotationAmmount2) * angleConstant); //39.822222222222222
      fxFLswerve.set(TalonFXControlMode.Position, (currentAngle3 + rotationAmmount3) * angleConstant);
      fxFRswerve.set(TalonFXControlMode.Position, (currentAngle4 + rotationAmmount4) * angleConstant); 
     
    }
  }

  //Distance Drive With Values
    public void driveWithValues(double FWD, double STR, double RCW, double distance, double speed) {
    
    fxBLdrive.configPeakOutputForward(speed, timeOutMs);
    fxBLdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxBRdrive.configPeakOutputForward(speed, timeOutMs);
    fxBRdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxFLdrive.configPeakOutputForward(speed, timeOutMs);
    fxFLdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxFRdrive.configPeakOutputForward(speed, timeOutMs);
    fxFRdrive.configPeakOutputReverse(-speed, timeOutMs);
    angle = -ahrs.getYaw(); // defining variable for gyro
    radians = angle * Math.PI / 180;
    currentAngle = fxBRdrive.getSelectedSensorPosition() / angleConstant;
    currentAngle2 = fxBLdrive.getSelectedSensorPosition() / angleConstant;
    currentAngle3 = fxFLdrive.getSelectedSensorPosition() / angleConstant;
    currentAngle4 = fxFRdrive.getSelectedSensorPosition() / angleConstant;
    // swerve formulas

    temp = FWD * Math.cos(radians) + STR * Math.sin(radians);
    STR = -FWD * Math.sin(radians) + STR * Math.cos(radians);
    FWD = temp;
    R = Math.sqrt((Constants.L * Constants.L) + (Constants.W * Constants.W));
    A = STR - RCW * (Constants.L / R);
    B = STR + RCW * (Constants.L / R);
    C = FWD - RCW * (Constants.W / R);
    D = FWD + RCW * (Constants.W / R);
    ws1 = Math.sqrt((B * B) + (C * C));
    ws2 = Math.sqrt((B * B) + (D * D));
    ws3 = Math.sqrt((A * A) + (D * D));
    ws4 = Math.sqrt((A * A) + (C * C));
    wa1 = Math.atan2(B, C) * 180 / Math.PI;
    wa2 = Math.atan2(B, D) * 180 / Math.PI;
    wa3 = Math.atan2(A, D) * 180 / Math.PI;
    wa4 = Math.atan2(A, C) * 180 / Math.PI;
    max = ws1;
    if (ws2 > max) {
      max = ws2;
    }
    if (ws3 > max) {
      max = ws3;
    }
    if (ws4 > max) {
      max = ws4;
    }
    if (max > 1) {
      ws1 /= max;
      ws2 /= max;
      ws3 /= max;
      ws4 /= max;
    }
    // swerve formulas

    rotationAmmount = Math.IEEEremainder(wa1 - currentAngle, 360); // calculating ammount to move wheel
    rotationAmmount2 = Math.IEEEremainder(wa2 - currentAngle2, 360); // calculating ammount to move wheel
    rotationAmmount3 = Math.IEEEremainder(wa3 - currentAngle3, 360); // calculating ammount to move wheel
    rotationAmmount4 = Math.IEEEremainder(wa4 - currentAngle4, 360); // calculating ammount to move wheel


    if(rotationAmmount < .1 & rotationAmmount2 < .1 & rotationAmmount3 < .1 & rotationAmmount4 < .1) {
    fxBRdrive.set(ControlMode.Position, distance); // driving drive wheel off the formulas
    fxBLdrive.set(ControlMode.Position, -distance);
    fxFLdrive.set(ControlMode.Position, -distance);
    fxFRdrive.set(ControlMode.Position, distance);
    }
    SmartDashboard.putNumber("enc pos", fxBRdrive.getSelectedSensorPosition());
    if (FWD != 0 | STR != 0 | RCW != 0) {
	  // Set the wheel angle to the required position
      fxBRswerve.set(ControlMode.Position, (currentAngle + rotationAmmount) * angleConstant);
      fxBLswerve.set(ControlMode.Position, (currentAngle2 + rotationAmmount2) * angleConstant);
      fxFLswerve.set(ControlMode.Position, (currentAngle3 + rotationAmmount3) * angleConstant);
      fxFRswerve.set(ControlMode.Position, (currentAngle4 + rotationAmmount4) * angleConstant);
    }
  }  
  
  //RCW Drive With Values
  public void driveWithValues(double FWD, double STR, double RCW, double speed) {
    fxBLdrive.configPeakOutputForward(speed, timeOutMs);
    fxBLdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxBRdrive.configPeakOutputForward(speed, timeOutMs);
    fxBRdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxFLdrive.configPeakOutputForward(speed, timeOutMs);
    fxFLdrive.configPeakOutputReverse(-speed, timeOutMs);
    fxFRdrive.configPeakOutputForward(speed, timeOutMs);
    fxFRdrive.configPeakOutputReverse(-speed, timeOutMs);
    angle = -ahrs.getYaw(); // defining variable for gyro
    radians = angle * Math.PI / 180;
    currentAngle = fxBRdrive.getSelectedSensorPosition() / angleConstant;
    currentAngle2 = fxBLdrive.getSelectedSensorPosition() / angleConstant;
    currentAngle3 = fxFLdrive.getSelectedSensorPosition() / angleConstant;
    currentAngle4 = fxFRdrive.getSelectedSensorPosition() / angleConstant;
    // swerve formulas

    temp = FWD * Math.cos(radians) + STR * Math.sin(radians);
    STR = -FWD * Math.sin(radians) + STR * Math.cos(radians);
    FWD = temp;
    R = Math.sqrt((Constants.L * Constants.L) + (Constants.W * Constants.W));
    A = STR - RCW * (Constants.L / R);
    B = STR + RCW * (Constants.L / R);
    C = FWD - RCW * (Constants.W / R);
    D = FWD + RCW * (Constants.W / R);
    ws1 = Math.sqrt((B * B) + (C * C));
    ws2 = Math.sqrt((B * B) + (D * D));
    ws3 = Math.sqrt((A * A) + (D * D));
    ws4 = Math.sqrt((A * A) + (C * C));
    wa1 = Math.atan2(B, C) * 180 / Math.PI;
    wa2 = Math.atan2(B, D) * 180 / Math.PI;
    wa3 = Math.atan2(A, D) * 180 / Math.PI;
    wa4 = Math.atan2(A, C) * 180 / Math.PI;
    max = ws1;
    if (ws2 > max) {
      max = ws2;
    }
    if (ws3 > max) {
      max = ws3;
    }
    if (ws4 > max) {
      max = ws4;
    }
    if (max > 1) {
      ws1 /= max;
      ws2 /= max;
      ws3 /= max;
      ws4 /= max;
    }
    // swerve formulas

    rotationAmmount = Math.IEEEremainder(wa1 - currentAngle, 360); // calculating ammount to move wheel
    rotationAmmount2 = Math.IEEEremainder(wa2 - currentAngle2, 360); // calculating ammount to move wheel
    rotationAmmount3 = Math.IEEEremainder(wa3 - currentAngle3, 360); // calculating ammount to move wheel
    rotationAmmount4 = Math.IEEEremainder(wa4 - currentAngle4, 360); // calculating ammount to move wheel

    fxBRdrive.set(ControlMode.PercentOutput, .3); // driving drive wheel off the formulas
    fxBLdrive.set(ControlMode.PercentOutput, -.3);
    fxFLdrive.set(ControlMode.PercentOutput, -.3);
    fxFRdrive.set(ControlMode.PercentOutput, .3);
    if (FWD != 0 | STR != 0 | RCW != 0) {
      // Set the wheel angle to the required position
	  fxBRswerve.set(ControlMode.Position, (currentAngle + rotationAmmount) * angleConstant);
      fxBLswerve.set(ControlMode.Position, (currentAngle2 + rotationAmmount2) * angleConstant);
      fxFLswerve.set(ControlMode.Position, (currentAngle3 + rotationAmmount3) * angleConstant);
      fxFRswerve.set(ControlMode.Position, (currentAngle4 + rotationAmmount4) * angleConstant);
    }
  }

  public void goToZero() {
    fxBRdrive.set(ControlMode.Position,0);
    fxBLdrive.set(ControlMode.Position,0);
    fxFLdrive.set(ControlMode.Position,0);
    fxFRdrive.set(ControlMode.Position,0);
  }

  public void zeroEncoders() {
    fxFLdrive.setSelectedSensorPosition(0);
    fxFRdrive.setSelectedSensorPosition(0);
    fxBLdrive.setSelectedSensorPosition(0);
    fxBRdrive.setSelectedSensorPosition(0);
    fxBRdrive.setSelectedSensorPosition(0);
    fxBLdrive.setSelectedSensorPosition(0);
    fxFRdrive.setSelectedSensorPosition(0);
    fxFLdrive.setSelectedSensorPosition(0);
  }

  public void zeroDrive() {
    fxFLdrive.setSelectedSensorPosition(0);
    fxFRdrive.setSelectedSensorPosition(0);
    fxBLdrive.setSelectedSensorPosition(0);
    fxBRdrive.setSelectedSensorPosition(0);
  }

  public double getDriveEncoder() {
    return fxFLdrive.getSelectedSensorPosition();
  }
  public void writeEncoderPos() {
    driveEncoderRelitivePosition = getDriveEncoder();
  }
  public boolean driveNeeded(boolean neededArg) {
    needed = neededArg;
    return needed;
  }
  public boolean driveNeeded() {
    return needed;
  }
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
