package frc.robot.subsystems;
import java.util.List;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
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

    private final List<Double> coralReefHeights = List.of(
        Constants.Elevator.Heights.kL1,
        Constants.Elevator.Heights.kL2,
        Constants.Elevator.Heights.kL3,
        Constants.Elevator.Heights.kL4
    );
    private final SparkMax motor;
    private final SparkMaxConfig config;
    private final DigitalInput lowerLimitInput;
    private final RelativeEncoder encoder;
    private final ElevatorFeedforward feedforward;
    private final ProfiledPIDController pid;

    private boolean kZeroed = false;

    public Elevator() {
        motor = new SparkMax(
            Constants.Elevator.kMotorCANId, 
            SparkLowLevel.MotorType.kBrushless
        );
        config = new SparkMaxConfig();
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
        lowerLimitInput = new DigitalInput(Constants.Elevator.kLowerLimitInput);
        this.setDefaultCommand(this.setHeightCommand(Constants.Elevator.Heights.kCoralIntake));
        setupDashboard();
    }

    public boolean aimingAtTrough(){
        return pid.atSetpoint() && ( pid.getSetpoint().position == Constants.Elevator.Heights.kL1 );
    }

    private void configureMotor(){
        config.encoder.positionConversionFactor(Constants.Elevator.conversion);
        config.softLimit.forwardSoftLimit(1.55);
        config.limitSwitch.forwardLimitSwitchEnabled(true);
        config.limitSwitch.forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyClosed);
        config.limitSwitch.reverseLimitSwitchEnabled(false);
        config.smartCurrentLimit(40);
        config.idleMode(SparkBaseConfig.IdleMode.kBrake);
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

    private void setHeight( double height ){
        double pidInput = pid.calculate(encoder.getPosition(), height);
        double ffInput = feedforward.calculate( pid.getSetpoint().velocity );
        motor.setVoltage(pidInput + ffInput);
    }

    public Command setHeightCommand( double height ){
        return run(()-> setHeight(height))
            .until(pid::atGoal)
            .beforeStarting(resetZeroHeightCommand().onlyIf(()->!kZeroed));
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

    public boolean coralReefReady(){
        return pid.atSetpoint() && coralReefHeights.contains(pid.getSetpoint().position);
    }

    public boolean coralIntakeReady(){
        return pid.atSetpoint() && (pid.getSetpoint().position == Constants.Elevator.Heights.kCoralIntake);
    }

    @Override
    public void initSendable(SendableBuilder builder) {
        builder.setSmartDashboardType("ElevatorSubsystem");
        builder.addBooleanProperty("coral_reef_ready", this::coralReefReady, null);
        builder.addBooleanProperty("coral_intake_ready", this::coralIntakeReady, null);
        builder.addDoubleProperty("height", this.encoder::getPosition, null);
        builder.addDoubleProperty("targetheight", ()->this.pid.getGoal().position, null);
        builder.addDoubleProperty("power", this.motor::getAppliedOutput, null);
        builder.addBooleanProperty("lowerlimit", ()->!lowerLimitInput.get(), null);
        builder.addBooleanProperty("upperlimit", motor.getForwardLimitSwitch()::isPressed, null);
    }


}
