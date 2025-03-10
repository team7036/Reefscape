// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose3d;

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
    public static int kOperatorPort = 1;
  }
  public static class Intake {
    public static int kCoralIntakeMotorCANID = 55;
    public static double kCoralIntakeSpeed = 0.1;
    public static double kCoralDeliverySpeed = 1.0;
    public static int kAlgaeMotorCANId = 30;
  }
  public static class Bindings {
    public static int triggerDownThreshhold = 0;
  }
  public static class Elevator {
    // CAN ID
    public static int kMotorCANId = 60;
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
    public static final double kMaxAcceleration = 2.0;
    public static final double kMaxVelocity = 2;
    // Convert raw encoder value to meters
    public static double conversion = 5.27e-3;

    public static class Heights {
      // Target Heights
      public static double kIntaking = 0.3;
      public static double kCoralStation = 0.18;
      public static double kL4 = 1.44;
      public static double kL3 = 0.95;
      public static double kL2 = 0.62;
      public static double kL1 = 0.4;
    }
  }
  public static class TestMotorControllers {
    public static int testSparkMax1 = 51;
    public static int testSparkMax2 = 52;
  }
  public static class Climber {
    public static int kClimberMotorCANID = 0;
    public static double kClimberUpSpeed = 0.2;
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
  public static class AprilTags {
    public enum Tags {
      //Red
      RED_SIDE_A(6),
      RED_SIDE_B(7),
      RED_SIDE_C(8),
      RED_SIDE_D(9),
      RED_SIDE_E(10),
      RED_SIDE_F(11),
      RED_CORAL_PA(1),
      RED_CORAL_PB(2),
      RED_PROCESSOR(3),
      RED_BARGE_R(5),
      RED_BARGE_B(15),
      //Blue
      BLUE_SIDE_A(17),
      BLUE_SIDE_B(18),
      BLUE_SIDE_C(19),
      BLUE_SIDE_D(20),
      BLUE_SIDE_E(21),
      BLUE_SIDE_F(22),
      BLUE_CORAL_PA(12),
      BLUE_CORAL_PB(13),
      BLUE_PROCESSOR(16),
      BLUE_BARGE_B(14),
      BLUE_BARGE_R(4);

      private int id;

      Tags(int id) {
        this.id = id;
      }

      public int getId() {
        return this.id;
      }
    }

    public static boolean isValidTag(int id) {
      for (Tags tag : Tags.values()) {
        if (tag.getId() == id) {
          return true;
        }
      }
      return false;
    }
  }
}
