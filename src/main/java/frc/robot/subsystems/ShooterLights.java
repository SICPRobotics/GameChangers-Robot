package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import frc.robot.SubsystemBaseWrapper;

public class ShooterLights extends SubsystemBaseWrapper{
    private final DigitalOutput dio;
    public ShooterLights(){
        dio = new DigitalOutput(0);
    }

    public void setHigh(){
        dio.set(true);
    }
    public void setLow(){
        dio.set(false);
    }
    
}
