package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class SubsystemBaseWrapper extends SubsystemBase {
    
    public SubsystemBaseWrapper() {
        SmartDashboard.putData(this);
    }
}