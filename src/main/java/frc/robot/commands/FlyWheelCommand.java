package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.FlyWheel;

public class FlyWheelCommand extends CommandBase {

    private final FlyWheel flyWheel;

    public FlyWheelCommand(FlyWheel flyWheel) {
        this.flyWheel = flyWheel;
    }

    @Override
    public void initialize() {
        this.flyWheel.turnOn(-1);
        System.out.println("SPIN UP");
    }

    @Override
    public void end(boolean interrupted) {
        this.flyWheel.turnOff();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}