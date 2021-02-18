package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public final class ResetPoistion extends CommandBase {
    private final DriveTrain driveTrain;

    public ResetPoistion(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
    }

    @Override
    public void execute() {
        driveTrain.reset();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}