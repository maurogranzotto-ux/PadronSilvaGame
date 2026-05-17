package com.example.tdpadronsilva;

/**
 * Costanti globali del gioco.
 * Centralizzare i valori qui rende facile modificare il bilanciamento
 * senza dover cercare numeri "magici" sparsi nel codice.
 */
public class Constants {


    // Griglia
    public static final int TILE   = 60;           // pixel per cella
    public static final int COLS   = 14;           // colonne (celle)
    public static final int ROWS   = 9;            // righe   (celle)
    public static final int WIDTH  = COLS * TILE;  // larghezza area di gioco (pixel)
    public static final int HEIGHT = ROWS * TILE + 70; // + barra UI          (pixel)


    // Percorso nemici ({colonna, riga} in sequenza)
    public static final int[][] PATH = {
            {0,1},{1,1},{2,1},{3,1},
            {3,2},{3,3},{3,4},{3,5},
            {4,5},{5,5},{6,5},
            {6,4},{6,3},{6,2},{6,1},
            {7,1},{8,1},
            {8,2},{8,3},{8,4},{8,5},{8,6},{8,7},
            {9,7},{10,7},
            {10,6},{10,5},{10,4},{10,3},
            {11,3},{12,3},{13,3}
    };


    // Torri
    public static final int    TOWER_COST      = 50;
    public static final double TOWER_RANGE     = 2.2 * TILE;
    public static final double TOWER_FIRE_RATE = 0.9;   // secondi tra colpi


    // Proiettili
    public static final double BULLET_SPEED  = 280.0;
    public static final int    BULLET_DAMAGE = 12;


    // Stato iniziale del giocatore
    public static final int STARTING_MONEY = 150;
    public static final int STARTING_LIVES = 20;


    // Ondate
    public static final long SPAWN_INTERVAL_BASE = 1_400_000_000L; // ns
    public static final long SPAWN_INTERVAL_MIN  =   600_000_000L; // ns
    public static final long SPAWN_INTERVAL_STEP =    80_000_000L; // riduzione per ondata


    // Costruttore privato: questa è una classe di sole costanti
    private Constants() {}
}
