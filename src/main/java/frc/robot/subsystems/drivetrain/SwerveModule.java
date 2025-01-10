package frc.robot.subsystems.drivetrain;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class SwerveModule{

    private static SparkMax driveMotor;
    private static SparkMax turnMotor;
    private static RelativeEncoder driveEncoder;
    private static CANcoder turnEncoder;
    private static PIDController drivePID = new PIDController(0, 0, 0);
    private static PIDController turnPID = new PIDController(0, 0, 0);
    private static SimpleMotorFeedforward driveFeedForward = new SimpleMotorFeedforward(0, 0);
    private static SimpleMotorFeedforward turnFeedforward = new SimpleMotorFeedforward(0, 0);
    

    public SwerveModule(int driveId, int turnId){
        driveMotor = new SparkMax(driveId, MotorType.kBrushless);
        turnMotor = new SparkMax(turnId, MotorType.kBrushless);
        driveEncoder = driveMotor.getEncoder();
        turnEncoder = new CANcoder(turnId);
    }

    public SwerveModuleState getState(){
        return new SwerveModuleState(driveEncoder.getVelocity(), new Rotation2d(this.getTurnPosition()));
    }
    public double getTurnPosition() {
        return turnEncoder.getAbsolutePosition().getValueAsDouble() * 2 * Math.PI;
    }
    public void setDesiredState(SwerveModuleState desired) {
        Rotation2d rotation = new Rotation2d(this.getTurnPosition());
        desired.optimize(rotation);
        desired.speedMetersPerSecond *= desired.angle.minus(rotation).getCos();

        setDriveSpeed(desired.speedMetersPerSecond);
        setTurnPosition(desired.angle.getRadians());
    }

    public void setDriveSpeed(double speedMetersPerSecond){
        final double driveOutput = drivePID.calculate(driveEncoder.getVelocity(), speedMetersPerSecond);
        final double driveFeed = driveFeedForward.calculate(speedMetersPerSecond);
        driveMotor.setVoltage(driveOutput + driveFeed);
    }

    public void setTurnPosition(double angleRadians){
        final double turnOutput = turnPID.calculate(getTurnPosition(), angleRadians);
        final double turnFeed = turnFeedforward.calculate(turnPID.getSetpoint());
        turnMotor.setVoltage(turnOutput+turnFeed);
    }
}
