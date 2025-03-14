package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private final SparkMax climberMotor;
    private final RelativeEncoder encoder;

    public Climber() {
        climberMotor = new SparkMax(Constants.Climber.kClimberMotorCANID, SparkLowLevel.MotorType.kBrushless);
        encoder = climberMotor.getEncoder();
        setupDashboard();
    }
    private void setupDashboard() {
       SmartDashboard.putData("climber/climber_up", this.climberUpCommand());
       SmartDashboard.putData("climber/climber_down", this.climberDownCommand());
    }
    public Command climberUpCommand() {
        return run(() -> {
            climberMotor.set(Constants.Climber.kClimberSpeed);
        });
    }
    public Command climberDownCommand() {
        return run(() -> {
            climberMotor.set(Constants.Climber.kClimberSpeed * -1);
        });
    }
    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("Climber");
    }
}
