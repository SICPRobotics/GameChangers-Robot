package frc.robot.truth;

import java.util.function.Consumer;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import frc.robot.pi_client.PiClient;
import frc.robot.pi_client.VisionObserver;
import frc.robot.pi_client.VisionStatus;

/**
 * The Ministry of Truth for our robot. It's the way that we'll be uniting the vision position data and odometry data, along with other things,
 * into one source of information. Name is a reference to 1984.
 */
public class Minitrue implements VisionObserver {

    private final PiClient piClient;
    private Truth truth;

    private final Listeners beforePoseSet = new Listeners();
    public Minitrue beforePoseSet(Consumer<Truth> consumer) {
        beforePoseSet.add(consumer);
        return this;
    }

    private final Listeners beforePoseUpdate = new Listeners();
    public Minitrue beforePoseUpdate(Consumer<Truth> consumer) {
        beforePoseUpdate.add(consumer);
        return this;
    }

    private final Listeners onPoseUpdate = new Listeners();
    public Minitrue onPoseUpdate(Consumer<Truth> consumer) {
        onPoseUpdate.add(consumer);
        return this;
    }

    public Minitrue(PiClient piClient) {
        this.piClient = piClient;
        this.piClient.onVisionUpdate(this);
        truth = new Truth();
        truth.odometry = new DifferentialDriveOdometry(new Rotation2d());
    }

    /**
     * Called when the vision sends an update.
     */
    public void onVisionUpdate(VisionStatus status) {
        // Do stuff with the vision data
    }

    /**
     * @return The robot's truth (aka state).
     */
    public Truth getTruth() {
        return truth;
    }

    /**
     * @return The robot's pose, in meters, taken from the robot's truth.
     */
    public Pose2d getPose() {
        return truth.odometry.getPoseMeters();
    }

    public Pose2d updatePose(Rotation2d gyroAngle, double leftDistanceMeters, double rightDistanceMeters) {
        return truth.odometry.update(gyroAngle, leftDistanceMeters, rightDistanceMeters);
    }

    public Pose2d setPose(Rotation2d gyroAngle, Rotation2d robotRotation, double x, double y) {
        truth.odometry.resetPosition(new Pose2d(x, y, robotRotation), gyroAngle);
        return getPose();
    }
}