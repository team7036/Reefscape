// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.drivetrain.DrivetrainDefaultCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivetrain.Drivetrain;
public class RobotContainer {

  private static final CommandXboxController driverController = new CommandXboxController(Constants.Controllers.kDriverPort);
  private static final CommandXboxController operatorController = new CommandXboxController(Constants.Controllers.kOperatorPort);
  private static final Drivetrain drivetrainSubsystem = new Drivetrain(true);
  private static final Elevator elevatorSubsystem = new Elevator();
  private static final Intake intakeSubystem = new Intake();
    //public static final Vision visionSubsystem = new Vision();
    
    public RobotContainer() {  
      //Elevator
      operatorBindings();
      driverBindings();
      setupDashboard();
    }
  
    void driverBindings(){
      drivetrainSubsystem.setDefaultCommand(
        new DrivetrainDefaultCommand(
          drivetrainSubsystem,
          driverController::getLeftY,
          driverController::getLeftX,
          driverController::getRightX
        )
      );
      driverController.back().onTrue(new InstantCommand(()->drivetrainSubsystem.resetGyro()));
      driverController.start().onTrue(new InstantCommand(()->{
        drivetrainSubsystem.fieldRelative = !drivetrainSubsystem.fieldRelative;
    }));
  }

  void operatorBindings(){
    // Elevator
    // - Coral -
    elevatorSubsystem.setDefaultCommand( elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kCoralStation ) );
    operatorController.y().whileTrue(elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL1));
    operatorController.b().whileTrue(elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL2));
    operatorController.a().whileTrue(elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL3));
    operatorController.x().whileTrue(elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL4));
    operatorController.rightBumper().whileTrue(intakeSubystem.deliverCoralCommand().onlyIf(()->elevatorSubsystem.coralReefReady()));
    operatorController.leftBumper().whileTrue(intakeSubystem.intakeCoralCommand().onlyIf(()->elevatorSubsystem.coralIntakeReady()));
  }

  void setupDashboard(){
    SmartDashboard.putData("drivetrain", drivetrainSubsystem);
    SmartDashboard.putData("elevator", elevatorSubsystem);
    SmartDashboard.putData("intake", intakeSubystem);
    //SmartDashboard.putData("vision", visionSubsystem);
  }
}
