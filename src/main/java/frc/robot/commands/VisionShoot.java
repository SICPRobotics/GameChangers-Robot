package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpiutil.math.MathUtil;
import frc.robot.pi_client.PiClient;
import frc.robot.subsystems.Feeder;
import frc.robot.subsystems.FlyWheel;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.ShooterLights;
import frc.robot.subsystems.Turret;

public class VisionShoot extends CommandBase {
    final Turret turret;
    final FlyWheel flyWheel;
    final Indexer indexer;
    final Feeder feeder;
    final Hood hood;
    final ShooterLights lights;

    final PiClient pi;
    final PIDController xPid = new PIDController(0.001, 0.01, 0.02);
    boolean shooting = false;
    public VisionShoot(final Turret turret, final FlyWheel flyWheel, final Indexer indexer, final Feeder feeder, final Hood hood, final ShooterLights lights, final PiClient pi) {
        this.turret = turret;
        this.flyWheel = flyWheel;
        this.indexer = indexer;
        this.feeder = feeder;
        this.hood = hood;
        this.lights = lights;

        this.pi = pi;

        System.out.println("VisionShoot");

        addRequirements(turret, flyWheel, lights);
    }

    @Override
    public void initialize() {
        flyWheel.setMotor(1);
        lights.set(true);
        shooting = false;
    }

    @Override
    public void execute() {
        if (!shooting) {
            var ready = true;
            final var val = xPid.calculate(pi.getVisionStatus().target.x, 177);
            System.out.println(val);
            if (Math.abs(pi.getVisionStatus().target.x - 177) > 20) {
                turret.setMotor(MathUtil.clamp(val, -0.25, 0.25));
                ready = false;
            }

            if (Math.abs(getTargetHoodPosition() - hood.getPosition()) < 10) {
                hood.setPosition(getTargetHoodPosition());
                ready = false;
            }

            if (ready) {
                shooting = true;
                turret.setMotor(0);
                hood.setMotor(0);
                indexer.setMotor(0.2);
                feeder.setMotor(0.5);
            }
        }
    }

    public int getTargetHoodPosition() {
        return 0;
    }

    @Override
    public void end(boolean val) {
        flyWheel.setMotor(0);
        turret.setMotor(0);
        feeder.setMotor(0);
        lights.set(false);
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
