package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

public final class GroundIntake extends SubsystemBaseWrapper implements MotorSubsystem, ToggleSubsystem {

    private VictorSPX intakeMotor;
    public GroundIntake() {
        super();
        intakeMotor = new VictorSPX(Constants.GroundIntake.MOTOR_ID);
    }

    public void start() {
        setMotor(1);
        System.out.println("Ground intake subsystem start!");
    }

    public void stop() {
        setMotor(0);
        System.out.println("Ground intake subsystem end!");
    }

    public void setMotor(final double value) {
        intakeMotor.set(ControlMode.PercentOutput, value * Constants.GroundIntake.SPEED);
        
    }
  
}