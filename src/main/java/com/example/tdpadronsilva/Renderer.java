package com.example.tdpadronsilva;

import com.example.tdpadronsilva.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Si occupa esclusivamente di disegnare il gioco sul Canvas JavaFX.
 *
 * Il Renderer non modifica mai lo stato: legge da GameState e scrive
 * pixel sul GraphicsContext. Questo schema (separazione update/render)
 * rende semplice aggiungere effetti visivi senza toccare la logica.
 */
public class Renderer {
    private final GraphicsContext gc;
    private final GameState       state;

    public Renderer(GraphicsContext gc, GameState state) {
        this.gc    = gc;
        this.state = state;
    }


    public void render() {
        drawBackground();
        state.map.draw(gc);   // griglia + frecce percorso
        drawEntities();
        drawUI();
        if (state.gameOver) drawGameOver();
    }
    private void drawBackground() {
        gc.setFill(Color.web("#1a1a2e"));
        gc.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
    }
    private void drawEntities() {
        // Ordine: torri → nemici → proiettili (z-order)
        for (Tower t : state.towers)  t.draw(gc);
        for (Enemy e : state.enemies) e.draw(gc);
        for (Bullet b : state.bullets) b.draw(gc);
    }
    private void drawUI() {
        int uiY = Constants.ROWS * Constants.TILE;
        // Sfondo barra
        gc.setFill(Color.web("#0d2137"));
        gc.fillRect(0, uiY, Constants.WIDTH, 70);
        // Valori principali
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc.setFill(Color.web("#f0c040"));
        gc.fillText("💰 " + state.money, 20, uiY + 32);
        gc.setFill(Color.web("#e74c3c"));
        gc.fillText("❤️  " + state.lives, 190, uiY + 32);
        gc.setFill(Color.web("#5dade2"));
        gc.fillText("Ondata: " + state.waveManager.getWave(), 340, uiY + 32);
        gc.setFill(Color.web("#58d68d"));
        gc.fillText("Score: " + state.score, 500, uiY + 32);
        // Suggerimento
        gc.setFont(Font.font("Arial", 12));
        gc.setFill(Color.web("#888888"));
        gc.fillText("Clicca su una cella verde per piazzare una torre  "
                + "(costo: " + Constants.TOWER_COST + " monete)", 20, uiY + 58);
    }
    private void drawGameOver() {
        // Overlay
        gc.setFill(Color.color(0, 0, 0, 0.72));
        gc.fillRect(0, 0, Constants.WIDTH, Constants.ROWS * Constants.TILE);
        double cx = Constants.WIDTH / 2.0;
        double cy = Constants.ROWS * Constants.TILE / 2.0;
        // Titolo
        gc.setFill(Color.web("#e74c3c"));
        gc.setFont(Font.font("Arial", FontWeight.BOLD, 56));
        gc.fillText("GAME OVER", cx - 175, cy - 10);
        // Statistiche finali
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 24));
        gc.fillText("Punteggio finale: " + state.score,               cx - 115, cy + 42);
        gc.fillText("Ondata raggiunta: " + state.waveManager.getWave(), cx - 115, cy + 74);
    }
}

