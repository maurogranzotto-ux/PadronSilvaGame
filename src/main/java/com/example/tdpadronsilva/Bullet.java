package com.example.tdpadronsilva;

import com.example.tdpadronsilva.Constants;
import com.example.tdpadronsilva.Enemy;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


/**
 * Proiettile "homing": insegue il bersaglio ogni frame.
 *
 * Non segue una traiettoria balistica: ogni frame ricalcola la direzione
 * verso la posizione attuale del nemico, quindi lo raggiunge sempre
 * anche se il nemico si muove.
 *
 * Viene rimosso dal gioco quando:
 *   - raggiunge il bersaglio (hasHit = true)
 *   - il bersaglio muore prima di essere colpito
 *   - il bersaglio raggiunge la fine del percorso
 */
public class Bullet {


    // ── Posizione ─────────────────────────────────────────────────────────
    private double x, y;


    // ── Riferimento al bersaglio ──────────────────────────────────────────
    private final Enemy target;


    // ── Statistiche ───────────────────────────────────────────────────────
    private double speed = Constants.BULLET_SPEED;
    private int damage = Constants.BULLET_DAMAGE;


    // ── Stato ─────────────────────────────────────────────────────────────
    private boolean hasHit = false;


    // ─────────────────────────────────────────────────────────────────────


    public Bullet(double startX, double startY, Enemy target) {
        this.x = startX;
        this.y = startY;
        this.target = target;
    }


    public void update(double dt) {
        double dx = target.getX() - x;
        double dy = target.getY() - y;
        double dist = Math.hypot(dx, dy);


        // Raggio di collisione: se siamo vicini abbastanza, colpiamo
        if (dist < 8) {
            target.setHp(target.getHp()-damage);
            if (target.getHp() <= 0) target.setDead(true);
            hasHit = true;
            return;
        }


        double move = speed * dt;
        x += (dx / dist) * move;
        y += (dy / dist) * move;
    }


    public void draw(GraphicsContext gc) {
        gc.setFill(Color.web("#f1c40f"));
        gc.fillOval(x - 5, y - 5, 10, 10);
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

    public Enemy getTarget() {
        return target;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public boolean isHasHit() {
        return hasHit;
    }

    public void setHasHit(boolean hasHit) {
        this.hasHit = hasHit;
    }
}
