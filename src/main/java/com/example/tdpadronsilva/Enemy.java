package com.example.tdpadronsilva;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Un nemico che percorre il PATH dalla prima all'ultima cella.
 *
 * Logica di movimento:
 *   Ogni frame, il nemico calcola il vettore verso il prossimo waypoint
 *   (PATH[pathIndex + 1]) e si sposta di (speed * dt) pixel.
 *   Quando raggiunge il waypoint avanza all'indice successivo.
 */

public class Enemy {
    // posizione e movimento
    private double x, y;
    private int pathIndex; // waypoint attuale (PATH[pathIndex])
    private double speed; // pixel al secondo

    // vita
    private double hp, maxHp;

    // flag di stato
    private boolean dead;
    private boolean reachedEnd;

    public Enemy(int wave){
        // posiziona all'inizio del percorso
        this.x = Constants.PATH[0][0] * Constants.TILE + Constants.TILE / 2.0;
        this.y = Constants.PATH[0][1] * Constants.TILE + Constants.TILE / 2.0;
        this.pathIndex = 0;

        // ogni wave i nemici sono più forti e veloci
        this.maxHp = hp = 40 + wave * 18;
        this.speed = 55 + wave * 6;

        this.dead = false;
        this.reachedEnd = false;
    }

    public void update(double dt){ // delta time: tempo trascorso dall'ultimo frame in secondi;

        // Se questo nemico ha raggionto la fine del percorso, update() termina.
        if (pathIndex + 1 >= Constants.PATH.length) {
            this.reachedEnd = true;
            return;
        }

        // Calcola il vettore verso il prossimo waypoint.
        int nextCol = Constants.PATH[this.pathIndex + 1][0];
        int nextRow = Constants.PATH[this.pathIndex + 1][1];
        double targetX = nextCol * Constants.TILE + Constants.TILE / 2.0;
        double targetY = nextRow * Constants.TILE + Constants.TILE / 2.0;

        double dx = targetX - this.x;
        double dy = targetY - this.y;
        double dist = Math.hypot(dx, dy);
        double move  = speed * dt;

        if (move >= dist){
            // Arrivato al waypoint: salta al successivo
            this.x = targetX;
            this.y = targetY;
            this.pathIndex++;
        } else {
            this.x += (dx / dist) * move;
            this.y += (dy / dist) * move;
        }
    }

    public void draw(GraphicsContext gc) {
        double r = 13;

        // Corpo
        gc.setFill(Color.web("#c0392b"));
        gc.fillOval(this.x - r, this.y - r, r * 2, r * 2);
        gc.setStroke(Color.web("#e74c3c"));
        gc.setLineWidth(1.5);
        gc.strokeOval(this.x - r, this.y - r, r * 2, r * 2);


        // Barra HP
        double barW = 30, barH = 5;
        double barX = this.x - barW / 2.0;
        double barY = this.y - r - 9;


        gc.setFill(Color.web("#4a0000"));
        gc.fillRect(barX, barY, barW, barH);
        gc.setFill(Color.web("#27ae60"));
        gc.fillRect(barX, barY, barW * (this.hp / this.maxHp), barH);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int pathIndex) {
        this.pathIndex = pathIndex;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getHp() {
        return hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(double maxHp) {
        this.maxHp = maxHp;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public boolean isReachedEnd() {
        return reachedEnd;
    }

    public void setReachedEnd(boolean reachedEnd) {
        this.reachedEnd = reachedEnd;
    }
}
