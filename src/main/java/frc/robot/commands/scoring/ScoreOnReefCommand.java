package frc.robot.commands.scoring;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;

enum ReefLevel {
    L1,
    L2,
    L3,
    L4
}

public class ScoreOnReefCommand extends Command {
    private Intake intake;
    private Elevator elevator;
    public ScoreOnReefCommand( Intake _intake, Elevator _elevator, ReefLevel _level ){
        
    }
}
