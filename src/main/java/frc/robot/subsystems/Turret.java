package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class Turret extends SubsystemBaseWrapper implements MotorSubsystem {
    private final VictorSP motor;

    public Turret() {
        super();
        
        motor = new VictorSP(8);
    }
    
    public void turn(double direction) {
        setMotor(0.2 *direction);
    }
    public void turnOff(){
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(value);
    }
}
