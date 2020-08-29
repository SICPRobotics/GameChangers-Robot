package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.PastaPuller;

public class PullPasta extends CommandBase {

    private final PastaPuller pastaPuller;

    public PullPasta(PastaPuller pastaPuller) {
        this.pastaPuller = pastaPuller;
    }

    @Override
    public void initialize() {
        this.pastaPuller.startPulling();
    }

    @Override
    public void end(boolean interrupted) {
        this.pastaPuller.stopPulling();
    }
}