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
        
        motor = new VictorSP(Constants.MotorSubsystems.TURRET_ID);
    }
    
    public void turnOn(double velocity) {
        setMotor(0.2 *velocity);
    }
    public void turnOff(){
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(value);
    }
}
