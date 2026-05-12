package com.example.demo;

public class DartThrow {
    private final int points;
    private final String label;

    public DartThrow(int value, int multiplier) {
        this.points = value * multiplier;
        
        // Label-Logik
        if (value == 0) {
            this.label = "Miss";
        } else if (value == 25) {
            this.label = (multiplier == 2) ? "D-Bull" : "S-Bull";
        } else {
            String prefix = (multiplier == 3) ? "T-" : (multiplier == 2) ? "D-" : "S-";
            this.label = prefix + value;
        }
    }

    public int getPoints() { return points; }
    public String getLabel() { return label; }
}