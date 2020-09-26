package frc.robot.truth;

import java.util.ArrayList;
import java.util.function.Consumer;

public class Listeners extends ArrayList<Consumer<Truth>> {
    public void fire(Truth truth) {
        forEach(consumer -> consumer.accept(truth));
    }
}