package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class AlgaeIntake extends SubsystemBase {

    private final SparkMax motor;
    private final RelativeEncoder encoder;
    private final PIDController pid;

    public AlgaeIntake(){
        motor = new SparkMax(Constants.Intake.kAlgaeMotorCANId, SparkLowLevel.MotorType.kBrushless);
        encoder = motor.getEncoder();
        pid = new PIDController(1, 0, 0);
        this.setDefaultCommand(this.clawUpCommand());
    }

    public boolean hasAlgae(){
        return false;
    }

    public Command clawUpCommand(){
        return run(()->{
            double pidInput = pid.calculate(encoder.getPosition(), 0);
            motor.setVoltage(pidInput);
        });
    }

    public Command clawDownCommand(){
        return run(()->{
            double pidInput = pid.calculate(encoder.getPosition(), -3.3);
            motor.setVoltage(pidInput);
        });
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("IntakeSubsystem");
        SmartDashboard.putData("algaeIntake/pid", pid);
        builder.addBooleanProperty("hasAlgae", this::hasAlgae, null);
        builder.addDoubleProperty("position", encoder::getPosition, null);
    }
}
