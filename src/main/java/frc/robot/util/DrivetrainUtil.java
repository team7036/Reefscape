package frc.robot.util;

import java.util.Optional;

import com.pathplanner.lib.config.RobotConfig;

public class DrivetrainUtil {

    private static Optional<RobotConfig> getConfig() {
        RobotConfig config = null;

        try {
            config = RobotConfig.fromGUISettings();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(config);
    }
    public static RobotConfig config() {
        return getConfig().orElseThrow(() -> new IllegalAccessError("Cannot get robot config!"));
    }
}
