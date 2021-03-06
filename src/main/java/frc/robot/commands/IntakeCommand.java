package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {

    private final Intake intake;

    public IntakeCommand(Intake intake) {
        this.intake = intake;
    }

    @Override
    public void initialize() {
        this.intake.turnOn(1);
        System.out.println("STARTINTAKE");
    }

    @Override
    public void end(boolean interrupted) {
        this.intake.turnOff();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}