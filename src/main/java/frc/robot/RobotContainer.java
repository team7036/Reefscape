// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.drivetrain.DrivetrainDefaultCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Vision;
import frc.robot.subsystems.drivetrain.Drivetrain;

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
    operatorController.a().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(0)) );
    operatorController.b().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(1)) );
    operatorController.x().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(2)) );   
    operatorController.y().whileTrue( elevatorSubsystem.setHeightCommand(reefHeights.get(3)) );
    // A,B,X,Y
    //Coral
    operatorController.rightTrigger(Constants.Bindings.triggerDownThreshhold).onTrue( intakeSubystem.intakeCoralCommand().onlyIf(()->(
      elevatorSubsystem.pid.atSetpoint() && elevatorSubsystem.pid.getSetpoint().position == Constants.Elevator.Heights.kCoralStation
    )) );
    operatorController.rightBumper().onTrue( intakeSubystem.deliverCoralCommand().onlyIf(()->(
      //reefHeights (elevatorSubsystem.pid.getSetpoint().position)
      elevatorSubsystem.pid.atSetpoint() && reefHeights.contains(elevatorSubsystem.pid.getSetpoint().position)
    )) );
  }

  void setupDashboard(){
    SmartDashboard.putData("drivetrain", drivetrainSubsystem);
    SmartDashboard.putData("elevator", elevatorSubsystem);
    SmartDashboard.putData("intake", intakeSubystem);
  }
}

