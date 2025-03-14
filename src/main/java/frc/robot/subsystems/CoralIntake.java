package frc.robot.subsystems;

import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class CoralIntake extends SubsystemBase {
    private final SparkMax coralMotor;
    private final SparkLimitSwitch coralSensor;

    //private final SparkLimitSwitch algaeSensor;

    public CoralIntake() {
        coralMotor = new SparkMax(Constants.Intake.kCoralIntakeMotorCANID, SparkLowLevel.MotorType.kBrushless);
        coralSensor = coralMotor.getForwardLimitSwitch();

        // algaeSensor = algaeMotor.getForwardLimitSwitch();
        this.setDefaultCommand(this.stopCoralCommand());
        setupDashboard();
    }

    private void setupDashboard() {
        SmartDashboard.putData("intake/getCoral", this.intakeCoralCommand());
        SmartDashboard.putData("intake/deliverCoral", this.deliverCoralCommand(false));
    }

    public boolean hasCoral(){
        return coralSensor.isPressed();
    }

    public Command stopCoralCommand() {
        return run(() -> coralMotor.set(0));
    }

    public Command deliverCoralCommand(boolean trough){
        return run(()->coralMotor.set( trough ? Constants.Intake.kTroughDeliverySpeed : Constants.Intake.kReefDeliverySpeed));
    }

    public Command intakeCoralCommand() {
        return run(() -> coralMotor.set(Constants.Intake.kCoralIntakeSpeed)).until(coralSensor::isPressed);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("IntakeSubsystem");
        builder.addBooleanProperty("hasCoral", this::hasCoral, null);

    }
}
