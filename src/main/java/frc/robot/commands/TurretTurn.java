package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Turret;

public class TurretTurn extends CommandBase{
    private final Turret turret;
    private final double direction;

    public TurretTurn(Turret turret, double direction) {
        this.turret = turret;
        this.direction = direction;
        addRequirements(turret);
    }

    @Override
    public void initialize() {
        this.turret.turnOn(direction);
    }

    @Override
    public void end(boolean interrupted) {
        this.turret.turnOff();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
