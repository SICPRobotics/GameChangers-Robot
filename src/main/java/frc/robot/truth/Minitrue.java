package frc.robot.truth;

import edu.wpi.first.wpilibj.geometry.Pose2d;
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

    public Minitrue(PiClient piClient) {
        this.piClient = piClient;
        this.piClient.onVisionUpdate(this);
        truth = new Truth();
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
     * @return The robot's pose taken from the robot's truth.
     */
    public Pose2d getPose() {
        return truth.pose;
    }
}