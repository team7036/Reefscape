// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.drivetrain.DrivetrainDefaultCommand;
import frc.robot.commands.elevator.SetElevatorHeightCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class RobotContainer {

  private final CommandXboxController driverController = new CommandXboxController(Constants.Controllers.kDriverPort);
  public static final Drivetrain drivetrainSubsystem = new Drivetrain();
  public static final Elevator elevatorSubsystem = new Elevator();

  public RobotContainer() {

    drivetrainSubsystem.setDefaultCommand(
      new DrivetrainDefaultCommand(
        drivetrainSubsystem,
        () -> driverController.getLeftY(),  
        () -> driverController.getLeftX(),
        () -> driverController.getRightX()
      )
    );

    elevatorSubsystem.setDefaultCommand(
      new SetElevatorHeightCommand(elevatorSubsystem, 0)
    );


    
  }
}
