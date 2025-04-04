package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralIntake;

public class IntakeCoralCommand extends SequentialCommandGroup {
    public IntakeCoralCommand(
        CoralIntake intake,
        Elevator elevator){
        addCommands(
            elevator.setHeightCommand(Constants.Elevator.Heights.kCoralIntake),
            intake.intakeCoralCommand()
        );
    }
}
