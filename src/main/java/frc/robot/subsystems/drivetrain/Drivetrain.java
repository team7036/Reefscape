package frc.robot.subsystems.drivetrain;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.controllers.PPLTVController;
import com.studica.frc.AHRS;

import edu.wpi.first.math.estimator.SwerveDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drivetrain.Movement;
import frc.robot.Constants.Drivetrain.Swerve;
import frc.robot.util.DrivetrainUtil;

public class Drivetrain extends SubsystemBase {

    private final SwerveModule frontRight, frontLeft, backRight, backLeft;

    private SwerveModuleState[] moduleStates;

    private final AHRS gyro;
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;

    public boolean fieldRelative;

    public final SwerveDrivePoseEstimator poseEstimator;

    public Drivetrain(boolean _fieldRelative){
        fieldRelative = _fieldRelative;
        frontRight = new SwerveModule(Swerve.CANIds.kFrontRightDrive, Swerve.CANIds.kFrontRightTurn, Swerve.CANIds.kFrontRightEncoder);
        backLeft = new SwerveModule(Swerve.CANIds.kBackLeftDrive, Swerve.CANIds.kBackLeftTurn, Swerve.CANIds.kBackLeftEncoder);
        backRight = new SwerveModule(Swerve.CANIds.kBackRightDrive, Swerve.CANIds.kBackRightTurn, Swerve.CANIds.kBackRightEncoder);
        frontLeft = new SwerveModule(Swerve.CANIds.kFrontLeftDrive, Swerve.CANIds.kFrontLeftTurn, Swerve.CANIds.kFrontLeftEncoder);
        gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);
        kinematics = new SwerveDriveKinematics(
            new Translation2d(0.315, -0.315), // Front Left Location
            new Translation2d(0.315, 0.315), // Front Right
            new Translation2d(-0.315, -0.315), // Back Left
            new Translation2d(-0.315, 0.315) // Back Right
        );
        odometry = new SwerveDriveOdometry(
            kinematics,
            gyro.getRotation2d(), 
            getSwerveModulePositions());
        poseEstimator = new SwerveDrivePoseEstimator(
            kinematics,
            getAngle(),
            getSwerveModulePositions(),
            odometry.getPoseMeters()
        );
        resetGyro();
        //Configure AutoBuilder
        AutoBuilder.configure(
            this::getCurrentPose,
            this::resetPose,
            this::getRobotRelativeSpeeds,
            (speeds, FeedForwards) -> setSpeeds(speeds, true),
            new PPLTVController(0.02),
            DrivetrainUtil.config(),
            () -> false, //Jayden already made paths for both red & blue
            this
        );
    }

    public Rotation2d getAngle(){
        return gyro.getRotation2d();
    }
    private ChassisSpeeds getRobotRelativeSpeeds() {
        return kinematics.toChassisSpeeds(getStates());
    }
    private SwerveModuleState[] getStates() {
        return moduleStates;
    }
    public void setSpeeds( ChassisSpeeds speeds, boolean forceRobotRelative ){
        moduleStates = kinematics.toSwerveModuleStates(
            fieldRelative && !forceRobotRelative ? ChassisSpeeds.fromRobotRelativeSpeeds(speeds, gyro.getRotation2d()) : speeds
        );
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, Movement.kMaxSpeed);
        frontLeft.setDesiredState(moduleStates[0]);
        frontRight.setDesiredState(moduleStates[1]);
        backLeft.setDesiredState(moduleStates[2]);
        backRight.setDesiredState(moduleStates[3]);
    }

    public void resetGyro(){
        gyro.reset();
    }
    public SwerveModulePosition[] getSwerveModulePositions() {
        return new SwerveModulePosition[]{
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        };
    }

    @Override
    public void periodic(){
        // Update Drivetrain Odometry
        updateOdometry();
        poseEstimator.update(getAngle(), getSwerveModulePositions());

    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("pose/x", getCurrentPose()::getX, null);
        builder.addDoubleProperty("pose/y", getCurrentPose()::getY, null);
        builder.addDoubleProperty("pose/rot", getCurrentPose().getRotation()::getDegrees, null);
        builder.addBooleanProperty("field_relative", () -> this.fieldRelative, null);
    }
    public void resetPose(Pose2d pose) {
        odometry.resetPose(pose);
    }
    public void updateOdometry() {
        poseEstimator.update(getAngle(), getSwerveModulePositions());
    }
    public Pose2d getCurrentPose() {
        return poseEstimator.getEstimatedPosition();
    }
}
