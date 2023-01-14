/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {
    // Can values
    public static int pdp = 1;
    //public static int pcm = 1;
    public static int fxShooter = 2;
    public static int maxIntake = 3;
    public static int maxConvayer1 = 4;
    public static int maxConvayer2 = 5; 
    public static int fxFLdrive = 6;
    public static int fxFRdrive = 7;
    public static int fxBLdrive = 8;
    public static int fxBRdrive = 9;
    public static int fxFLswerve = 10;
    public static int fxFRswerve = 11;
    public static int fxBLswerve = 12;
    public static int fxBRswerve = 13;
    public static int maxAdjuster = 14;
    public static int maxRotater = 15;
    public static int fxLClimberWinch = 16;
    public static int fxRClimberWinch = 17;
    public static int maxClimberDrive = 17;
    public static int fxClimberS2Pivot = 18; //validate
    public static int fxClimberS2Claw = 19; //validate

    //Joystick values 
    public static int SQUARE = 1;
    public static int X = 2;
    public static int CIRCLE = 3;
    public static int TRIANGLE = 4;
    public static int L1 = 5;
    public static int R1 = 6;
    public static int L2 = 7;
    public static int R2 = 8;
    public static int SHARE = 9;
    public static int OPT = 10;
    public static int HOME = 13;

    //robot constants
    public static int shooterRPM = 6000; //3500 regular try 5000 to see if there are differences - 6350 max
    public static int shooterRPM2 = 4000; //3500 regular
    public static int shooterRPMAuto = 4100;
    public static int shooterRPMHigh = 4100;
    public static int shooterRPMLow = 2200;
    public static double lineAdjusterPos = -9.5;
    public static double adjusterHomePos = 0;
    public static double convayer1Speed = 0.75;//.-57
    public static double convayer2Speed = -1;//-.7

    //swerve values
    public static double L = 26.6875; //originally 21.9376 AB
    public static double W = 22.87402; //originally 25.215
}
