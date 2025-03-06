// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.drivetrain.DrivetrainDefaultCommand;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.drivetrain.Drivetrain;
import frc.robot.util.XboxControllerBindings;

public class RobotContainer {

  private final CommandXboxController driverController = new CommandXboxController(Constants.Controllers.kDriverPort);
  private final XboxControllerBindings bindings = new XboxControllerBindings(driverController.getHID());
  public static final Drivetrain drivetrainSubsystem = new Drivetrain(true);
  public static final Elevator elevatorSubsystem = new Elevator();
  public static final Intake intakeSubystem = new Intake();
  
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
    //ELEVATOR
    bindings.addBinding(XboxControllerBindings.D_PAD_UP, (xboxController) -> elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kL1 ));
    bindings.addBinding(XboxControllerBindings.D_PAD_RIGHT, (xboxController) -> elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kL2 ));
    bindings.addBinding(XboxControllerBindings.D_PAD_DOWN, (xboxController) -> elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kL3 ));
    bindings.addBinding(XboxControllerBindings.D_PAD_LEFT, (xboxController) -> elevatorSubsystem.setHeightCommand( Constants.Elevator.Heights.kL4 ));
    //CORAL
    bindings.addBinding(XboxControllerBindings.LEFT_BUMPER_DOWN, (xboxController) -> intakeSubystem.intakeCoralCommand());
    bindings.addBinding(XboxControllerBindings.LEFT_TRIGGER_DOWN, (xboxController) -> intakeSubystem.deliverCoralCommand());
    //ALGAE
    bindings.addBinding(XboxControllerBindings.RIGHT_BUMPER_DOWN, (xboxController) -> intakeSubystem.intakeAlgaeCommand());
    bindings.addBinding(XboxControllerBindings.RIGHT_TRIGGER_DOWN, (xboxController) -> intakeSubystem.deliverAlgaeCommand());
    //A,B,X,Y
    bindings.addBinding(XboxControllerBindings.A_DOWN, (xboxController) -> elevatorSubsystem.resetZeroHeightCommand());
    //bindings.addBinding(XboxControllerBindings.B_DOWN, (xboxController) -> {});
    //bindings.addBinding(XboxControllerBindings.X_DOWN, (xboxController) -> {});
    //bindings.addBinding(XboxControllerBindings.Y_DOWN, (xboxController) -> {});
    setupDashboard();
    
  }

  void setupDashboard(){
    SmartDashboard.putData("drivetrain", drivetrainSubsystem);
    SmartDashboard.putData("elevator", elevatorSubsystem);
    SmartDashboard.putData("intake", intakeSubystem);
  }

}
