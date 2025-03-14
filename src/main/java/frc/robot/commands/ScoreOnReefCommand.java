package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.CoralIntake;

public class ScoreOnReefCommand extends SequentialCommandGroup {

    public ScoreOnReefCommand( CoralIntake _intake, Elevator _elevator, int _level ) {
        addCommands(
            new WaitUntilCommand( _intake::hasCoral ),
            _elevator.setHeightCommand( Constants.Elevator.Heights.ReefLevel[_level]), //Get the reef heigh given the level.
            _intake.deliverCoralCommand( _level == 0 )
        );
    }
}
