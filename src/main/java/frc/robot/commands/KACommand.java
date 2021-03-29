package frc.robot.commands;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.WillowMath;
import frc.robot.subsystems.DriveTrain;

public class KACommand extends CommandBase{
    private final DriveTrain driveTrain;
    private ArrayList<Double> velocity, volts, time;
    //private final double RAMP_UP_RATE = 0.05;
    //private Double volt = 0.25;
    private double target = 8;
    private Timer timer;

    public KACommand(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        velocity = new ArrayList<Double>();
        volts = new ArrayList<Double>();
        time = new ArrayList<Double>();
        timer = new Timer();
    }
    @Override
    public void initialize() {
        timer.start();
    }
    @Override
    public void execute() {
        driveTrain.voltDrive(target, target);
        velocity.add(driveTrain.getLeftVelocityMeters());
        volts.add(driveTrain.getLeftVolts());
        time.add(timer.get());
    }

    @Override
    public void end(boolean interrupted) {
        //System.out.println("KA = " + WillowMath.derivative(new double[] {WillowMath.aOfBestFit(list, list2)})[0]);
    }
    @Override
    public boolean isFinished() {
        return driveTrain.getLeftDistanceMeters() > 10 || timer.get() > 10;
    }
}
