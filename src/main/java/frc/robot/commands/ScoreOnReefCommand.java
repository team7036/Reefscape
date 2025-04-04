package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralIntake;

public class ScoreOnReefCommand extends SequentialCommandGroup {

    public ScoreOnReefCommand(
        CoralIntake intake,
        Elevator elevator,
        int level) {
        
        addCommands(
            new WaitUntilCommand(intake::hasCoral),
            elevator.setHeightCommand(Constants.Elevator.Heights.ReefLevel[level]), //Get the reef heigh given the level.
            intake.deliverCoralCommand(level == 0)
        );
    }
}
