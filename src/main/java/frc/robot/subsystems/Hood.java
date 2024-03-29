package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public class Hood extends SubsystemBaseWrapper implements MotorSubsystem {

    TalonSRX motor = new TalonSRX(Constants.MotorSubsystems.HOOD_ID);
    DigitalInput limit = new DigitalInput(1);
    public Hood(){
        motor.configFactoryDefault();
        motor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
    }
    @Override
    public void periodic() {
        //SmartDashboard.putNumber("Hood Angle", this.getAngle());
        SmartDashboard.putNumber("Hood Position", getPosition());
        SmartDashboard.putBoolean("Hood Limit", this.limit.get());

        if (!limit.get() && motor.getMotorOutputPercent() > 0) {
            setMotor(0);
            
        }

        if (!limit.get()) {
            motor.setSelectedSensorPosition(0);
        }
    }
    @Override
    public void turnOn(double velocity) {
        setMotor(velocity * 0.2);
    }
    @Override
    public void turnOff() {
       setMotor(0);
    }
    public void calibrate(){
        /*if(!limit.get()){
            setMotor(0.16);
        }
        else{
            setMotor(0);
            motor.setSelectedSensorPosition(0);
        }*/
    }
    @Override
    public void setMotor(double value) {
        motor.set(ControlMode.PercentOutput, !limit.get() && value > 0 ? 0 : value);
    }
    public double getPosition(){
        return motor.getSelectedSensorPosition();
    }
    /*public void setAngle(double theta){
        setMotor(getAngle() != theta  ? getAngle() > theta ? 0.16 : -0.16: 0);
    }*/
    public void setPosition(int pos) {
        motor.set(ControlMode.Position, pos);
    }
    public double getAngle(){
        return (getPosition() * (1/4096) * (1/50) * 50) + 58.8; // 58.8 = theta at zero // need a constant for how much the encoder ticks increase degrease 50 is a very rouch estimate // 1 gear rotatoin moves to full exrtant, 
    }
}
