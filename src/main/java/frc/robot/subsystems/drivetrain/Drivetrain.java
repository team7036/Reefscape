package frc.robot.subsystems.drivetrain;

import com.studica.frc.AHRS;

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

public class Drivetrain extends SubsystemBase {

    private final SwerveModule frontRight;
    private final SwerveModule backLeft;
    private final SwerveModule backRight;
    private final SwerveModule frontLeft;

    private SwerveModuleState[] moduleStates;
    private final AHRS gyro;
    private final SwerveDriveKinematics kinematics;
    private final SwerveDriveOdometry odometry;
    public boolean fieldRelative;

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
            new SwerveModulePosition[]{
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
        });
        resetGyro();
    }

    public Rotation2d getAngle(){
        return gyro.getRotation2d();
    }

    public void setSpeeds( ChassisSpeeds speeds ){
        moduleStates = kinematics.toSwerveModuleStates(
            fieldRelative ? ChassisSpeeds.fromRobotRelativeSpeeds(speeds, gyro.getRotation2d()) : speeds
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

    @Override
    public void periodic(){

        // Update Drivetrain Odometry
        odometry.update(
            gyro.getRotation2d(),
            new SwerveModulePosition[] {
                frontLeft.getPosition(),
                frontRight.getPosition(),
                backLeft.getPosition(),
                backRight.getPosition()
            }
        );

        // TODO: Add vision to update actual location on the field
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("heading", this.gyro::getAngle, null);
        builder.addBooleanProperty("field_relative", null, null);
    }

}
