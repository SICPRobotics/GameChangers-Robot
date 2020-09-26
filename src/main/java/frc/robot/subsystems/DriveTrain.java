package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Constants.Wheel;
import frc.robot.SubsystemBaseWrapper;
import frc.robot.truth.Minitrue;

/**
 * the DriveTrain, aka the thing that moves the robot
 */
public final class DriveTrain extends SubsystemBaseWrapper {
    private final DifferentialDrive robotDrive;
    
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.DriveTrain.FRONT_RIGHT_MOTOR_ID);
    private final WPI_TalonSRX rearRight = new WPI_TalonSRX(Constants.DriveTrain.REAR_RIGHT_MOTOR_ID);
    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.DriveTrain.FRONT_LEFT_MOTOR_ID);
    private final WPI_TalonSRX rearLeft = new WPI_TalonSRX(Constants.DriveTrain.REAR_LEFT_MOTOR_ID);

    private static Gyro gyro;
    private final DoubleSupplier getLeftPosition;
    private final DoubleSupplier getLeftVelocity;
    private final DoubleSupplier getRightPosition;
    private final DoubleSupplier getRightVelocity;

    public DriveTrain(Minitrue minitrue) {
        super(minitrue);
        // Motors
        frontRight.configFactoryDefault();
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        frontRight.setSelectedSensorPosition(0);

        rearRight.configFactoryDefault();
        SpeedControllerGroup right = new SpeedControllerGroup(frontRight, rearRight);
        getRightPosition = frontRight::getSelectedSensorPosition;
        getRightVelocity = frontRight::getSelectedSensorVelocity;

        frontLeft.configFactoryDefault();
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        frontLeft.setSelectedSensorPosition(0);

        rearLeft.configFactoryDefault();
        SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);
        getLeftPosition = frontLeft::getSelectedSensorPosition;
        getLeftVelocity = frontLeft::getSelectedSensorVelocity;
        
        this.robotDrive = new DifferentialDrive(left, right);
        // Gyro
        gyro = new ADXRS450_Gyro(Port.kOnboardCS0);
        gyro.calibrate();
        gyro.reset();
        gyro.getAngle();

        // Reset the encoders if the position is about to be reset
        minitrue.beforePoseSet(truth -> resetDriveEncoders());
    }

    // Mostly taken from last year's robot
    /**
     * The method to drive the robot.
     * 
     * @param moveValue   the amount that the robot should move (Y axis, Joystick
     *                    rawAxis 1)
     * @param rotateValue the amount that the robot should rotate (X axis, Joystick
     *                    rawAxis 0)
     * @param scaleValue  the amount that everything should be scaled by (usually
     *                    given by the little flap thing on the bottom of the
     *                    joystick, Joystick rawAxis 3)
     */
    public void cheesyDrive(final double moveValue, final double rotateValue, final double scaleValue) {
        //System.out.println("Attempted rotate " + rotateValue);
        final double actualAdjustValue = ((-scaleValue + 1) / 2);
        final double movevalue = Math.abs(moveValue) < Constants.CheesyDrive.Y_AXIS_DEADZONE_RANGE
                ? 0
                : moveValue * actualAdjustValue;

        // Deadzone on x axis only if y value is small
        final double turnvalue = Math.abs(rotateValue) < Constants.CheesyDrive.X_AXIS_DEADZONE_RANGE
                && Math.abs(moveValue) < Constants.CheesyDrive.X_AXIS_DEADZONE_Y_MIN ? 0
                        : rotateValue * actualAdjustValue;

        this.robotDrive.arcadeDrive(movevalue, turnvalue, true);
        // this.robotDrive.tankDrive((moveValue + rotateValue) * adjustValue, (moveValue
        // - rotateValue) * adjustValue);
    }

    public void periodic() {
        Rotation2d gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());
        minitrue.updatePose(gyroAngle, this.getLeftPositionMeters(), this.getRightPositionMeters());

        SmartDashboard.putNumber("TalonSRX 0 (front right) Temperature", frontRight.getTemperature());
        SmartDashboard.putNumber("TalonSRX 1 (rear right) Temperature", rearRight.getTemperature());
        SmartDashboard.putNumber("TalonSRX 2 (rear left) Temperature", rearLeft.getTemperature());
        SmartDashboard.putNumber("TalonSRX 3 (front left) Temperature", frontLeft.getTemperature());
        SmartDashboard.putNumber("Front Right Motor Position", frontRight.getSelectedSensorPosition());
        SmartDashboard.putNumber("Front Left Motor Position", frontLeft.getSelectedSensorPosition());
        SmartDashboard.putNumber("Front Right Motor Velocity", frontRight.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Front Left Motor Velocity", frontLeft.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Gyro Value", gyro.getAngle());
        
    }

    public double getRotation() {
        return gyro.getAngle();
    }
    public void calibrateGyro() {
        gyro.calibrate();
    }
    /*public void calibrateDriveEncoders(){
        rightEncoder.setDistancePerPulse(0.1524/360); // for 6 in circumfrance wheels Change to match correct wheel size, also this will move to constants after I am done 
        rightEncoder.setMinRate(10);
        rightEncoder.setMaxPeriod(0.1);
        leftEncoder.setDistancePerPulse(0.1524/360); 
        leftEncoder.setMinRate(10);
        leftEncoder.setMaxPeriod(0.1);
        resetDriveEncoders();
    }*/

    public void resetDriveEncoders(){
        frontRight.setSelectedSensorPosition(0);
        frontLeft.setSelectedSensorPosition(0);
    }

    public double getLeftPosition() {
        return getLeftPosition.getAsDouble();
    }

    public double getLeftPositionMeters() {
        return (getLeftPosition() / 4096) * Wheel.circumference;
    }

    public double getRightPosition() {
        return getRightPosition.getAsDouble();
    }

    public double getRightPositionMeters() {
        return (getRightPosition() / 4096) * Wheel.circumference;
    }

    public double getLeftVelocity() {
        return getLeftVelocity.getAsDouble();
    }

    public double getRightVelocity() {
        return getRightVelocity.getAsDouble();
    }
}
