/**
 * Classe concernant le player et ses attributs
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.LifeState.State;

import java.util.Objects;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.server.Bomb;

public final class Player {

    private final PlayerID id;
    private final int nbrBombe;
    private final int portee;
    private final Sq<LifeState> lifeStates;
    private final Sq<DirectedPosition> posdir;


    /**
     * Permier constructeur du player
     * @param id PlayerID
     * @param lifeStates Sq<LifeState>
     * @param directedPos Sq<DirectedPosition>
     * @param maxBombs int
     * @param bombRange int
     */
    public Player(PlayerID id, Sq<LifeState> lifeStates, Sq<DirectedPosition> directedPos, int maxBombs, int bombRange){
        this.id = Objects.requireNonNull(id);
        this.lifeStates = Objects.requireNonNull(lifeStates);
        posdir = Objects.requireNonNull(directedPos);

        ArgumentChecker.requireNonNegative(maxBombs);
        ArgumentChecker.requireNonNegative(bombRange);
        nbrBombe = maxBombs;
        portee = bombRange;

    }

    /**
     * Second constructeur du player
     * @param id PlayerID
     * @param lives int
     * @param position Cell
     * @param maxBombs int
     * @param bombRange int
     */
    public Player(PlayerID id, int lives, Cell position, int maxBombs, int bombRange){
        this(id, lives == 0 ? Sq.constant(new LifeState(0, State.DEAD)) : Sq.repeat(Ticks.PLAYER_INVULNERABLE_TICKS, 
                new LifeState(lives, State.INVULNERABLE)).concat(Sq.constant(new LifeState(lives, State.VULNERABLE))),
                Sq.constant(new DirectedPosition(SubCell.centralSubCellOf(Objects.requireNonNull(position)), Direction.S)),
                maxBombs, 
                bombRange);
    } 

    /**
     * Fonction retournant l'id du player
     * @return id
     */
    public PlayerID id(){
        return id;
    }

    /**
     * Fonction retournant la séquence de lifestate du player
     * @return séquence de lifestate
     */
    public Sq<LifeState> lifeStates(){
        return lifeStates;
    }

    /**
     * fonction retournant le premier lifestate du player
     * @return Lifestate
     */
    public LifeState lifeState(){
        return lifeStates().head();
    }

    /**
     * fonction retournant l état de la prochaine vie sous forme de séquence
     * @return séquence de lifestate
     */
    public Sq<LifeState> statesForNextLife(){
        Sq<LifeState> stateDying = Sq.repeat(Ticks.PLAYER_DYING_TICKS , new LifeState(this.lives(), State.DYING));        
        Sq<LifeState> stateDeadOrInvulnerable;
        Sq<LifeState> stateVulnerable;

        if (this.lives()-1 <= 0) {
            stateDeadOrInvulnerable = Sq.constant(new LifeState(0, State.DEAD));
            return stateDying.concat(stateDeadOrInvulnerable);
        }
        else {
            stateDeadOrInvulnerable = Sq.repeat(Ticks.PLAYER_INVULNERABLE_TICKS, new LifeState(this.lives()-1, State.INVULNERABLE));
            stateVulnerable = Sq.constant(new LifeState(this.lives()-1, State.VULNERABLE));
            return stateDying.concat(stateDeadOrInvulnerable.concat(stateVulnerable));
        }
    }

    /**
     * fonction retournant le nombre de vies du player
     * @return int lives
     */
    public int lives(){
        return lifeState().lives();
    }

    /**
     * fonction retournant true si le player a des vies, false sinon
     * @return true ou false
     */
    public boolean isAlive(){
        if (lives() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * fonction retournant les directedPosition du player
     * @return séquence de directedPosition
     */
    public Sq<DirectedPosition> directedPositions(){
        return posdir;
    }

    /**
     * fonction retournant la position du player
     * @return SubCell 
     */
    public SubCell position(){
        return directedPositions().head().position();
    }

    /**
     * fonction retournant la direction du player
     * @return Direction
     */
    public Direction direction(){
        return directedPositions().head().direction();
    }

    /**
     * fonction retournant le nombre max de bombes d'un player
     * @return int 
     */
    public int maxBombs(){
        return nbrBombe;
    }

    /**
     * fonction definissant un nouveau nombre maximum de bombes pour un player
     * @param int
     * @return Player
     */
    public Player withMaxBombs(int newMaxBombs){
        return new Player(this.id(), this.lifeStates(), this.directedPositions(), newMaxBombs, this.bombRange());
    }

    /**
     * fonction retournant le range d'un player
     * @return int
     */
    public int bombRange(){
        return portee;
    }

    /**
     * fonction définissant un nouveau range pour un player
     * @param int
     * @return Player
     */
    public Player withBombRange(int newBombRange){
        return new Player(this.id(), this.lifeStates(), this.directedPositions(), this.maxBombs(), newBombRange);
    }

    /**
     * fonction créant une nouvelle bombe à l'aide des paramètres du joueur
     * @return Bomb
     */
    public Bomb newBomb(){
        return new Bomb(this.id(), this.position().containingCell(), Ticks.BOMB_FUSE_TICKS, this.bombRange());
    }


    /**
     * Classe imbriquée LifeState du Player
     * 
     * @author Samuel Dubuis (259157)
     * @author Yann Gabbud  (260036)
     */
    public static final class LifeState{

        private final int vies;
        private final State etat;
        
        /**
         * enumeration des etat
         */
        public enum State {
            INVULNERABLE, VULNERABLE, DYING, DEAD;
        }

        /**
         * Constructeur du LifeState
         * @param lives int
         * @param state State
         */
        public LifeState(int lives, State state){
            ArgumentChecker.requireNonNegative(lives);
            this.etat = Objects.requireNonNull(state); 
            this.vies = lives;
        }

        /**
         * fonction retournant le nombres de vies
         * @return int
         */
        public int lives(){
            return vies;
        }

        /**
         * fonction retournant l'état actuel du player
         * @return state
         */
        public State state(){
            return etat;
        }

        /**
         * fonction retournant true si le player est capable de se déplacer, false sinon
         * @return boolean
         */
        public boolean canMove(){
            if (etat == State.INVULNERABLE || etat == State.VULNERABLE){
                return true;
            }
            else return false;
        }
    }


    /**
     * Classe imbriquée DirectedPosition du player
     * @author Samuel Dubuis (259157)
     * @author Yann Gabbud  (260036)
     */
    public static final class DirectedPosition{

        private final SubCell position;
        private final Direction direction;

        /**
         * Constructeur de DirectedPosition
         * @param position SubCell
         * @param direction Direction
         */
        public DirectedPosition(SubCell position, Direction direction){
            this.position = Objects.requireNonNull(position);
            this.direction = Objects.requireNonNull(direction); 
        }

        /**
         * fonction retournant la position du player selon la directerPosition
         * @return position
         */
        public SubCell position(){
            return position;
        }

        /**
         * fonction retournant la direction du player selon le DirectedPosition
         * @return direction
         */
        public Direction direction(){
            return direction;
        }

        /**
         * fonction définissant une nouvelle position pour le player
         * @param SubCell
         * @return DirecterPosition 
         */
        public DirectedPosition withPosition(SubCell newPosition){
            return new DirectedPosition(newPosition, this.direction());
        }

        /**
         * fonction définissant une nouvelle direction pour le player
         * @param Direction
         * @return DirectedPosition
         */
        public DirectedPosition withDirection(Direction newDirection){
            return new DirectedPosition(this.position(), newDirection);
        }

        /**
         * fonction retournant une séquence de directedPosition tel que le player soit à l'arrêt
         * @param DirectedPosition
         * @return séquence de directedPosition constante
         */
        public static Sq<DirectedPosition> stopped(DirectedPosition p){
            return Sq.constant(p);
        }

        /**
         * fonction retournant la séquence de directedPosition d'un player se déplacant, séquence crée selon la direction
         * @param p
         * @return séquence de directedPosition
         */
        public static Sq<DirectedPosition> moving(DirectedPosition p){         
            Sq<DirectedPosition> directedPosition = Sq.iterate(new DirectedPosition(p.position(), p.direction()), d -> d.withPosition(d.position().neighbor(p.direction())));
            return directedPosition;
        }

    }

}
