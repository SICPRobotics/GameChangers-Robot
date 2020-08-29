package frc.robot.controllers;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class Sticks extends Mirrored<Stick>{
    public final Stick left, right;
    public Sticks(GenericHID controller) {
        left =  new Stick(() -> controller.getX(Hand.kLeft),  ()-> controller.getY(Hand.kLeft));
        right = new Stick(() -> controller.getX(Hand.kRight), ()-> controller.getY(Hand.kRight));
    }

    
}