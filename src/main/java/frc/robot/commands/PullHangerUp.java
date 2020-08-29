package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hanger;

public final class PullHangerUp extends CommandBase {
    private final Hanger hanger;

    public PullHangerUp(final Hanger hanger) {
        this.hanger = hanger;
    }

    @Override
    public void initialize() {
        this.hanger.startPullingUp();
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(final boolean interrupted) {
        this.hanger.stopPullingUp();
    }
}