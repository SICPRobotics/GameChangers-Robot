package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.SubsystemBaseWrapper;

public class ShooterLights extends SubsystemBaseWrapper{
    private final DigitalOutput dio;
    public ShooterLights(){
        dio = new DigitalOutput(0);
    }

    public void set(final boolean bool){
        dio.set(bool);
    }
    
    public boolean get() {
        return dio.get();
    }

    public void toggle() {
        set(!get());
    }
}
