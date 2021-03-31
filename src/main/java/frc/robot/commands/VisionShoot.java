package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.pi_client.PiClient;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.FlyWheel;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Turret;

public class VisionShoot extends CommandBase {
    final Turret turret;
    final FlyWheel flyWheel;
    final Indexer indexer;
    final Feeder feeder;

    final PiClient pi;
    final PIDController pid = new PIDController(0.001, 0.01, 0.02);
    boolean shooting = false;
    public VisionShoot(final Turret turret, final FlyWheel flyWheel, final Indexer indexer, final Feeder feeder, final PiClient pi) {
        this.turret = turret;
        this.flyWheel = flyWheel;
        this.indexer = indexer;
        this.feeder = feeder;

        this.pi = pi;
        addRequirements(turret, flyWheel);
    }

    @Override
    public void initialize() {
        flyWheel.setMotor(-1);
        shooting = false;
    }

    @Override
    public void execute() {
        if (!shooting) {
            final var val = pid.calculate(pi.getVisionStatus().target.x, 177);
            System.out.println(val);
            if (Math.abs(pi.getVisionStatus().target.x - 177) < 5) {
                shooting = true;
                turret.setMotor(0);
                indexer.setMotor(0.2);
                feeder.setMotor(0.5);
            } else {
                turret.setMotor(MathUtil.clamp(val, -0.25, 0.25));
            }
        }
    }

    @Override
    public void end(boolean val) {
        flyWheel.setMotor(0);
        turret.setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
