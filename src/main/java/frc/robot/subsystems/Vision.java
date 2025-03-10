package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {

    public Pose3d robotPose;
    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

    public Vision(){
    }

    public boolean detectsAprilTag() {
        return limelight.getEntry("tv").getDouble(0.0) == 1.0;
    }

    public void periodic(){
        if ( detectsAprilTag() ){
            //int id = (int) limelight.getEntry("tid").getDouble(-1);
            robotPose = new Pose3d(
                    limelight.getEntry("tx").getDouble(0.0),
                    limelight.getEntry("ty").getDouble(0.0),
                    limelight.getEntry("tz").getDouble(0.0),
                    new Rotation3d(
                            Math.toRadians(limelight.getEntry("rx").getDouble(0.0)),
                            Math.toRadians(limelight.getEntry("ry").getDouble(0.0)),
                            Math.toRadians(limelight.getEntry("rz").getDouble(0.0))
                    )
            );
        } else {
            robotPose = null;
        }
    }

    void setupDashboard(){
        SmartDashboard.putNumber("vision/poseX", robotPose.getX());
        SmartDashboard.putNumber("vision/poseY", robotPose.getY());
        SmartDashboard.putNumber("vision/poseZ", robotPose.getZ());
        SmartDashboard.putNumber("vision/poseRot", robotPose.getRotation().getAngle());
    }
}
