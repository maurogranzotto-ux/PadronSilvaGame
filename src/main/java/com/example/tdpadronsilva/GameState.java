package com.example.tdpadronsilva;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Stato centrale del gioco.
 *
 * Contiene tutte le entità (torri, nemici, proiettili) e le risorse del
 * giocatore (monete, vite, punteggio). Coordina l'aggiornamento di ogni
 * sottosistema chiamando i loro metodi update() nell'ordine corretto.
 *
 * Responsabilità:
 *   - Tick di simulazione: spawn, movimento, combattimento, pulizia
 *   - Transizione tra ondate (delega a WaveManager)
 *   - Piazzamento torri (validazione + costo)
 */
public class GameState {


    public int     money    = Constants.STARTING_MONEY;
    public int     lives    = Constants.STARTING_LIVES;
    public int     score    = 0;
    public boolean gameOver = false;


    public final List<Tower>  towers  = new ArrayList<>();
    public final List<Enemy>  enemies = new ArrayList<>();
    public final List<Bullet> bullets = new ArrayList<>();

    public final GameMap map         = new GameMap();
    public final WaveManager waveManager = new WaveManager();

    public GameState() {
        waveManager.startNextWave();
    }

    /**
     * Aggiorna l'intera simulazione per un frame.
     *
     * @param dt       secondi trascorsi dall'ultimo frame
     * @param nowNano  timestamp attuale in nanosecondi (System.nanoTime)
     */
    public void update(double dt, long nowNano) {
        spawnEnemies(nowNano);
        updateEnemies(dt);
        checkWaveComplete();
        updateTowers(dt);
        updateBullets(dt);
    }

    private void spawnEnemies(long nowNano) {
        waveManager.update(nowNano, enemies);
    }

    private void updateEnemies(double dt) {
        Iterator<Enemy> it = enemies.iterator();
        while (it.hasNext()) {
            Enemy e = it.next();
            e.update(dt);


            if (e.isReachedEnd()) {
                lives--;
                it.remove();
                if (lives <= 0) gameOver = true;

            } else if (e.isDead()) {
                money += 10 + waveManager.getWave() * 2;
                score += 20 + waveManager.getWave() * 5;
                it.remove();
            }
          }
    }

    private void checkWaveComplete() {
        if (waveManager.isWaveComplete(enemies)) {
            waveManager.startNextWave();
         }
    }


    private void updateTowers(double dt) {
        for (Tower t : towers) {
            t.setCooldown(t.getCooldown()-dt);
            if (t.getCooldown() <= 0) {
                Enemy target = t.findTarget(enemies);
                 if (target != null) {
                    bullets.add(new Bullet(t.centerX(), t.centerY(), target));
                    t.setCooldown(t.getFireRate());
                }
            }
        }
    }


    private void updateBullets(double dt) {
        Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
            Bullet b = it.next();
            b.update(dt);
              // Rimuovi se ha colpito o il bersaglio non è più in gioco
            if (b.isHasHit() || b.getTarget().isDead() || b.getTarget().isReachedEnd()) {
                it.remove();
            }
        }
    }
    /**
     * Tenta di piazzare una torre su (col, row).
     *
     * @return true se il piazzamento è avvenuto, false altrimenti
     *         (cella occupata, percorso, o monete insufficienti)
     */
    public boolean tryPlaceTower(int col, int row) {
        if (!map.canPlace(col, row)) return false;
        if (money < Constants.TOWER_COST) return false;


        towers.add(new Tower(col, row));
        map.placeTower(col, row);
        money -= Constants.TOWER_COST;
        return true;
    }
}
