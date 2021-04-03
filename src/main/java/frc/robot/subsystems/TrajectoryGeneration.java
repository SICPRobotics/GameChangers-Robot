package frc.robot.subsystems;

import java.util.List;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveKinematicsConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.EllipticalRegionConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.RectangularRegionConstraint;
import edu.wpi.first.wpilibj.trajectory.constraint.TrajectoryConstraint;
import frc.robot.SubsystemBaseWrapper;

public final class TrajectoryGeneration extends SubsystemBaseWrapper {
    private TrajectoryConfig trajectoryConfig;
    private Pose2d startPoint,endPoint;
    private List<Translation2d> wayPoints;
    private Trajectory trajectory;
    private DriveTrain driveTrain;
    private boolean generated;
    public TrajectoryGeneration(Pose2d startPoint, Pose2d endPoint, List<Translation2d> wayPoints, DriveTrain subsystem) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.wayPoints = wayPoints;
        this.driveTrain = subsystem;
        this.generated = false;
        trajectoryConfig = new TrajectoryConfig(4, 0.5); //max v and a 2m/s and 0.5m/s^2 
        trajectoryConfig.setKinematics(driveTrain.kinematics);
        trajectoryConfig.addConstraint(new DifferentialDriveKinematicsConstraint(driveTrain.kinematics, 2));
        //trajectoryConfig.addConstraint(new EllipticalRegionConstraint(center, xWidth, yWidth, rotation, constraint))
        System.out.println("TrajectoryGeneration Constructor");
    }
    public TrajectoryGeneration(String trajectoryInput){

    }
    public void generateTrajectory(){
        System.out.println("Begin Trajectory Generation");
        if(!generated){
            trajectory = TrajectoryGenerator.generateTrajectory(startPoint, wayPoints, endPoint, trajectoryConfig);
            generated = true;
        }
        else{
            System.out.println("Trajectory Already Generated"); 
        }
        System.out.println("End Trajectory Generation");
    }
    public Trajectory getTrajectory() {
        return trajectory;
    }
}






// trajectoryConfig.addConstraint(
        //     new DifferentialDriveVoltageConstraint(
        //         new SimpleMotorFeedforward(
        //             Constants.VoltageConstants.kS,Constants.VoltageConstants.kV,Constants.VoltageConstants.kA),
        //             driveTrain.kinematics, 10));