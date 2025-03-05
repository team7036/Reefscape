// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class Controllers {
    public static final int kDriverPort = 0;
  }
  public static class Intake {
    public static int coralMotorCANId = 55;
    public static double coralIntakeSpeed = 0.1;
    public static double coralDeliverySpeed = 1.0;
    public static int algaeMotorCANId = 30;
  }
  public static class Elevator {
    // CAN ID
    public static int motorCanId = 60;
    // Feed Foward
    public static double kS = 0;
    public static double kG = 0.24;
    public static double kV = 20;
    public static double kA = 0;
    // PID
    public static double kP = 70;
    public static int kI = 0;
    public static double kD = 0.0;
    //For startup or shutdown to reset elevator height to all the way done
    public static final double maxAcceleration = 2.0;
    public static final double maxVelocity = 2;
    // Convert raw encoder value to meters
    public static double conversion = 5.27e-3;

    public static class Heights {
      // Target Heights
      public static double intaking = 0.3;
      public static double CoralStation = 0.18;
      public static double ReefL4 = 1.49;
      public static double ReefL3 = 0.95;
      public static double ReefL2 = 0.67;
      public static double ReefL1 = 0.5;
      public static double defaultHeight = intaking;
    }
  }
  public static class TestMotorControllers {
    public static int testSparkMax1 = 51;
    public static int testSparkMax2 = 52;
  }

  public static class Drivetrain {
    public static class Movement {
      public static double kWheelRadius = 0.102;
      
      public static double kDriveGearRatio = 6.75;
      public static double kSlewRateLimitX = 3.0;
      public static double kSlewRateLimitY = 3.0;
      public static double kSlewRateLimitRot = 3.0;
      // Convert Raw RPM of NEO to RPM of wheel
      // RPM * Gear Ratio * Wheel Radius / 60 * 2 * Math.PI
      public static double kDriveVelocityConversionFactor = 1/kDriveGearRatio * 1/60 * 2*Math.PI*kWheelRadius;
      public static double kMaxSpeed = 5800 * kDriveVelocityConversionFactor; // m/s
      public static double kMaxRotation = 20;

    }
    public static class Swerve {
      public class CANIds {
        public static final int kFrontLeftDrive = 22;
        public static final int kFrontLeftTurn = 23;
        public static final int kFrontLeftEncoder = 30;
        public static final int kFrontRightDrive = 16;
        public static final int kFrontRightTurn = 17;
        public static final int kFrontRightEncoder = 31;
        public static final int kBackRightDrive = 18;
        public static final int kBackRightTurn = 19;
        public static final int kBackRightEncoder = 33;
        public static final int kBackLeftDrive = 20;
        public static final int kBackLeftTurn = 21;
        public static final int kBackLeftEncoder = 32;
      }
      public class PID {
        public class Drive {
          public static final double kP =  0;
          public static final int kI = 0;
          public static final double kD = 0;
        }
        public class Turn {
          public static final double kP = 4.4;
          public static final double kI = 0;
          public static final double kD = 0.1;
        }
      }
      public class FeedForward {
        public class Drive {
          public static final double kS = 0;
          public static final double kV = 1.3;
        }
        public class Turn {
          public static final double kS = 0.13;
          public static final double kV = 0;
        }
      }
    }
  }
}
