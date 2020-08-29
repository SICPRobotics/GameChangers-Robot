package frc.robot.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.controllers.buttons.DPadButton;
import frc.robot.controllers.buttons.DPadButton.Direction;

public class DPad {
    public final Button up;
    public final Button right;
    public final Button down;
    public final Button left;
    public DPad(GenericHID controller) {
        up = new DPadButton(controller, Direction.UP);
        right = new DPadButton(controller, Direction.RIGHT);
        down = new DPadButton(controller, Direction.DOWN);
        left = new DPadButton(controller, Direction.LEFT);
    }
}