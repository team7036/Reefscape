package frc.robot.subsystems.drivetrain;

import com.studica.frc.AHRS;

import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class Drivetrain {
    private static SwerveModule frontLeft = new SwerveModule(0, 0);
    private static SwerveModule frontRight = new SwerveModule(0, 0);
    private static SwerveModule backLeft = new SwerveModule(0, 0);
    private static SwerveModule backRight = new SwerveModule(0, 0);

    private final SlewRateLimiter xSpeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter ySpeedLimiter = new SlewRateLimiter(3);
    private final SlewRateLimiter rotSpeedLimiter = new SlewRateLimiter(2 * Math.PI);

    private SwerveModuleState[] moduleStates;

    private final AHRS gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(null);

    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(kinematics, null, null);

    public Drivetrain(){
        gyro.reset();
    }

}
