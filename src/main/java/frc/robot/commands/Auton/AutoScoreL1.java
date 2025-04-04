package frc.robot.commands.Auton;

import frc.robot.Constants;
import frc.robot.RobotContainer;

public class AutoScoreL1 {
    public AutoScoreL1() {
        RobotContainer.getElevator().setHeightCommand(Constants.Elevator.Heights.ReefLevel[1]);
        RobotContainer.getCoralIntake().deliverCoralCommand(true);
    }
}
