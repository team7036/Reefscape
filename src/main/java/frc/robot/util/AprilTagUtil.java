package frc.robot.util;

import edu.wpi.first.apriltag.AprilTag;
import frc.robot.Constants;

import java.util.Optional;

public class AprilTagUtil {
    public static boolean isValidTag(int id) {
        for (Constants.AprilTags.Tags tag : Constants.AprilTags.Tags.values()) {
            if (tag.getId() == id) {
                return true;
            }
        }
        return false;
    }
    public static AprilTag getAprilTag(int id) {
        if(isValidTag(id)) {
            Optional<AprilTag> tag = Constants.AprilTags.APRIL_TAGS.stream().filter(aprilTag -> aprilTag.ID == id).findFirst();
            return tag.orElse(null);
        }
        return null;
    }
    public static Constants.AprilTags.Tags fromId(int id) {
        for(Constants.AprilTags.Tags tag : Constants.AprilTags.Tags.values()) {
            if(tag.getId() == id) {
                return tag;
            }
        }
        return null;
    }
}
