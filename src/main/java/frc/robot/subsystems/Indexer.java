package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.VictorSP;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class Indexer extends SubsystemBaseWrapper implements MotorSubsystem{
    private final TalonSRX motor;

    public Indexer() {
        super();
        motor = new TalonSRX(Constants.MotorSubsystems.INDEXER_ID);
    }
    
    public void turnOn(double velocity) {
        setMotor(0.2);
    }
    public void turnOff(){
        setMotor(0);
    }

    public void setMotor(final double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}
