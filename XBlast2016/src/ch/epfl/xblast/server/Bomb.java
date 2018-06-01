/**
 * Classe concernant les bombes du jeu et leurs fonctions
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;

public final class Bomb {

    private final PlayerID ownerID;
    private final Cell position;
    private final Sq<Integer> fuseLength;
    private final int range;

    /**
     * Constructeur d'une bombe
     * @param ownerID
     * @param position
     * @param fuseLengths
     * @param range
     */
    public Bomb(PlayerID ownerID, Cell position, Sq<Integer> fuseLengths, int range){
        this.ownerID = Objects.requireNonNull(ownerID);
        this.position = Objects.requireNonNull(position);
        if (fuseLengths.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.fuseLength = Objects.requireNonNull(fuseLengths);
        this.range = ArgumentChecker.requireNonNegative(range);
    }

    /**
     * Constructeur secondaire d'une bombe appelant le constructeur principal
     * @param ownerId
     * @param position
     * @param fuseLength
     * @param range
     */
    public Bomb(PlayerID ownerId, Cell position, int fuseLength, int range){
        this(ownerId, position, Sq.iterate(fuseLength, u -> u-1).limit(fuseLength), range);
    }

    /**
     * fonction retournant le PlayerID de la bombe
     * @return PlayerID
     */
    public PlayerID ownerId(){
        return ownerID;
    }

    /**
     * fonction retournant la position de la bombe
     * @return Position
     */
    public Cell position(){
        return position;
    }

    /**
     * fonction retournant la longueur de la mèche sous forme de séquence
     * @return Séquence de integer
     */
    public Sq<Integer> fuseLengths(){
        return fuseLength;
    }

    /**
     * fonction retournant la longueur de la mèche sous forme de int
     * @return int
     */
    public int fuseLength(){
        return fuseLengths().head();
    }

    /**
     * fonction retournant le range de la bombe
     * @return int
     */
    public int range(){
        return range;
    }

    /**
     * fonction retournant les quatres bras de l'explosion d'une bombe
     * @return liste des bras de l'explosion
     */
    public List<Sq<Sq<Cell>>> explosion(){
        List<Sq<Sq<Cell>>> explosion = new ArrayList<>();

        explosion.add(explosionArmTowards(Direction.E));
        explosion.add(explosionArmTowards(Direction.S));
        explosion.add(explosionArmTowards(Direction.W));
        explosion.add(explosionArmTowards(Direction.N));

        return explosion;
    }

    /**
     * fonction créant le bras d'une explosion dans la direction donnée en paramètre
     * @param Direction
     * @return Séquence de séquence de cell, un bras de l'explosion
     */
    public Sq<Sq<Cell>> explosionArmTowards(Direction dir){
        Sq<Cell> tempSq = Sq.iterate(this.position(), tempC -> tempC.neighbor(dir));
        tempSq = tempSq.limit(this.range());
        Sq<Sq<Cell>> sqArmTowards = Sq.repeat(Ticks.EXPLOSION_TICKS, tempSq);
        return sqArmTowards;
    }



}
