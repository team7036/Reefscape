package frc.robot.subsystems.drivetrain;

import com.studica.frc.AHRS;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drivetrain.Movement;
import frc.robot.Constants.Drivetrain.Swerve;

public class Drivetrain extends SubsystemBase {

    private final SwerveModule frontRight = new SwerveModule(Swerve.CANIds.kFrontRightDrive, Swerve.CANIds.kFrontRightTurn, Swerve.CANIds.kFrontRightEncoder);
    private final SwerveModule backLeft = new SwerveModule(Swerve.CANIds.kBackLeftDrive, Swerve.CANIds.kBackLeftTurn, Swerve.CANIds.kBackLeftEncoder);
    private final SwerveModule backRight = new SwerveModule(Swerve.CANIds.kBackRightDrive, Swerve.CANIds.kBackRightTurn, Swerve.CANIds.kBackRightEncoder);
    private final SwerveModule frontLeft = new SwerveModule(Swerve.CANIds.kFrontLeftDrive, Swerve.CANIds.kFrontLeftTurn, Swerve.CANIds.kFrontLeftEncoder);

    private SwerveModuleState[] moduleStates;

    private final AHRS gyro = new AHRS(AHRS.NavXComType.kMXP_SPI);

    private final SwerveDriveKinematics kinematics = new SwerveDriveKinematics(
        new Translation2d(0.315, -0.315), // Front Left Location
        new Translation2d(0.315, 0.315), // Front Right
        new Translation2d(-0.315, -0.315), // Back Left
        new Translation2d(-0.315, 0.315) // Back Right
    );

    private final SwerveDriveOdometry odometry = new SwerveDriveOdometry(
        kinematics,
        gyro.getRotation2d(), 
        new SwerveModulePosition[]{
            frontLeft.getPosition(),
            frontRight.getPosition(),
            backLeft.getPosition(),
            backRight.getPosition()
        });

    public Drivetrain(){
        gyro.reset();
        setupDashboard();
    }

    void setupDashboard(){
        SmartDashboard.putData("back left", backLeft);
        SmartDashboard.putData("back right", backRight);
        SmartDashboard.putData("front left", frontLeft);
        SmartDashboard.putData("front right", frontRight);
    }

    public void setSpeeds( ChassisSpeeds speeds ){
        moduleStates = kinematics.toSwerveModuleStates(speeds);
        SwerveDriveKinematics.desaturateWheelSpeeds(moduleStates, Movement.kMaxSpeed);
        frontLeft.setDesiredState(moduleStates[0]);
        frontRight.setDesiredState(moduleStates[1]);
        backLeft.setDesiredState(moduleStates[2]);
        backRight.setDesiredState(moduleStates[3]);
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

}
