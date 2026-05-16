package com.example.tdpadronsilva;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Rappresenta la mappa di gioco: griglia, percorso, torri piazzate.
 *
 * Responsabilità:
 *   - Tenere traccia di quali celle sono percorso o hanno una torre
 *   - Rispondere a query di validità (canPlace, isPath, hasTower)
 *   - Disegnare la griglia e le frecce di direzione
 */

public class GameMap {
    private final boolean[][] isPath; // celle che fanno parte del percorso
    private final boolean[][] hasTower;  // celle occupate da una torre

    public GameMap() {
        this.isPath = new boolean[Constants.COLS][Constants.ROWS];
        this.hasTower = new boolean[Constants.COLS][Constants.ROWS];

        // Marca le celle del PATH come percorso
        for (int i = 0; i < Constants.PATH.length; i++) {
            // i indica il numero della coppia
            // 0 e 1 indicano il primo e secondo numero all'interno della coppia
            isPath[Constants.PATH[i][0]][Constants.PATH[i][1]] = true;
        }
    }

    // True se la cella esiste fisicamente dentro la griglia di gioco
    private boolean inBounds(int col, int row) {
        return col >= 0 && col < Constants.COLS
                && row >= 0 && row < Constants.ROWS;
    }

    //True se la cella è percorribile dai nemici
    public boolean isPathCell(int col, int row) {
        return inBounds(col, row) && isPath[col][row];
    }

    // True se c'è già una torre su questa cella
    public boolean hasTowerAt(int col, int row) {
        return inBounds(col, row) && hasTower[col][row];
    }


    // True se la cella è libera (non percorso, non torre, dentro i limiti)
    public boolean canPlace(int col, int row) {
        return inBounds(col, row) && !isPath[col][row] && !hasTower[col][row];
    }

    // Registra una torre su questa cella (chiamare dopo aver creato l'oggetto Torre)
    public void placeTower(int col, int row) {
        if (inBounds(col, row)) hasTower[col][row] = true;
    }

    public void draw(GraphicsContext gc) {
        drawGrid(gc);
        drawPathArrows(gc);
    }


    private void drawGrid(GraphicsContext gc) {
        int T = Constants.TILE;
        for (int c = 0; c < Constants.COLS; c++) {
            for (int r = 0; r < Constants.ROWS; r++) {
                gc.setFill(isPath[c][r]
                        ? Color.web("#7a5c3a")   // strada (marrone)
                        : Color.web("#2a5427")); // erba (verde scuro)
                gc.fillRect(c * T, r * T, T - 1, T - 1);
            }
        }
    }


    // Piccole frecce al centro di ogni cella di percorso per indicare la direzione
    private void drawPathArrows(GraphicsContext gc) {
        gc.setFill(Color.color(1, 1, 1, 0.18));
        gc.setFont(Font.font("Arial", 20));
        int T = Constants.TILE;


        for (int i = 0; i < Constants.PATH.length - 1; i++) {
            int dc = Constants.PATH[i + 1][0] - Constants.PATH[i][0];
            int dr = Constants.PATH[i + 1][1] - Constants.PATH[i][1];
            String arrow = (dc > 0) ? "→" : (dc < 0) ? "←" : (dr > 0) ? "↓" : "↑";


            double px = Constants.PATH[i][0] * T + T / 2.0 - 7;
            double py = Constants.PATH[i][1] * T + T / 2.0 + 7;
            gc.fillText(arrow, px, py);
        }
    }

}