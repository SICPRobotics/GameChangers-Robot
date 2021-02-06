package frc.robot.controllers;

import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.XboxController;

public class OperatorController {
    public final Buttons buttons;
    public final Sticks sticks;
    public final Triggers triggers;

    private final XboxController controller;
    public OperatorController(int port) {
        controller = new XboxController(port);
        
        buttons = new Buttons(controller);
        sticks = new Sticks(controller);
        triggers = new Triggers(controller);
    }

    /*
    public double getRightTriggerAxis() {
        return controller.getTriggerAxis(Hand.kRight);
    }

    public double getLeftTriggerAxis() {
        return controller.getTriggerAxis(Hand.kLeft);
    }
    */
    
    public void rumbleRight(double value) {
        controller.setRumble(RumbleType.kRightRumble, value);
    }

    public void rumbleLeft(double value) {
        controller.setRumble(RumbleType.kLeftRumble, value);
    }
}