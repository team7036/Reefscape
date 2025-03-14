package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.Drivetrain.Movement;
import frc.robot.Constants.Drivetrain.Swerve;

public class SwerveModule extends SubsystemBase {

    private final SparkMax driveMotor, turnMotor;
    private final SparkMaxConfig driveConfig, turnConfig;
    private final RelativeEncoder driveEncoder;
    private final CANcoder turnEncoder;
    private final PIDController drivePID = new PIDController(
        Swerve.PID.Drive.kP,
        Swerve.PID.Drive.kI,
        Swerve.PID.Drive.kD
        );
    private final PIDController turnPID = new PIDController(
        Swerve.PID.Turn.kP, 
        Swerve.PID.Turn.kI, 
        Swerve.PID.Turn.kD
    );
    private final SimpleMotorFeedforward driveFeedForward = new SimpleMotorFeedforward(Swerve.FeedForward.Drive.kS, Swerve.FeedForward.Drive.kV);
    private final SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(Swerve.FeedForward.Turn.kS, Swerve.FeedForward.Turn.kV);
    

    public SwerveModule(int driveMotorId, int turnMotorId, int turnEncoderId){
        driveMotor = new SparkMax(driveMotorId, MotorType.kBrushless);
        driveConfig = new SparkMaxConfig();
        turnMotor = new SparkMax(turnMotorId, MotorType.kBrushless);
        turnConfig = new SparkMaxConfig();
        driveEncoder = driveMotor.getEncoder();
        turnEncoder = new CANcoder(turnEncoderId);
        turnPID.enableContinuousInput(-Math.PI, Math.PI);
        configureMotors();
    }

    private void configureMotors(){
        turnConfig.smartCurrentLimit(40);
        driveConfig.smartCurrentLimit(40);
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(getTurnPosition()));
    }

    public SwerveModulePosition getPosition(){
        return new SwerveModulePosition(driveEncoder.getPosition(), new Rotation2d(getTurnPosition()));
    }

    public double getTurnPosition() {
        return turnEncoder.getAbsolutePosition().getValueAsDouble() * 2 * Math.PI;
    }

    public double getDriveSpeed(){
        return driveEncoder.getVelocity() * Movement.kDriveVelocityConversionFactor;
    }

    public void setDesiredState(SwerveModuleState desired) {
        Rotation2d rotation = new Rotation2d(getTurnPosition());
        desired.optimize(rotation);
        desired.speedMetersPerSecond *= desired.angle.minus(rotation).getCos();
        setDriveSpeed(desired.speedMetersPerSecond);
        setTurnPosition(desired.angle.getRadians());
    }

    public void setDriveSpeed(double speedMetersPerSecond){
        final double driveOutput = drivePID.calculate(getDriveSpeed(), speedMetersPerSecond);
        final double driveFeed = driveFeedForward.calculate(speedMetersPerSecond);
        driveMotor.setVoltage(driveOutput + driveFeed);
    }

    public void setTurnPosition(double angleRadians){
        final double turnOutput = turnPID.calculate(getTurnPosition(), angleRadians);
        final double turnFeed = turnFeedforward.calculate(turnPID.getSetpoint());
        turnMotor.setVoltage(turnOutput + turnFeed);
    }

    private double getDriveTarget(){
        return drivePID.getSetpoint();
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("turn/position", this::getTurnPosition, null);
        builder.addDoubleProperty("drive/speed", this::getDriveSpeed, null);
        builder.addDoubleProperty("drive/target", this::getDriveTarget, null);
    }
}
