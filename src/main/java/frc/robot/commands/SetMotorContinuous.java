package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.MotorSubsystem;

public final class SetMotorContinuous extends CommandBase {
    private final MotorSubsystem subsystem;
    private final DoubleSupplier valueGetter;
    public SetMotorContinuous(final MotorSubsystem subsystem, final DoubleSupplier value) {
        this.subsystem = subsystem;
        this.valueGetter = value;
        addRequirements((SubsystemBase) this.subsystem);
    }

    @Override
    public void execute() {
        this.subsystem.setMotor(valueGetter.getAsDouble());
    }
}