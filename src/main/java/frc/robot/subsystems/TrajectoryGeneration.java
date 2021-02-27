package frc.robot.subsystems;

import java.util.List;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants;
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
        trajectoryConfig = new TrajectoryConfig(4, 2); //max v and a 4m/s and 4m/s^2 respectivly dosen't really matter right now but can fine tune it later
        // trajectoryConfig.addConstraint(
        //     new DifferentialDriveVoltageConstraint(
        //         new SimpleMotorFeedforward(
        //             Constants.VoltageConstants.kS,Constants.VoltageConstants.kV,Constants.VoltageConstants.kA),
        //             driveTrain.kinematics, 10));
        trajectoryConfig.setKinematics(driveTrain.kinematics);
        System.out.println("TrajectoryGeneration Constructor");
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
            //trajectory.relativeTo(new Pose2d(new Translation2d(0,0), new Rotation2d((3*Math.PI)/2)));
        System.out.println("End Trajectory Generation");
    }
    public Trajectory getTrajectory() {
        return trajectory;
    }
}