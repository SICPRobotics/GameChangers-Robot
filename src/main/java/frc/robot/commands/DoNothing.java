package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.MotorSubsystem;

public final class DoNothing extends CommandBase {
    private final MotorSubsystem subsystem;

    public DoNothing(final MotorSubsystem subsystem) {
        this.subsystem = subsystem;
        addRequirements((SubsystemBase) this.subsystem);
    }

    @Override
    public void execute() {
        this.subsystem.setMotor(0);
    }
}