package frc.robot.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class Buttons {
    public final Button A;         
    public final Button B;         
    public final Button X;         
    public final Button Y;         
    public final Button LB; 
    public final Button RB;
    public final Button back;
    public final Button start;
    public final Button LS;
    public final Button RS;
    public final DPad dPad;
    
    public Buttons(GenericHID controller) {
        A               = new JoystickButton(controller, 1); 
        B               = new JoystickButton(controller, 2); 
        X               = new JoystickButton(controller, 3);
        Y               = new JoystickButton(controller, 4); 
        LB              = new JoystickButton(controller, 5); 
        RB              = new JoystickButton(controller, 6); 
        back            = new JoystickButton(controller, 7); 
        start           = new JoystickButton(controller, 8);
        LS              = new JoystickButton(controller, 9); 
        RS              = new JoystickButton(controller, 10);
        
        dPad = new DPad(controller);
    }
}