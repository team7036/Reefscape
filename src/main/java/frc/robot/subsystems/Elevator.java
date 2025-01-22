package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

    private final SparkMax elevatorMotor;
    private final RelativeEncoder elevatorEncoder;
    private final ElevatorFeedforward elevatorFeedForward;
    private final PIDController elevatorPID;


    public Elevator() {
        elevatorMotor = new SparkMax(Constants.Elevator.motorCanId , SparkLowLevel.MotorType.kBrushless);
        elevatorEncoder = elevatorMotor.getEncoder();
        elevatorPID = new PIDController(Constants.Elevator.kP, Constants.Elevator.kI, Constants.Elevator.kD);
        elevatorFeedForward = new ElevatorFeedforward(Constants.Elevator.kS, Constants.Elevator.kG, Constants.Elevator.kV);
    }

    public boolean atSetpoint(){
        return elevatorPID.atSetpoint();
    }

    public double getHeight(){
        return elevatorEncoder.getPosition();
    }

    public void setDesiredHeight(double desiredHeight){
        double pidInput = elevatorPID.calculate(getHeight(), desiredHeight);
        double ffInput = elevatorFeedForward.calculate(elevatorPID.getSetpoint());
        elevatorMotor.setVoltage(ffInput + pidInput);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("height", this::getHeight, null);
    }


}
