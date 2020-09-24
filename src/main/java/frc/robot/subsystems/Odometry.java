package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.SubsystemBaseWrapper;

// Move to Constatns after finish 

public final class Odometry extends SubsystemBaseWrapper {

    // // Move to Constatns after finish 
    // private final double STARTING_POSITOIN_X = 1;
    // private final double STARTING_POSITOIN_Y = 1;
    // private Rotation2d rotation;
    // private Pose2d pose;
    // private double right, left;

    // private DifferentialDriveOdometry differentialDriveOdometry;

    // public Odometry() {
    //     super();
    //     this.right = 0;
    //     this.left = 0;
    //     differentialDriveOdometry = new DifferentialDriveOdometry(new Rotation2d(DriveTrain.getRotation()), new Pose2d(STARTING_POSITOIN_X, STARTING_POSITOIN_Y, rotation));
    // }

    // public void update(){
    //     differentialDriveOdometry.update(new Rotation2d(Math.toRadians(DriveTrain.getRotation())), right, left);
    //     SmartDashboard.putString("Odometry", differentialDriveOdometry.getPoseMeters().toString());

    // }
    // public void getDistance(double right, double left){
    //     this.right = right;
    //     this.left = left;
    // }
    // public Pose2d getPose(){
    //     return differentialDriveOdometry.getPoseMeters();
    // }
}
