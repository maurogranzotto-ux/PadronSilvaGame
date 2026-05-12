package com.example.tdpadronsilva;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameTest extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Setup della finestra (usa le tue costanti per le dimensioni se le hai)
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // 2. Istanziamo un nemico (Wave 1)
        Enemy testEnemy = new Enemy(1);

        // 3. Game Loop (AnimationTimer chiama handle circa 60 volte al secondo)
        new AnimationTimer() {
            long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                // Calcolo del dt (delta time) in secondi
                double dt = (now - lastTime) / 1_000_000_000.0;
                lastTime = now;

                // --- LOGICA ---
                testEnemy.update(dt);

                // --- RENDERING ---
                // Puliamo lo sfondo
                gc.setFill(Color.LIGHTGRAY);
                gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // (Opzionale) Disegniamo il percorso per vedere se il nemico lo segue bene
                drawPath(gc);

                // Disegniamo il nemico
                testEnemy.draw(gc);

                // Feedback in console se raggiunge la fine
                // Nota: dovrai aggiungere un getter 'isReachedEnd()' nella tua classe Enemy
                // if (testEnemy.isReachedEnd()) System.out.println("Nemico arrivato!");
            }
        }.start();

        stage.setScene(new Scene(new Group(canvas)));
        stage.setTitle("Test Movimento Nemico");
        stage.show();
    }

    // Metodo di supporto per vedere i waypoint di Constants.PATH
    private void drawPath(GraphicsContext gc) {
        gc.setStroke(Color.DARKGRAY);
        gc.setLineWidth(2);
        for (int i = 0; i < Constants.PATH.length - 1; i++) {
            double x1 = Constants.PATH[i][0] * Constants.TILE + Constants.TILE / 2.0;
            double y1 = Constants.PATH[i][1] * Constants.TILE + Constants.TILE / 2.0;
            double x2 = Constants.PATH[i+1][0] * Constants.TILE + Constants.TILE / 2.0;
            double y2 = Constants.PATH[i+1][1] * Constants.TILE + Constants.TILE / 2.0;
            gc.strokeLine(x1, y1, x2, y2);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}