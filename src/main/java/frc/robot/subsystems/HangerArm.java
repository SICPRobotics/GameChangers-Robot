package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SubsystemBaseWrapper;

public final class HangerArm extends SubsystemBaseWrapper implements MotorSubsystem {
    private final TalonSRX motor = new TalonSRX(5);
    public HangerArm() {
        super();
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        motor.setSelectedSensorPosition(0);
    }

    public void setMotor(double value) {
        if (value > 0 && motor.getSelectedSensorPosition() > 22000.0) {
            return;
        }
      
        motor.set(ControlMode.PercentOutput, value);
    }
    public void slowDrive(){
        motor.set(ControlMode.PercentOutput, 0.1);
    }
    public void stop(){
        motor.set(ControlMode.PercentOutput, 0);
    }
    public int getEncoderVale(){
        return motor.getSelectedSensorPosition();
    }
    public void setEncoderValue(int value){
        motor.setSelectedSensorPosition(value);
    }

    public void periodic() {
        if (motor.getSelectedSensorPosition() > 22000.0) {
            setMotor(0);
        }
        SmartDashboard.putNumber("Hanger arm", motor.getSelectedSensorPosition());
    }
}