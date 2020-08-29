package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.Lights;

public final class SetLightsToColor extends InstantCommand {
    public SetLightsToColor(final Lights lights, final Lights.LightsColor color) {
        super(() -> lights.setColor(color), lights);
    }

    @Override
    public boolean runsWhenDisabled() {
        return true;
    }
}