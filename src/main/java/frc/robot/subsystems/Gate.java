package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class Gate extends SubsystemBaseWrapper implements PistonSubsystem {
    private DoubleSolenoid solenoid;
    public Gate() {
        super();
        solenoid = new DoubleSolenoid(Constants.Gate.FORWARD_SOLENOID_ID, Constants.Gate.REVERSE_SOLENOID_ID);
        SmartDashboard.putBoolean("GATE UP?", true);
    }

    public void pistonForward() {
        solenoid.set(Value.kForward);
        SmartDashboard.putBoolean("GATE UP?", false);
    }

    public void pistonReverse() {
        solenoid.set(Value.kReverse);
        SmartDashboard.putBoolean("GATE UP?", true);
    }

    public boolean isUp() {
        return solenoid.get() == Value.kReverse;
    }
}
