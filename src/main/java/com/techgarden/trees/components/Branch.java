package com.techgarden.trees.components;

public class Branch {
    private double length;

    public Branch(double lengthInCm) {
        this.length = lengthInCm;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void increaseLength(double centimeters) {
        this.length += centimeters;
    }
}
