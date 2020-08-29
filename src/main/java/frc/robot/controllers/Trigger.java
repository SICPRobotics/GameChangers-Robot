package frc.robot.controllers;

import java.util.function.DoubleSupplier;

public class Trigger {
    private final DoubleSupplier getSupplier;
    public Trigger(DoubleSupplier getSupplier) {
        this.getSupplier = getSupplier;
    }

    public double get() {
        return this.getSupplier.getAsDouble();
    }
}