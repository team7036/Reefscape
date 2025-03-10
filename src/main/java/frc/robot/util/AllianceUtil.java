package frc.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.subsystems.Vision;

import java.util.Optional;
import java.util.Set;

public class AllianceUtil {
    public static boolean isRed() {
        Optional<DriverStation.Alliance> alliance = DriverStation.getAlliance();
        return alliance.isPresent() && alliance.get().equals(DriverStation.Alliance.Red);
    }
}
