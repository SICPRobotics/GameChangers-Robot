package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Turret;

public class IndexerCommand extends CommandBase{
    private final Indexer indexer;

    public IndexerCommand(Indexer indexer) {
        this.indexer = indexer;
    }

    @Override
    public void initialize() {
        this.indexer.turnOn(0.2);
    }

    @Override
    public void end(boolean interrupted) {
        this.indexer.turnOff();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
