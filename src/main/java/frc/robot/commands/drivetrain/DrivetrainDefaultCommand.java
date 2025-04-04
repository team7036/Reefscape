package frc.robot.commands.drivetrain;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.Drivetrain.Movement;
import frc.robot.subsystems.drivetrain.Drivetrain;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DrivetrainDefaultCommand extends Command {

  private Drivetrain drive;

  private DoubleSupplier leftXSupplier, leftYSupplier, rightXSupplier;
  private BooleanSupplier halfSupplier;

  private static SlewRateLimiter xSpeedLimiter = new SlewRateLimiter(Movement.kSlewRateLimitX);
  private static SlewRateLimiter ySpeedLimiter = new SlewRateLimiter(Movement.kSlewRateLimitY);
  private static SlewRateLimiter rotSpeedLimiter = new SlewRateLimiter(Movement.kSlewRateLimitRot);

  /** Creates a new DrivetrainDefaultCommand. */
  public DrivetrainDefaultCommand(
    Drivetrain drive,
    DoubleSupplier leftX,
    DoubleSupplier leftY,
    DoubleSupplier rightX,
    BooleanSupplier halfSpeed) {
    this.setName("DrivetrainDefaultCommand");
    addRequirements(drive);
    this.drive = drive;
    leftXSupplier = leftX;
    leftYSupplier = leftY;
    rightXSupplier = rightX;
    halfSupplier = halfSpeed;
  }

  @Override
  public void execute() {

    double xSpeed = (halfSupplier.getAsBoolean() ? Movement.kMaxSpeed / 2 : Movement.kMaxSpeed)
        * xSpeedLimiter.calculate(MathUtil.applyDeadband(leftXSupplier.getAsDouble(), 0.04));
    double ySpeed = (halfSupplier.getAsBoolean() ? Movement.kMaxSpeed / 2 : Movement.kMaxSpeed)
        * -ySpeedLimiter.calculate(MathUtil.applyDeadband(leftYSupplier.getAsDouble(), 0.04));
    double rot = (halfSupplier.getAsBoolean() ? Movement.kMaxRotation / 2 : Movement.kMaxRotation)
        * -rotSpeedLimiter.calculate(MathUtil.applyDeadband(rightXSupplier.getAsDouble(), 0.04));
  
    drive.setSpeeds(new ChassisSpeeds(xSpeed, ySpeed, rot), false);

  }
}
