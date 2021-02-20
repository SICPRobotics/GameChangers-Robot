package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterLights;

public class ShooterLightsCommand extends CommandBase{
    private final ShooterLights shooterLights;

    public ShooterLightsCommand(ShooterLights shooterLights){
        this.shooterLights = shooterLights;
    }

    @Override
    public void initialize() {
        this.shooterLights.setHigh();
    }

    @Override
    public void end(boolean interrupted) {
        this.shooterLights.setLow();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
