// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.elevator.SetElevatorHeightCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.testing.TestingMappings;

public class TestContainer {

  private final CommandXboxController driverController = new CommandXboxController(Constants.Controllers.kDriverPort);
  private static final TestingMappings elevatorTestingMappings = new TestingMappings.MappingsBuilder(false).addMapping(Constants.Elevator.motorCanId, Constants.TestMotorControllers.testSparkMax1).build();
  public static final Elevator elevatorSubsystem = new Elevator(elevatorTestingMappings);

  public TestContainer() {}
  public void runTest() {
    elevatorSubsystem.setDefaultCommand(
      new SetElevatorHeightCommand(elevatorSubsystem, 0)
    );
  }
}
