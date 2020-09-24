package frc.robot.truth;

import frc.robot.pi_client.PiClient;
import frc.robot.pi_client.VisionObserver;
import frc.robot.pi_client.VisionStatus;

/**
 * The Ministry of Truth for our robot. It's the way that we'll be uniting the vision position data and odometry data, along with other things,
 * into one source of information. Name is a reference to 1984.
 */
public class Minitrue implements VisionObserver {

    private final PiClient piClient;

    public Minitrue(PiClient piClient) {
        this.piClient = piClient;
        this.piClient.onVisionUpdate(this);
    }

    public void onVisionUpdate(VisionStatus status) {
        // Do stuff with the vision data
    }

    /**
     * TODO
     * @return Return's the robot's truth.
     */
    public Truth getTruth() {
        return null;
    }
}