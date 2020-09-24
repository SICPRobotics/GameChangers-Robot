package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.SubsystemContainer;

public abstract class AdvancedCommand<TOptions> extends CommandBase {
    protected final SubsystemContainer subs;
    protected final TOptions options;
    public AdvancedCommand(SubsystemContainer subs, TOptions options) {
        super();
        this.subs = subs;
        this.options = options;
    }
}