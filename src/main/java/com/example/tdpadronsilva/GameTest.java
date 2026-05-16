package com.example.tdpadronsilva;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameTest extends Application {

    private GameMap map;
    private java.util.List<Enemy> enemies;
    private java.util.List<Tower> towers;
    private WaveManager waveManager;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Inizializza
        map         = new GameMap();
        enemies     = new java.util.ArrayList<>();
        towers      = new java.util.ArrayList<>();
        waveManager = new WaveManager();
        waveManager.startNextWave();

        // Piazza qualche torre fissa per vedere se si disegnano
        towers.add(new Tower(1, 3));
        towers.add(new Tower(5, 2));
        towers.add(new Tower(9, 5));

        // Click per piazzare torri
        canvas.setOnMouseClicked(e -> {
            int col = (int)(e.getX() / Constants.TILE);
            int row = (int)(e.getY() / Constants.TILE);
            if (map.canPlace(col, row)) {
                towers.add(new Tower(col, row));
                map.placeTower(col, row);
            }
        });

        stage.setScene(new Scene(new VBox(canvas)));
        stage.setTitle("GameTest - visuale");
        stage.setResizable(false);
        stage.show();

        // Game loop
        new AnimationTimer() {
            long lastNano = 0;

            @Override
            public void handle(long nowNano) {
                double dt = (lastNano == 0) ? 0.0 : (nowNano - lastNano) / 1_000_000_000.0;
                lastNano = nowNano;

                // Spawn nemici
                waveManager.update(nowNano, enemies);

                // Aggiorna nemici
                enemies.removeIf(e -> e.isDead() || e.isReachedEnd());
                for (Enemy e : enemies) e.update(dt);

                // Prossima ondata
                if (waveManager.isWaveComplete(enemies)) waveManager.startNextWave();

                // Disegna
                gc.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
                map.draw(gc);
                for (Tower t  : towers)  t.draw(gc);
                for (Enemy e  : enemies) e.draw(gc);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}