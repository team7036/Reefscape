package frc.robot.commands.elevator;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Elevator;

public class SetElevatorHeightCommand extends Command {

    private Elevator elevator;
    private double height;

    public SetElevatorHeightCommand(Elevator _elevator, double _height){
        elevator = _elevator;
        height = _height;
        this.setName("SetHeightCommand");
        addRequirements(elevator);
    }

    @Override
    public void execute() {
        elevator.setDesiredHeight(height);
    }

    @Override
    public boolean isFinished(){
        return elevator.atSetpoint();   
    }

    @Override
    public void end(boolean interupted){
          
    }
    
}
