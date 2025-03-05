package frc.robot.subsystems;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkLimitSwitch;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Intake extends SubsystemBase {
    private final SparkMax coralMotor;
    private final SparkLimitSwitch coralSensor;
    private final SparkMax algaeMotor;
    private final SparkLimitSwitch algaeSensor;

    public Intake() {
        coralMotor = new SparkMax(Constants.Intake.coralMotorCANId, SparkLowLevel.MotorType.kBrushless);
        coralSensor = coralMotor.getForwardLimitSwitch();
        algaeMotor = new SparkMax(Constants.Intake.algaeMotorCANId, SparkLowLevel.MotorType.kBrushless);
        algaeSensor = algaeMotor.getForwardLimitSwitch();
        this.setDefaultCommand(this.stopCoralCommand());
        setupDashboard();
    }

    private void setupDashboard(){
        SmartDashboard.putData("intake/getCoral", this.intakeCoralCommand());
        SmartDashboard.putData("intake/deliverCoral", this.deliverCoralCommand());
    }

    public Command stopCoralCommand(){
        return run(()->coralMotor.set(0));
    }

    public Command intakeCoralCommand(){
        return run(()->coralMotor.set(0.2)).until(coralSensor::isPressed);
    }

    public Command deliverCoralCommand(){
        return run(()->coralMotor.set(1)).until(()->!coralSensor.isPressed());
    }

    public Command intakeAlgaeCommand(){
        // TODO
        return run(()->{});
    }

    public Command deliverAlgaeCommand(){
        // TODO
        return run(()->{});
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("IntakeSubsystem");
        builder.addBooleanProperty("coralLoaded", coralSensor::isPressed, null);
        builder.addBooleanProperty("algaeLoaded", algaeSensor::isPressed, null);

    }
}
