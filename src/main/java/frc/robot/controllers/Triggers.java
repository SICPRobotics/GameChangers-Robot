package frc.robot.controllers;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Triggers extends Mirrored<Trigger> {
    public Triggers(XboxController controller) {
        left = new Trigger(() -> controller.getTriggerAxis(Hand.kLeft));
        right = new Trigger(() -> controller.getTriggerAxis(Hand.kRight));
    }
}