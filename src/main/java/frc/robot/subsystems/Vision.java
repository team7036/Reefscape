package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Vision extends SubsystemBase {


    public static class AprilTag {
        private final int id;
        private final Pose3d location;

        public AprilTag(int id, Pose3d loc) {
            this.id = id;
            this.location = loc;
        }

        public int getId() {
            return id;
        }

        public Pose3d getLocation() {
            return location;
        }
    }

    public boolean hasDetectedAprilTag() {
        return NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0.0) == 1.0;
    }

    public AprilTag getDetectedAprilTag() {
        if(hasDetectedAprilTag()) {
            int id = (int) NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(-1);
            Pose3d loc = new Pose3d(
                    NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0),
                    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0),
                    NetworkTableInstance.getDefault().getTable("limelight").getEntry("tz").getDouble(0.0),
                    new Rotation3d(
                            Math.toRadians(NetworkTableInstance.getDefault().getTable("limelight").getEntry("rx").getDouble(0.0)),
                            Math.toRadians(NetworkTableInstance.getDefault().getTable("limelight").getEntry("ry").getDouble(0.0)),
                            Math.toRadians(NetworkTableInstance.getDefault().getTable("limelight").getEntry("rz").getDouble(0.0))
                    )
            );
            return new AprilTag(id, loc);
        }
        return null;
    }
}
