package frc.robot.util;

import edu.wpi.first.apriltag.AprilTag;
import frc.robot.Constants;
import frc.robot.Constants.AprilTags.Tags;

import java.util.Arrays;
//import java.util.Optional;

public class AprilTagUtil {
    public static boolean isValidTag(int id) {
        // Logic Expanded:
        // for (Constants.AprilTags.Tags tag : Constants.AprilTags.Tags.values()) {
        //     if (tag.getId() == id) {
        //         return true;
        //     }
        // }
        // Logic
        return Arrays.stream(Tags.values()).map(tag -> tag.getId()).anyMatch(_id -> _id == id);
    }
    public static AprilTag getAprilTag(int id) {
        if(isValidTag(id)) {
            //Optional<AprilTag> tag = Constants.AprilTags.APRIL_TAGS.stream().filter(aprilTag -> aprilTag.ID == id).findFirst();
            return Constants.AprilTags.APRIL_TAGS.stream().filter(aprilTag -> aprilTag.ID == id).findFirst().orElse(null);
        }
        return null;
    }
    public static Constants.AprilTags.Tags fromId(int id) {
        // Logic Expandes
        // for(Constants.AprilTags.Tags tag : Constants.AprilTags.Tags.values()) {
        //     if(tag.getId() == id) {
        //         return tag;
        //     }
        // }
        // Logic Shortened
        return Arrays.stream(Tags.values()).filter(tag -> tag.getId() == id).findFirst().orElseThrow(() -> new IllegalArgumentException("Id of " + id + " is not a valid AprilTag id"));
    }
}
