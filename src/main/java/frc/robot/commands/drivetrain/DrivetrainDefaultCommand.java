package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.Drivetrain.Movement;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class DrivetrainDefaultCommand extends Command {

  private Drivetrain drive;

  ChassisSpeeds speeds = new ChassisSpeeds();

  DoubleSupplier leftXSupplier;
  DoubleSupplier leftYSupplier;
  DoubleSupplier rightXSupplier;

  SlewRateLimiter xSpeedLimiter = new SlewRateLimiter(Movement.kSlewRateLimitX);
  SlewRateLimiter ySpeedLimiter = new SlewRateLimiter(Movement.kSlewRateLimitY);
  SlewRateLimiter rotSpeedLimiter = new SlewRateLimiter(Movement.kSlewRateLimitRot);

  /** Creates a new DrivetrainDefaultCommand. */
  public DrivetrainDefaultCommand(Drivetrain _drivetrain, DoubleSupplier _leftX, DoubleSupplier _leftY,
      DoubleSupplier _rightX) {
    drive = _drivetrain;
    leftXSupplier = _leftX;
    leftYSupplier = _leftY;
    rightXSupplier = _rightX;
    this.setName("DrivetrainDefaultCommand");
    addRequirements(drive);
  }

  @Override
  public void execute() {

    double xSpeed = Movement.kMaxSpeed
        * xSpeedLimiter.calculate(MathUtil.applyDeadband(leftXSupplier.getAsDouble(), 0.04));
    double ySpeed = Movement.kMaxSpeed
        * -ySpeedLimiter.calculate(MathUtil.applyDeadband(leftYSupplier.getAsDouble(), 0.04));
    double rot = Movement.kMaxRotation
        * -rotSpeedLimiter.calculate(MathUtil.applyDeadband(rightXSupplier.getAsDouble(), 0.04));
    drive.setSpeeds(new ChassisSpeeds(xSpeed, ySpeed, rot));

  }
}
