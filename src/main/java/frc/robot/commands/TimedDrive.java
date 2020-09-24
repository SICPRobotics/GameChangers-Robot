package frc.robot.commands;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.SubsystemContainer;
import frc.robot.commands.autonomous.AdvancedAutonomous;

public class TimedDrive extends AdvancedCommand<TimedDrive.Options> {
    public static class Options {
        /**
         * Time to drive in seconds.
         */
        double time;

        /**
         * Speed
         */
        double speed;

        /**
         * Rotation
         */
        double rotate;
    }

    final Timer timer;

    public TimedDrive(SubsystemContainer subs, Options options) {
        super(subs, options);

        timer = new Timer();
        addRequirements(subs.driveTrain);
    }

    public static TimedDrive fromJson(SubsystemContainer subs, JsonNode json) throws JsonProcessingException {
        return new TimedDrive(subs, AdvancedAutonomous.mapper.treeToValue(json, TimedDrive.Options.class));
    }

    @Override
    public void initialize() {
        super.initialize();

        timer.reset();
        timer.start();
    }

    @Override
    public void execute() {
        super.execute();

        subs.driveTrain.cheesyDrive(options.speed, options.rotate, -1);
    }

    @Override
    public boolean isFinished() {
        return timer.get() > options.time;
    }
}