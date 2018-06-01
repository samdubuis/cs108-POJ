/**
 * Interface concernant les différents ticks du jeu
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast.server;

import static ch.epfl.xblast.server.Ticks.TICKS_PER_SECOND;
import ch.epfl.xblast.Time;

/**
 * interface pour ticks
 */
public interface Ticks {

    public static final int PLAYER_DYING_TICKS = 8;
    public static final int PLAYER_INVULNERABLE_TICKS = 64;
    public static final int BOMB_FUSE_TICKS = 100;
    public static final int EXPLOSION_TICKS = 30;
    public static final int WALL_CRUMBLING_TICKS = EXPLOSION_TICKS;
    public static final int BONUS_DISAPPEARING_TICKS = EXPLOSION_TICKS;
    public static final int TICKS_PER_SECOND = 20;
    public static final int TICK_NANOSECOND_DURATION = Time.NS_PER_S / TICKS_PER_SECOND;
    public static final int TOTAL_TICKS = 2 * Time.S_PER_MIN * TICKS_PER_SECOND;
    
}
