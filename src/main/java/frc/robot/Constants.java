// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

  public static class AprilTags {
    //LIST OF ALL OF THE TAGS
    public enum Tags {
      //RED
      RED_CORAL_A(1),
      RED_CORAL_B(2),
      RED_PROCESSOR(3),
      RED_BARGE_R(4),
      RED_BARGE_L(5),
      RED_REEF_A(6),
      RED_REEF_B(7),
      RED_REEF_C(8),
      RED_REEF_D(9),
      RED_REEF_E(10),
      RED_REEF_F(11),
      //BLUE
      BLUE_CORAL_A(12),
      BLUE_CORAL_B(13),
      BLUE_BARGE_L(14),
      BLUE_BARGE_R(15),
      BLUE_PROCESSOR(16),
      BLUE_REEF_A(17),
      BLUE_REEF_B(18),
      BLUE_REEF_C(19),
      BLUE_REEF_D(20),
      BLUE_REEF_E(21),
      BLUE_REEF_F(22),
      //OTHER
      NONE(0);

      private int id;

      Tags(int id) {
        this.id = id;
      }

      public int getId() {
        return id;
      }
    }
    public static final List<AprilTag> APRIL_TAGS = List.of(
            new AprilTag(1, new Pose3d(new Translation3d(657.37, 25.80, 58.50), new Rotation3d(0, 0, Math.toRadians(126)))),
            new AprilTag(2, new Pose3d(new Translation3d(657.37, 291.20, 58.50), new Rotation3d(0, 0, Math.toRadians(234)))),
            new AprilTag(3, new Pose3d(new Translation3d(455.15, 317.15, 51.25), new Rotation3d(0, 0, Math.toRadians(270)))),
            new AprilTag(4, new Pose3d(new Translation3d(365.20, 241.64, 73.54), new Rotation3d(Math.toRadians(30), 0, 0))),
            new AprilTag(5, new Pose3d(new Translation3d(365.20, 75.39, 73.54), new Rotation3d(Math.toRadians(30), 0, 0))),
            new AprilTag(6, new Pose3d(new Translation3d(530.49, 130.17, 12.13), new Rotation3d(0, 0, Math.toRadians(300)))),
            new AprilTag(7, new Pose3d(new Translation3d(546.87, 158.50, 12.13), new Rotation3d(0, 0, 0))),
            new AprilTag(8, new Pose3d(new Translation3d(530.49, 186.83, 12.13), new Rotation3d(0, 0, Math.toRadians(60)))),
            new AprilTag(9, new Pose3d(new Translation3d(497.77, 186.83, 12.13), new Rotation3d(0, 0, Math.toRadians(120)))),
            new AprilTag(10, new Pose3d(new Translation3d(481.39, 158.50, 12.13), new Rotation3d(0, 0, Math.toRadians(180)))),
            new AprilTag(11, new Pose3d(new Translation3d(497.77, 130.17, 12.13), new Rotation3d(0, 0, Math.toRadians(240)))),
            new AprilTag(12, new Pose3d(new Translation3d(33.51, 25.80, 58.50), new Rotation3d(0, 0, Math.toRadians(54)))),
            new AprilTag(13, new Pose3d(new Translation3d(33.51, 291.20, 58.50), new Rotation3d(0, 0, Math.toRadians(306)))),
            new AprilTag(14, new Pose3d(new Translation3d(325.68, 241.64, 73.54), new Rotation3d(Math.toRadians(30), 0, Math.toRadians(180)))),
            new AprilTag(15, new Pose3d(new Translation3d(325.68, 75.39, 73.54), new Rotation3d(Math.toRadians(30), 0, Math.toRadians(180)))),
            new AprilTag(16, new Pose3d(new Translation3d(235.73, -0.15, 51.25), new Rotation3d(0, 0, Math.toRadians(90)))),
            new AprilTag(17, new Pose3d(new Translation3d(160.39, 130.17, 12.13), new Rotation3d(0, 0, Math.toRadians(240)))),
            new AprilTag(18, new Pose3d(new Translation3d(144.00, 158.50, 12.13), new Rotation3d(0, 0, Math.toRadians(180)))),
            new AprilTag(19, new Pose3d(new Translation3d(160.39, 186.83, 12.13), new Rotation3d(0, 0, Math.toRadians(120)))),
            new AprilTag(20, new Pose3d(new Translation3d(193.10, 186.83, 12.13), new Rotation3d(0, 0, Math.toRadians(60)))),
            new AprilTag(21, new Pose3d(new Translation3d(209.49, 158.50, 12.13), new Rotation3d(0, 0, 0))),
            new AprilTag(22, new Pose3d(new Translation3d(193.10, 130.17, 12.13), new Rotation3d(0, 0, Math.toRadians(300))))
    );
  }

  public static class Controllers {
    public static final int kDriverPort = 0;
    public static int kOperatorPort = 1;
  }
  public static class Intake {
    public static int kCoralIntakeMotorCANID = 55;
    public static double kCoralIntakeSpeed = 0.1;
    public static double kCoralDeliverySpeed = 0.65;
    public static int kAlgaeMotorCANId = 30;
  }
  public static class Bindings {
    public static double triggerDownThreshhold = 0.9;
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
      public static double kCoralStation = 0.28;
      public static double kL1 = kCoralStation + 0.2;
      public static double kL2 = kL1 + 0.25;
      public static double kL3 = kL2 + 0.35;
      public static double kL4 = kL3 + 0.5;
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
}
