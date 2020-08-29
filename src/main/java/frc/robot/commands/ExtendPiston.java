package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.PistonSubsystem;

public final class ExtendPiston extends CommandBase {
    private final PistonSubsystem piston;
    public ExtendPiston(final PistonSubsystem pistonSubsystem) {
        this.piston = pistonSubsystem;
        addRequirements((Subsystem) pistonSubsystem);
    }

    @Override
    public void initialize() {
        piston.pistonForward();
    }

    @Override
    public void end(final boolean interrupted) {
        piston.pistonReverse();
    }

}