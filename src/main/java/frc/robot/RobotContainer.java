// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.PoundInch;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.drivetrain.DrivetrainDefaultCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.util.ControllerUtil;
import frc.robot.util.ControllerUtil.LetterButtons;

public class RobotContainer {

  private final CommandXboxController driverController = new CommandXboxController(Constants.Controllers.kDriverPort);
  private final CommandXboxController operatorController = new CommandXboxController(Constants.Controllers.kOperatorPort);
  public static final Drivetrain drivetrainSubsystem = new Drivetrain(true);
  public static final Elevator elevatorSubsystem = new Elevator();
  public static final Intake intakeSubystem = new Intake();
  //public static final Vision visionSubsystem = new Vision();
  
  public RobotContainer() {

    drivetrainSubsystem.setDefaultCommand(
      new DrivetrainDefaultCommand(
        drivetrainSubsystem,
              driverController::getLeftY,
              driverController::getLeftX,
              driverController::getRightX
      )
    );

    elevatorSubsystem.setDefaultCommand( elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kCoralStation ) );

    //Elevator
    operatorKeybindings();
    setupDashboard();
    
  }

  void operatorKeybindings(){

    List<Double> reefHeights = List.of(
      Constants.Elevator.Heights.kL1,
      Constants.Elevator.Heights.kL2,
      Constants.Elevator.Heights.kL3,
      Constants.Elevator.Heights.kL4
    );
    List<Double> algaeHeights = List.of(
      //TODO: handle algae heights
      Constants.Elevator.Heights.kL1 + 0.0,
      Constants.Elevator.Heights.kL2 + 0.0,
      Constants.Elevator.Heights.kL3 + 0.0,
      Constants.Elevator.Heights.kL4 + 0.0
    );

    /*
    BINDINGS:
    - DPAD: elevator
    - LEFT BUMPER: coral intake
    - LEFT TRIGGER: coral delivery
    - RIGHT BUMPER: algae intake
    - RIGHT TRIGGER: algae delivery
    - A: zero elevator
    - B: tbd
    - X: tbd
    - Y: tbd
    */
    // Elevator
    // - Coral -
    operatorController.y().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(0)).onlyIf(() -> (
      ControllerUtil.isPovNeutral(operatorController) && ControllerUtil.areABXYUp(operatorController, LetterButtons.A)
    )) );
    operatorController.b().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(1)).onlyIf(() -> (
      ControllerUtil.isPovNeutral(operatorController) && ControllerUtil.areABXYUp(operatorController, LetterButtons.B)
    )) );
    operatorController.a().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(2)).onlyIf(() -> (
      ControllerUtil.isPovNeutral(operatorController) && ControllerUtil.areABXYUp(operatorController, LetterButtons.X)
    )) );   
    operatorController.x().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(3)).onlyIf(() -> (
      ControllerUtil.isPovNeutral(operatorController) && ControllerUtil.areABXYUp(operatorController, LetterButtons.Y)
    )) );
    // - Algae -
    operatorController.povUp().whileTrue( elevatorSubsystem.setHeightCommand(algaeHeights.get(0)).onlyIf(() -> (
      ControllerUtil.areABXYUp(operatorController)
    )) );
    operatorController.povLeft().whileTrue( elevatorSubsystem.setHeightCommand(algaeHeights.get(1)).onlyIf(() -> (
      ControllerUtil.areABXYUp(operatorController)
    )) );
    operatorController.povDown().whileTrue( elevatorSubsystem.setHeightCommand(algaeHeights.get(2)).onlyIf(() -> (
      ControllerUtil.areABXYUp(operatorController)
    )) );
    operatorController.povRight().whileTrue( elevatorSubsystem.setHeightCommand(algaeHeights.get(3)).onlyIf(() -> (
      ControllerUtil.areABXYUp(operatorController)
    )) );
    // A,B,X,Y
    //Coral
    operatorController.rightTrigger(Constants.Bindings.triggerDownThreshhold).onTrue( intakeSubystem.intakeCoralCommand().onlyIf(()->(
      elevatorSubsystem.pid.atSetpoint() && elevatorSubsystem.pid.getSetpoint().position == Constants.Elevator.Heights.kCoralStation
    )) );
    operatorController.rightBumper().onTrue( intakeSubystem.deliverCoralCommand().onlyIf(()->(
      elevatorSubsystem.pid.atSetpoint() && reefHeights.contains(elevatorSubsystem.pid.getSetpoint().position)
    )) );
    //Algae
    operatorController.leftTrigger(Constants.Bindings.triggerDownThreshhold).onTrue( intakeSubystem.intakeAlgaeCommand().onlyIf(() -> (
      elevatorSubsystem.pid.atSetpoint()
    )) );
    operatorController.leftBumper().onTrue( intakeSubystem.deliverAlgaeCommand().onlyIf(()->(
      elevatorSubsystem.pid.atSetpoint() && algaeHeights.contains(elevatorSubsystem.pid.getSetpoint().position)
    )) );
  }

  void setupDashboard(){
    SmartDashboard.putData("drivetrain", drivetrainSubsystem);
    SmartDashboard.putData("elevator", elevatorSubsystem);
    SmartDashboard.putData("intake", intakeSubystem);
    //SmartDashboard.putData("vision", visionSubsystem);
  }
}
