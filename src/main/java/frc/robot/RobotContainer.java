// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  public static final Vision visionSubsystem = new Vision();
  
  public RobotContainer() {
    drivetrainSubsystem.setDefaultCommand(
      new DrivetrainDefaultCommand(
        drivetrainSubsystem,
              driverController::getLeftY,
              driverController::getLeftX,
              driverController::getRightX
      )
    );

    elevatorSubsystem.setDefaultCommand( elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kIntaking ) );

    //add bindings
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
    //Elevator
    operatorController.povUp().onTrue( elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL1));
    operatorController.povRight().onTrue( elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL2));
    operatorController.povDown().onTrue( elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL3));
    operatorController.povLeft().onTrue( elevatorSubsystem.setHeightCommand(Constants.Elevator.Heights.kL4));
    //A,B,X,Y
    operatorController.a().onTrue( elevatorSubsystem.resetZeroHeightCommand());
    //Coral
    operatorController.leftBumper().onTrue( intakeSubystem.intakeCoralCommand());
    operatorController.leftTrigger(Constants.Bindings.triggerDownThreshhold).onTrue( intakeSubystem.deliverCoralCommand());
    //Algae
    operatorController.rightBumper().onTrue( intakeSubystem.intakeAlgaeCommand());
    operatorController.rightTrigger(Constants.Bindings.triggerDownThreshhold).onTrue( intakeSubystem.deliverAlgaeCommand());
    setupDashboard();
    
  }

  void setupDashboard(){
    SmartDashboard.putData("drivetrain", drivetrainSubsystem);
    SmartDashboard.putData("elevator", elevatorSubsystem);
    SmartDashboard.putData("intake", intakeSubystem);
  }
}
