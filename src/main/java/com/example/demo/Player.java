package com.example.demo;

public class Player {
    private String name;
    private int score = 501;
    private int totalPointsScored = 0;
    private int turnsCount = 0;

    public Player(String name) {
        this.name = name;
    }

    /**
     * Berechnet den neuen Score und aktualisiert die Statistik.
     */
    public void calculateScore(int points) {
        int tempScore = this.score - points;
        
        // Bust-Logik: Score bleibt gleich, wenn < 0 oder genau 1
        if (tempScore >= 0 && tempScore != 1) {
            this.score = tempScore;
        }
        
        // Statistik für Average (3-Dart-Schnitt)
        this.totalPointsScored += points;
        this.turnsCount++;
    }

    /**
     * Gibt den Durchschnitt pro 3-Dart-Aufnahme zurück.
     */
    public double getAverage() {
        if (turnsCount == 0) return 0.0;
        return (double) totalPointsScored / turnsCount;
    }

    public String getName() { return name; }
    public int getScore() { return score; }
}