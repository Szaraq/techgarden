package com.techgarden.trees.components;

public class Trunk {
    private double diameter;

    public Trunk(double diameterInCm) {
        this.diameter = diameterInCm;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public void increaseDiameter(double centimeters) {
        this.diameter += centimeters;
    }
}
