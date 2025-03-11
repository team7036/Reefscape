package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Elevator extends SubsystemBase {

    private final SparkMax motor;
    private SparkMaxConfig config = new SparkMaxConfig();
    private final DigitalInput lowerLimitInput;
    private final RelativeEncoder encoder;
    private final ElevatorFeedforward feedforward;
    public final ProfiledPIDController pid;
    private boolean kZeroed = false;

    public Elevator() {
        motor = new SparkMax(
            Constants.Elevator.kMotorCANId, 
            SparkLowLevel.MotorType.kBrushless
        );
        configureMotor();
        encoder = motor.getEncoder();
        pid = new ProfiledPIDController(
            Constants.Elevator.kP, 
            Constants.Elevator.kI, 
            Constants.Elevator.kD, 
            new TrapezoidProfile.Constraints(
                Constants.Elevator.kMaxVelocity, 
                Constants.Elevator.kMaxAcceleration
            )
        );
        feedforward = new ElevatorFeedforward(
            Constants.Elevator.kS,
            Constants.Elevator.kG,
            Constants.Elevator.kV,
            Constants.Elevator.kA
        );
        lowerLimitInput = new DigitalInput(0);
        setupDashboard();
    }

    private void configureMotor(){
        config.encoder.positionConversionFactor(Constants.Elevator.conversion);
        config.softLimit.forwardSoftLimit(1.55);
        config.limitSwitch.forwardLimitSwitchEnabled(true);
        config.limitSwitch.forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen);
        config.limitSwitch.reverseLimitSwitchEnabled(false);
        config.smartCurrentLimit(40);
        config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        motor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    private void setupDashboard(){
        SmartDashboard.putData("elevator/raise", run(()->motor.set(0.5)));
        SmartDashboard.putData("elevator/stop", this.stopCommand());
        SmartDashboard.putData("elevator/reset", this.resetZeroHeightCommand());
        SmartDashboard.putData("elevator/lower", run(()->motor.set(-0.5)));

        SmartDashboard.putData("elevator/L1", this.setHeightCommand(Constants.Elevator.Heights.kL1));
        SmartDashboard.putData("elevator/L2", this.setHeightCommand(Constants.Elevator.Heights.kL2));
        SmartDashboard.putData("elevator/L3", this.setHeightCommand(Constants.Elevator.Heights.kL3));
        SmartDashboard.putData("elevator/L4", this.setHeightCommand(Constants.Elevator.Heights.kL4));
    }

    public Command setHeightCommand( double height ){
        return run(()->{
            double pidInput = pid.calculate(encoder.getPosition(), height);
            double ffInput = feedforward.calculate( pid.getSetpoint().velocity );
            motor.setVoltage(pidInput + ffInput);
        }).beforeStarting(resetZeroHeightCommand().onlyIf(()->!kZeroed));
    }

    public Command stopCommand(){
        return run(()->motor.set(0));
    }

    public Command resetZeroHeightCommand(){
        return Commands.parallel(
            runOnce(()->motor.setVoltage(-8)),
            Commands.waitUntil(()->!lowerLimitInput.get()).andThen(()->{
                motor.setVoltage(0);
                encoder.setPosition(0);
                kZeroed = true;
            })
        );
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("ElevatorSubsystem");
        builder.addDoubleProperty("height", this.encoder::getPosition, null);
        builder.addDoubleProperty("power", this.motor::getAppliedOutput, null);
        builder.addBooleanProperty("lowerlimit", ()->!lowerLimitInput.get(), null);
        builder.addBooleanProperty("upperlimit", motor.getForwardLimitSwitch()::isPressed, null);

    }


}
