package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.truth.Minitrue;

public class SubsystemBaseWrapper extends SubsystemBase {
    protected final Minitrue minitrue;
    
    public SubsystemBaseWrapper(Minitrue minitrue) {
        this.minitrue = minitrue;
        SmartDashboard.putData(this);
    }
}