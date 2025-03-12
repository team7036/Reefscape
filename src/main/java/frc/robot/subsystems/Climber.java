package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Climber extends SubsystemBase {
    private final SparkMax climberMotor;
    private final RelativeEncoder encoder;
    private boolean isUp;

    public Climber() {
        climberMotor = new SparkMax(Constants.Climber.kClimberMotorCANID, SparkLowLevel.MotorType.kBrushless);
        encoder = climberMotor.getEncoder();
        isUp = false;
        setupDashboard();
    }
    private void setupDashboard() {
        SmartDashboard.putData("climber/climberUp", this.climberUpCommand());
        SmartDashboard.putData("climber/climberDown", this.climberDownCommand());
        SmartDashboard.putData("climber/resetClimber", this.resetClimberCommand());
        SmartDashboard.putData("climber/smartClimberCommand", this.smartClimberCommand());
    }
    public Command climberUpCommand() {
        return run(() -> {
            climberMotor.set(Constants.Climber.kClimberUpSpeed);
            //TODO: implement sensor later
        });
    }
    public Command climberDownCommand() {
        return run(() -> {
            climberMotor.set(Constants.Climber.kClimberUpSpeed * -1);
            //TODO: implement sensor later
        });
    }
    public Command resetClimberCommand() {
        return Commands.parallel(
            //Fix resetSpeed later (Once finished)
            runOnce(() -> climberMotor.setVoltage(-2)),
            //Fix True to match sensor (Once added)
            Commands.waitUntil(() -> true).andThen(() -> {
                climberMotor.setVoltage(0);
                encoder.setPosition(0);
                isUp = true;
            })
        );
    }
    public Command smartClimberCommand() {
        return isUp == true ? climberDownCommand() : climberUpCommand();
    }
}
