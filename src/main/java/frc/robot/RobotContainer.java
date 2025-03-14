// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pathplanner.lib.auto.AutoBuilder;

//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.IntakeCoralCommand;
import frc.robot.commands.ScoreOnReefCommand;
import frc.robot.commands.drivetrain.DrivetrainDefaultCommand;
import frc.robot.subsystems.AlgaeIntake;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralIntake;
//import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class RobotContainer {
  private static final CommandXboxController driverController = new CommandXboxController(
      Constants.Controllers.kDriverPort);
  private static final CommandXboxController operatorController = new CommandXboxController(
      Constants.Controllers.kOperatorPort);
  private static final Drivetrain drivetrainSubsystem = new Drivetrain(true);
  private static final Elevator elevatorSubsystem = new Elevator();
  private static final CoralIntake coralIntakeSubsystem = new CoralIntake();
  private static final AlgaeIntake algaeIntakeSubsystem = new AlgaeIntake();
  //private static final Climber climberSubsystem = new Climber();
  //public static final Vision visionSubsystem = new Vision();

  // Pathplanner
  private final SendableChooser<Command> autoPathChooser;

  public RobotContainer() {

    // Elevator
    operatorBindings();
    driverBindings();

    // Pathplanner
    boolean isCompetition = false; // Set to true on comp
    //If it is a competition, only provide competition paths. Names should start with comp_PATH NAME
    autoPathChooser = AutoBuilder.buildAutoChooserWithOptionsModifier(
        (stream) -> isCompetition
            ? stream.filter(auto -> auto.getName().startsWith("comp_"))
            : stream);

    setupDashboard();
  }

  private void driverBindings() {
    drivetrainSubsystem.setDefaultCommand(
        new DrivetrainDefaultCommand(
            drivetrainSubsystem,
            driverController::getLeftY,
            driverController::getLeftX,
            driverController::getRightX));
    driverController.back().onTrue(new InstantCommand(() -> drivetrainSubsystem.resetGyro()));
    driverController.start().onTrue(new InstantCommand(() -> {
      drivetrainSubsystem.fieldRelative = !drivetrainSubsystem.fieldRelative;
    }));
  }

  private void operatorBindings() {
    // Elevator
    operatorController.y().whileTrue( new ScoreOnReefCommand( coralIntakeSubsystem, elevatorSubsystem, 0 ) );
    operatorController.b().whileTrue( new ScoreOnReefCommand( coralIntakeSubsystem, elevatorSubsystem, 1 ) );
    operatorController.a().whileTrue( new ScoreOnReefCommand( coralIntakeSubsystem, elevatorSubsystem, 2 ) );
    operatorController.x().whileTrue( new ScoreOnReefCommand( coralIntakeSubsystem, elevatorSubsystem, 3 ) );
    operatorController.rightBumper().whileTrue(new IntakeCoralCommand( coralIntakeSubsystem, elevatorSubsystem ));
    operatorController.leftBumper().whileTrue( algaeIntakeSubsystem.clawDownCommand());
    // Climber
    // operatorController.povUp().whileTrue( climberSubsystem.climberUpCommand() );
    // operatorController.povDown().whileTrue( climberSubsystem.climberDownCommand() );
  }

  void setupDashboard() {
    SmartDashboard.putData("drivetrain", drivetrainSubsystem);
    SmartDashboard.putData("elevator", elevatorSubsystem);
    SmartDashboard.putData("coralIntake", coralIntakeSubsystem);
    SmartDashboard.putData("algaeIntake", algaeIntakeSubsystem);
    //SmartDashboard.putData("vision", visionSubsystem);

    // Pathplanner Auto Path Chooser
    SmartDashboard.putData("Auto Chooser", autoPathChooser);
  }

  public Command getAutoCommand() {
    return autoPathChooser.getSelected();
  }
  // public Command updateRobotPoseCommand() {
  //   return Commands.run(() -> {
  //     if ( visionSubsystem.detectsAprilTag() ){
  //       drivetrainSubsystem.poseEstimator.addVisionMeasurement(visionSubsystem.getRobotPose2d(), Timer.getFPGATimestamp());
  //     }
  //   },
  //   drivetrainSubsystem,
  //   visionSubsystem);
  // }
}
