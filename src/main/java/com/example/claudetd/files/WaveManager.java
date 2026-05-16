package com.example.claudetd.files;
import com.example.tdpadronsilva.Constants;
import com.example.tdpadronsilva.Enemy;

import java.util.List;


/**
 * Gestisce il ciclo delle ondate: quanti nemici spawnare, con quale ritmo,
 * quando iniziare la prossima ondata.
 *
 * Separare questa logica da GameState rende facile modificare
 * il bilanciamento o aggiungere tipi diversi di ondate in futuro.
 */
public class WaveManager {


    private int  wave           = 0;
    private int  enemiesInWave  = 0;
    private int  enemiesSpawned = 0;
    private long lastSpawnNano  = 0;
    private long spawnInterval;   // nanosecondi tra uno spawn e l'altro


    // ─────────────────────────────────────────────────────────────────────


    public WaveManager() {
        spawnInterval = Constants.SPAWN_INTERVAL_BASE;
    }


    // ── Ciclo di vita ondata ──────────────────────────────────────────────


    /** Prepara i parametri per l'ondata successiva */
    public void startNextWave() {
        wave++;
        enemiesInWave  = 4 + wave * 3;    // ondate sempre più dense
        enemiesSpawned = 0;


        // Il ritmo di spawn aumenta ogni ondata (fino a un minimo)
        spawnInterval = Math.max(
                Constants.SPAWN_INTERVAL_MIN,
                Constants.SPAWN_INTERVAL_BASE - (long) wave * Constants.SPAWN_INTERVAL_STEP
        );
    }


    /**
     * Chiamato ogni frame. Se è il momento giusto, spawna un nuovo nemico
     * aggiungendolo alla lista passata come parametro.
     *
     * @return true se è stato spawnato un nemico
     */
    public boolean update(long nowNano, List<Enemy> enemies) {
        if (enemiesSpawned >= enemiesInWave) return false;


        if (nowNano - lastSpawnNano >= spawnInterval) {
            enemies.add(new Enemy(wave));
            enemiesSpawned++;
            lastSpawnNano = nowNano;
            return true;
        }
        return false;
    }


    /**
     * True quando tutti i nemici dell'ondata sono stati spawnati
     * e il campo di gioco è vuoto.
     */
    public boolean isWaveComplete(List<Enemy> enemies) {
        return enemiesSpawned >= enemiesInWave && enemies.isEmpty();
    }


    // ── Getter ────────────────────────────────────────────────────────────


    public int getWave() { return wave; }
}
