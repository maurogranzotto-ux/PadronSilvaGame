package com.example.tdpadronsilva;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.List;

public class Tower {

    private final int col, row;

    private double range    = Constants.TOWER_RANGE;
    private double fireRate = Constants.TOWER_FIRE_RATE;

    private double cooldown = 0; // secondi rimanenti prima del prossimo sparo

    public Tower(int col, int row) {
        this.col = col;
        this.row = row;
    }


    // Centro X in pixel
    public double centerX() { return col * Constants.TILE + Constants.TILE / 2.0; }


    // Centro Y in pixel
    public double centerY() { return row * Constants.TILE + Constants.TILE / 2.0; }

    /**
     * Trova il nemico più avanzato nel raggio.
     * Restituisce null se nessun nemico è a tiro.
     */

    public Enemy findTarget(List<Enemy> enemies) {
        Enemy best  = null;
        int   bestIndex = -1;


        for (Enemy e : enemies) {
            if (e.isDead() || e.isReachedEnd()) continue;


            double dist = Math.hypot(e.getX() - centerX(), e.getY() - centerY());
            if (dist <= range && e.getPathIndex() > bestIndex) {
                best   = e;
                bestIndex = e.getPathIndex();
            }
        }
        return best;
    }


    public void draw(GraphicsContext gc) {
        double x = col * Constants.TILE;
        double y = row * Constants.TILE;
        int    T = Constants.TILE;


        // Base
        gc.setFill(Color.web("#34495e"));
        gc.fillRoundRect(x + 4, y + 4, T - 8, T - 8, 8, 8);


        // Struttura centrale
        gc.setFill(Color.web("#7f8c8d"));
        gc.fillRoundRect(x + 16, y + 16, T - 32, T - 32, 4, 4);


        // Canna
        gc.setFill(Color.web("#bdc3c7"));
        gc.fillOval(x + T / 2.0 - 5, y + T / 2.0 - 5, 10, 10);


        // Cerchio del raggio (visibile solo tenuemente)
        gc.setStroke(Color.color(1, 1, 1, 0.08));
        gc.setLineWidth(1);
        gc.strokeOval(centerX() - range, centerY() - range, range * 2, range * 2);
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public double getRange() {
        return range;
    }

    public void setRange(double range) {
        this.range = range;
    }

    public double getFireRate() {
        return fireRate;
    }

    public void setFireRate(double fireRate) {
        this.fireRate = fireRate;
    }

    public double getCooldown() {
        return cooldown;
    }

    public void setCooldown(double cooldown) {
        this.cooldown = cooldown;
    }
}
