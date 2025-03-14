package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class UpdateDrivePoseCommand extends Command {
    private Drivetrain drive;
    private Vision vision;

    public UpdateDrivePoseCommand(Drivetrain drive, Vision vision) {
        addRequirements(drive, vision);
        this.drive = drive;
        this.vision = vision;
    }

    @Override
    public void execute() {
        drive.poseEstimator.addVisionMeasurement(vision.getRobotPose2d(), Timer.getFPGATimestamp());
    }

    
}
