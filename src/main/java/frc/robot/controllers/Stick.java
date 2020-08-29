package frc.robot.controllers;

import java.util.function.DoubleSupplier;

public class Stick {
    private DoubleSupplier getXSupplier, getYSupplier;
    public Stick(DoubleSupplier getX, DoubleSupplier getY) {
        this.getXSupplier = getX;
        this.getYSupplier = getY;
    }

    public double getX() {
        return deadzone(this.getXSupplier.getAsDouble());
    }

    public double getY() {
        return - deadzone(this.getYSupplier.getAsDouble());
    }

    public double deadzone(double value) {
        return Math.abs(value) < 0.1 ? 0 : value;
    }
}