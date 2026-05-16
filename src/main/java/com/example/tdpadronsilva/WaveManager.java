package com.example.tdpadronsilva;
import java.util.List;

/**
 * Gestisce il ciclo delle ondate: quanti nemici spawnare, con quale ritmo,
 * quando iniziare la prossima ondata.
 *
 * Separare questa logica da GameState rende facile modificare
 * il bilanciamento o aggiungere tipi diversi di ondate in futuro.
 */

public class WaveManager {
    private int wave = 0;
    private int enemiesInWave = 0;
    private int  enemiesSpawned = 0;
    private long lastSpawnNano = 0;
    private long spawnInterval; // nanosecondi tra uno spawn e l'altro

    public WaveManager() {
        this.spawnInterval = Constants.SPAWN_INTERVAL_BASE;
    }

    public void startNextWave() {
        this.wave++;
        this.enemiesInWave  = 4 + wave * 3;    // ondate sempre più dense
        this.enemiesSpawned = 0;


        // Il ritmo di spawn aumenta ogni ondata (fino a un minimo)
        this.spawnInterval = Math.max(
                Constants.SPAWN_INTERVAL_MIN,
                Constants.SPAWN_INTERVAL_BASE - (long) this.wave * Constants.SPAWN_INTERVAL_STEP
        );
    }

    /**
     * Chiamato ogni frame. Se è il momento giusto, spawna un nuovo nemico
     * aggiungendolo alla lista passata come parametro.
     *
     * return true se è stato spawnato un nemico
     */

    public boolean update(long nowNano, List<Enemy> enemies) {
        if (this.enemiesSpawned >= this.enemiesInWave) return false;


        if (nowNano - this.lastSpawnNano >= this.spawnInterval) {
            enemies.add(new Enemy(this.wave));
            this.enemiesSpawned++;
            this.lastSpawnNano = nowNano;
            return true;
        }
        return false;
    }


    /**
     * True quando tutti i nemici dell'ondata sono stati spawnati
     * e il campo di gioco è vuoto.
     */
    public boolean isWaveComplete(List<Enemy> enemies) {
        return this.enemiesSpawned >= this.enemiesInWave && enemies.isEmpty();
    }

    public int getWave() { return this.wave; }
}

