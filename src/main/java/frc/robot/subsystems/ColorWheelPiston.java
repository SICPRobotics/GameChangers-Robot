package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class ColorWheelPiston extends SubsystemBaseWrapper implements PistonSubsystem {
    private final DoubleSolenoid solenoid = 
            new DoubleSolenoid(Constants.ColorWheel.DOUBLE_SOLENOID_FORWARD_ID, Constants.ColorWheel.DOUBLE_SOLENOID_REVERSE_ID);
    
    public ColorWheelPiston() {
        super();
    }
    
    public void pistonForward() {
        solenoid.set(Value.kForward);
    }

    public void pistonReverse() {
        solenoid.set(Value.kReverse);
    }
}