package frc.robot.util;

import java.util.Optional;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class AllianceUtil {
    public static boolean isRed() {
        Optional<Alliance> alliance = DriverStation.getAlliance();
        if(alliance.isPresent()) {
            return alliance.get() == Alliance.Red;
        }
        //Make default blue
        return false;
    }
}
