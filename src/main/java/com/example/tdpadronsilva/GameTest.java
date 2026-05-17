package com.example.tdpadronsilva;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GameTest extends Application {

    private GameState state;
    private Renderer  renderer;

    @Override
    public void start(Stage stage) {

        Canvas canvas = new Canvas(Constants.WIDTH, Constants.HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Usa GameState e Renderer che hai già scritto
        state    = new GameState();
        renderer = new Renderer(gc, state);

        // Click del mouse per piazzare torri
        canvas.setOnMouseClicked(e -> {
            int col = (int)(e.getX() / Constants.TILE);
            int row = (int)(e.getY() / Constants.TILE);
            state.tryPlaceTower(col, row);
        });

        stage.setScene(new Scene(new VBox(canvas)));
        stage.setTitle("Tower Defense");
        stage.setResizable(false);
        stage.show();

        // Game loop
        new AnimationTimer() {
            long lastNano = 0;

            @Override
            public void handle(long nowNano) {
                double dt = (lastNano == 0) ? 0.0
                        : (nowNano - lastNano) / 1_000_000_000.0;
                lastNano = nowNano;

                if (!state.gameOver) {
                    state.update(dt, nowNano);
                }
                renderer.render();
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}