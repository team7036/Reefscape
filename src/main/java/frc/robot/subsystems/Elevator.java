package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

    private final SparkMax motor;
    private final RelativeEncoder encoder;
    private final ElevatorFeedforward feedforward;
    private final ProfiledPIDController pid;

    public Elevator() {
        motor = new SparkMax(
            Constants.Elevator.motorCanId, 
            SparkLowLevel.MotorType.kBrushless
        );
        encoder = motor.getEncoder();
        pid = new ProfiledPIDController(
            Constants.Elevator.kP, 
            Constants.Elevator.kI, 
            Constants.Elevator.kD, 
            new TrapezoidProfile.Constraints(
                Constants.Elevator.maxVelocity, 
                Constants.Elevator.maxAcceleration
            )
        );
        feedforward = new ElevatorFeedforward(
            Constants.Elevator.kS,
            Constants.Elevator.kG,
            Constants.Elevator.kV,
            Constants.Elevator.kA
        );
    }

    public double getHeight(){
        return this.encoder.getPosition() * Constants.Elevator.conversion;
    }

    public Command setHeightCommand( double height ){
        return run(()->{
            double pidInput = pid.calculate(getHeight(), new TrapezoidProfile.State(height, 0));
            double ffInput = feedforward.calculate( pid.getSetpoint().velocity );
            motor.setVoltage( pidInput + ffInput);
        })
        ;
    }

    public Command stopCommand(){
        return run(()->motor.set(0));
    }

    public Command resetZeroHeightCommand(){
        return Commands.parallel(
            runOnce(()->motor.setVoltage(-2)),
            Commands.waitUntil(motor.getReverseLimitSwitch()::isPressed).andThen(()->{
                motor.setVoltage(0);
                encoder.setPosition(0);
            })
        );
    }

    
    private double getTargetHeight(){
        return this.pid.getGoal().position;
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.addDoubleProperty("targetheight", this::getTargetHeight, null);
        builder.addDoubleProperty("error", this.pid::getPositionError, null);
        builder.addDoubleProperty("power", this.motor::getAppliedOutput, null);
    }


}
