package frc.robot.subsystems;

import java.util.Arrays;
import java.util.function.BiConsumer;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Units;
import frc.robot.Constants;
import frc.robot.SubsystemBaseWrapper;

/**
 * the DriveTrain, aka the thing that moves the robot
 */
public final class DriveTrain extends SubsystemBaseWrapper {
    private final DifferentialDriveOdometry odometry;
    public final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.VoltageConstants.TRACK_WIDTH);
    private final ChassisSpeeds chassisSpeeds;
    private final Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0); //SPI.Port.kMXP ?
    private final WPI_TalonSRX frontRight = new WPI_TalonSRX(Constants.DriveTrain.FRONT_RIGHT_MOTOR_ID);
    private final WPI_TalonSRX rearRight = new WPI_TalonSRX(Constants.DriveTrain.REAR_RIGHT_MOTOR_ID);
    private final WPI_TalonSRX frontLeft = new WPI_TalonSRX(Constants.DriveTrain.FRONT_LEFT_MOTOR_ID);
    private final WPI_TalonSRX rearLeft = new WPI_TalonSRX(Constants.DriveTrain.REAR_LEFT_MOTOR_ID);
    private final SpeedControllerGroup left = new SpeedControllerGroup(frontLeft, rearLeft);
    private final SpeedControllerGroup right = new SpeedControllerGroup(frontRight, rearRight);
    private final DifferentialDrive robotDrive = new DifferentialDrive(left, right);
    public DriveTrain() {
        super();
        // Motors
        gyro.calibrate();
        frontRight.configFactoryDefault();
        frontRight.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        frontRight.setSelectedSensorPosition(0);
        rearRight.configFactoryDefault();
        //right = new SpeedControllerGroup(frontRight, rearRight);
        frontLeft.configFactoryDefault();
        frontLeft.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 30);
        frontLeft.setSelectedSensorPosition(0); // LEFT IS WRONG DIRECTION BY DEFAULT
        rearLeft.configFactoryDefault();
        //left = new SpeedControllerGroup(frontLeft, rearLeft);
        //this.robotDrive = new DifferentialDrive(left, right);
        odometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(gyro.getAngle())), new Pose2d(0, 0, new Rotation2d()));
        chassisSpeeds = new ChassisSpeeds(0,0,0);
        reset();
    }
    //Mostly taken from last year's robot
    /**
     * The method to drive the robot.
     * @param moveValue the amount that the robot should move (Y axis, Joystick rawAxis 1)
     * @param rotateValue the amount that the robot should rotate (X axis, Joystick rawAxis 0)
     * @param scaleValue the amount that everything should be scaled by (usually given by the
     * little flap thing on the bottom of the joystick, Joystick rawAxis 3)
     */
    public void cheesyDrive(final double moveValue, final double rotateValue, final double adjustValue) {
        final double actualAdjustValue = ((-adjustValue + 1) / 2);
        final double movevalue = Math.abs(moveValue) < Constants.CheesyDrive.Y_AXIS_DEADZONE_RANGE
                ? 0
                : moveValue * actualAdjustValue;

        //Deadzone on x axis only if y value is small
        final double turnvalue = Math.abs(rotateValue) < Constants.CheesyDrive.X_AXIS_DEADZONE_RANGE
                && Math.abs(moveValue) < Constants.CheesyDrive.X_AXIS_DEADZONE_Y_MIN
                ? 0
                : rotateValue * actualAdjustValue;

        this.robotDrive.arcadeDrive(movevalue, turnvalue, true);
        //this.robotDrive.tankDrive((moveValue + rotateValue) * adjustValue, (moveValue - rotateValue) * adjustValue);
    }
    public BiConsumer<Double, Double> tankDriveVolts = (leftVolts, rightVolts) -> {
        left.setVoltage(leftVolts);
        right.setVoltage(-rightVolts);
        this.robotDrive.feed();
    };

    // public void tankDriveVolts(Double leftVolts, Double rightVolts){
    //     left.setVoltage(leftVolts);
    //     right.setVoltage(-rightVolts);
    //     this.robotDrive.feed();
    // }
    
    public void periodic() {
        updatePose();
        SmartDashboard.putNumber("TalonSRX 0 (front right) Temperature", frontRight.getTemperature());
        SmartDashboard.putNumber("TalonSRX 1 (rear right) Temperature", rearRight.getTemperature());
        SmartDashboard.putNumber("TalonSRX 2 (rear left) Temperature", rearLeft.getTemperature());
        SmartDashboard.putNumber("TalonSRX 3 (front left) Temperature", frontLeft.getTemperature());
        SmartDashboard.putNumber("Front Right Motor Position", ((double)(frontRight.getSelectedSensorPosition()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE); // 4096 per rotation 8pi circumfrance
        SmartDashboard.putNumber("Front Left Motor Position", ((double)(frontLeft.getSelectedSensorPosition()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE);
        SmartDashboard.putNumber("Front Right Motor Velocity", ((double)(frontRight.getSelectedSensorVelocity()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE);
        SmartDashboard.putNumber("Front Left Motor Velocity", ((double)(frontLeft.getSelectedSensorVelocity()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE);
        SmartDashboard.putNumberArray("test Array", new double[2]);
        SmartDashboard.putNumber("Linear Velocity", getLinearVelocity());
        SmartDashboard.putNumber("Angular Velocity", getAngularVelocity());
        //System.out.println(this.getLeftDistanceMeters());
        //System.out.println(odometry.getPoseMeters().getTranslation().getX());
        //System.out.println(getRadians());
        //System.out.println(this.getPose().toString());
        SmartDashboard.putString("Pose2d", this.getPose().toString());
    }
    public double getRightDistanceMeters(){
        return ((double)(frontRight.getSelectedSensorPosition()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE;
    }
    public double getLeftDistanceMeters(){
        return ((double)(-frontLeft.getSelectedSensorPosition()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE;
    }
    public double getRightVelocityMeters(){
        return ((double)(frontRight.getSelectedSensorVelocity()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE;
    }
    public double getLeftVelocityMeters(){
        return ((double)(-frontLeft.getSelectedSensorVelocity()) / Constants.DriveTrain.COUNTS_PER_ROTAION) * Constants.DriveTrain.WHEEL_CIRCUMFRANCE;
    }
    public double getRadians(){
        return Math.toRadians(-gyro.getAngle());
    }
    public Pose2d getPose(){
        return odometry.getPoseMeters().relativeTo(new Pose2d(new Translation2d(0,0), new Rotation2d((3*Math.PI)/2)));
    }
    private ChassisSpeeds updateVelocity(){
        return kinematics.toChassisSpeeds(new DifferentialDriveWheelSpeeds(getLeftVelocityMeters(),getRightVelocityMeters()));
    }
    public double getLinearVelocity(){
        return updateVelocity().vxMetersPerSecond;
    }
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftVelocityMeters(),getRightVelocityMeters());
      }
    /*
        ENCODER MESUREMENTS OFF BY A BIT, encoders display value of 240in, 6.09m when the actual distance is 212in 5.38m
        FIX ASAP CHECK WHEEL CIRCUMFRANCE
    */
    public double getAngularVelocity(){
        return updateVelocity().omegaRadiansPerSecond;
    }
    private void updatePose(){
        odometry.update(new Rotation2d(getRadians()), getLeftDistanceMeters(), getRightDistanceMeters());
    }
    public void reset(){
        frontLeft.setSelectedSensorPosition(0);
        frontRight.setSelectedSensorPosition(0);
        gyro.calibrate();
        odometry.resetPosition(new Pose2d(new Translation2d(0,0), new Rotation2d(getRadians())), new Rotation2d(getRadians()));
    }
}