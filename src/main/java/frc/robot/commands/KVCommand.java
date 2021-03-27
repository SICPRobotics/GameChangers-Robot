package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.WillowMath;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

public class KVCommand extends CommandBase {

    private final DriveTrain driveTrain;
    private ArrayList<Double> velocity, volts;
    private final double RAMP_UP_RATE = 0.05;
    private Double volt = 0.25;

    public KVCommand(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        velocity = new ArrayList<Double>();
        volts = new ArrayList<Double>();
    }

    @Override
    public void initialize() {

    }
    @Override
    public void execute() {
        driveTrain.voltDrive(volt, volt);
        velocity.add(driveTrain.getLeftVelocityMeters());
        volts.add(driveTrain.getLeftVolts());
        System.out.println(volt);
        volt = volt + RAMP_UP_RATE;

    }

    @Override
    public void end(boolean interrupted) {
        System.out.println("kV = " + WillowMath.slopeOfBestFit(velocity, volts));
        System.out.println("KS = " + WillowMath.interceptOfBestFit(velocity, volts, WillowMath.slopeOfBestFit(velocity, volts)));
        volt = (double)0;
    }
    @Override
    public boolean isFinished() {
        return volt > 9;
    }
}