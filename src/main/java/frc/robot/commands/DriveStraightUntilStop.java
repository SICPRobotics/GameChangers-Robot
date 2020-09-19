package frc.robot.commands;

import com.fasterxml.jackson.databind.JsonNode;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public final class DriveStraightUntilStop extends CommandBase {
    private final Timer timer;
    private final PIDController controller;
    private final DriveTrain driveTrain;
    private final double targetRotation;
    private final double secondsFast;
    private final double slowSpeed;
    private final double fastSpeed;
    private int consecutiveStops;
    public DriveStraightUntilStop(final DriveTrain driveTrain, final double heading, final double secondsFast, final double slowSpeed, final double fastSpeed) {
        timer = new Timer();
        controller = new PIDController(0.0,0.0,0.0);

        this.driveTrain = driveTrain;
        this.targetRotation = heading;
        this.secondsFast = secondsFast;
        this.slowSpeed = slowSpeed;
        this.fastSpeed = fastSpeed;
    }

    public static DriveStraightUntilStop fromJson(DriveTrain driveTrain, JsonNode json) {
        return new DriveStraightUntilStop(
            driveTrain,
            json.get("heading").asDouble(),
            json.get("secondsFast").asDouble(),
            json.get("slowSpeed").asDouble(),
            json.get("fastSpeed").asDouble()
        );
    }

    public DriveStraightUntilStop(final DriveTrain driveTrain, final double heading, final double secondsFast) {
        this(driveTrain, heading, secondsFast, 0.4, 0.6);
    }

    public DriveStraightUntilStop(final DriveTrain driveTrain, final double heading) {
        this(driveTrain, heading, 0);
    }

    @Override
    public void initialize() {
        super.initialize();

        timer.reset();
        timer.start();

        controller.reset();

        consecutiveStops = 0;
    }

    @Override
    public void execute() {
        double output = controller.calculate(driveTrain.getRotation(), targetRotation);

        driveTrain.cheesyDrive((timer.get() > secondsFast ? slowSpeed : fastSpeed) * -1, output, 1);
    }

    @Override
    public boolean isFinished() {
        if (driveTrain.getLeftVelocity() < 30 && driveTrain.getRightVelocity() < 30) {
            consecutiveStops++;
        } else {
            consecutiveStops = 0;
        }
        return consecutiveStops > 8;
    }
}