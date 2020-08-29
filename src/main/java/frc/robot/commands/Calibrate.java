package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.HangerArm;
import frc.robot.subsystems.PistonSubsystem;

public final class Calibrate extends CommandBase {
    private final HangerArm hanger;
    private Timer timer;
    private int firstReading;
    private int nextSec;
    private int newReading;
    public Calibrate(final HangerArm hanger) {
        this.hanger = hanger;
        System.out.println("Calibrate");
        addRequirements(hanger);
    }

    @Override
    public void initialize() {
      timer = new Timer();
      timer.start();
      firstReading = hanger.getEncoderVale();
      hanger.slowDrive();
      nextSec = 1;
      System.out.println("Calibration init");
    }

    @Override
    public void execute() {
    }
  
    @Override
    public boolean isFinished() 
    {
      if (timer.get() > 10)
        return true;
      else if (timer.get() > nextSec)
      {
        newReading = hanger.getEncoderVale();
        if (newReading - firstReading < 2500)
          return true;
        firstReading = newReading;
        nextSec++;
      }
      return false;
    }
    @Override
    public void end(final boolean interrupted) {
        hanger.stop();
        hanger.setEncoderValue(0);
        System.out.println("Calibrate ended");
    }

}