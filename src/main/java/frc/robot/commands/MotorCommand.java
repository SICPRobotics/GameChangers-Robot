package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.MotorSubsystem;

public class MotorCommand extends CommandBase{
    private final MotorSubsystem motorSubsystem;
    private double velocity;
    public MotorCommand(MotorSubsystem motorSubsystem, double velocity){
        this.motorSubsystem = motorSubsystem;
        this.velocity = velocity;
    }
    
    @Override
    public void initialize() {
        this.motorSubsystem.turnOn(velocity);
    }

    @Override
    public void end(boolean interrupted) {
        this.motorSubsystem.turnOff();
    }
    @Override
    public boolean isFinished() {
        return false;
    }
}
