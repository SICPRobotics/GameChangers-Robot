package frc.robot.subsystems;

/**
 * This is to be implemented by subsystems that essentially wrap around a motor.
 */
public interface MotorSubsystem {
    public void setMotor(double value);
    public void turnOn(double velocity);
    public void turnOff();
}