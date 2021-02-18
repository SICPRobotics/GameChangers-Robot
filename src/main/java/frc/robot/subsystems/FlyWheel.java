package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class FlyWheel extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonFX motor;

    public FlyWheel() {
        super();
        
        motor = new TalonFX(10);
    }
    
    public void startFly() {
        setMotor(1);
        System.out.println("SetFly");
    }

    public void stopFly() {
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}
