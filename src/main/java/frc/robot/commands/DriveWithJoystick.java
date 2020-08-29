package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public final class DriveWithJoystick extends CommandBase {
    private final DriveTrain driveTrain;
    private final DoubleSupplier moveValueGetter;
    private final DoubleSupplier rotateValueGetter;
    private final DoubleSupplier adjustValueGetter;
    private final boolean isInverted;

    public DriveWithJoystick(final DriveTrain driveTrain, final DoubleSupplier moveValueGetter, final DoubleSupplier rotateValueGetter, final DoubleSupplier adjustValueGetter) {
        this(driveTrain, moveValueGetter, rotateValueGetter, adjustValueGetter, false);    
    }

    public DriveWithJoystick(final DriveTrain driveTrain, final DoubleSupplier moveValueGetter, final DoubleSupplier rotateValueGetter, final DoubleSupplier adjustValueGetter, final boolean isInverted) {
        this.driveTrain = driveTrain;
        this.moveValueGetter = moveValueGetter;
        this.rotateValueGetter = rotateValueGetter;
        this.adjustValueGetter = adjustValueGetter;
        this.isInverted = isInverted;
        addRequirements(driveTrain);
    }

    @Override
    public void execute() {
        this.driveTrain.cheesyDrive(-this.moveValueGetter.getAsDouble() * (isInverted ? -1 : 1), this.rotateValueGetter.getAsDouble(), this.adjustValueGetter.getAsDouble());
    }

}