// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.elevator.SetElevatorHeightCommand;
import frc.robot.subsystems.Elevator;

public class TestContainer {

  private final CommandXboxController driverController = new CommandXboxController(Constants.Controllers.kDriverPort);
  public static final Elevator elevatorSubsystem = new Elevator();

  public TestContainer() {

    elevatorSubsystem.setDefaultCommand(
      new SetElevatorHeightCommand(elevatorSubsystem, 0)
    );
    
  }
}
