package frc.robot.subsystems;

import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.AprilTags.Tags;
import frc.robot.util.AprilTagUtil;

public class Vision extends SubsystemBase {

    public Pose3d robotPose;
    public Constants.AprilTags.Tags currentTag;
    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable("limelight");

    public Vision(){
        setupDashboard();
    }

    public boolean detectsAprilTag() {
        return limelight.getEntry("tv").getDouble(0.0) == 1.0;
    }
    @Override
    public void periodic(){
        //update robotpose
        robotPose = transformToRobotPose(limelight.getEntry("botpose").getDoubleArray(new double[6]));
        if ( detectsAprilTag() ){
            int sensedId = (int) limelight.getEntry("tid").getDouble(-1);
            //Store Tag Id & Pose.
            currentTag = sensedId == -1 ? Tags.NONE : AprilTagUtil.fromId(sensedId);
        } else {
            currentTag = Tags.NONE;
        }
    }

    void setupDashboard(){
        SmartDashboard.putNumber("vision/poseX", robotPose.getX());
        SmartDashboard.putNumber("vision/poseY", robotPose.getY());
        SmartDashboard.putNumber("vision/poseZ", robotPose.getZ());
        SmartDashboard.putNumber("vision/poseRot", robotPose.getRotation().getAngle());
    }
    //Can move later, just a simple helper method
    private Pose3d transformToRobotPose(double[] arr) {
        return new Pose3d(arr[0], arr[1], arr[2], new Rotation3d(arr[3], arr[4], arr[5]));
    }

    public Pose3d getRobotPose() {
        return this.robotPose;
    }
}
