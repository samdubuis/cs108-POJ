/**
 * Classe concernant l'état du jeu ainsi que toutes ses fonctions
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */
package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;


import ch.epfl.cs108.Sq;
import ch.epfl.xblast.ArgumentChecker;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.Lists;
import ch.epfl.xblast.PlayerID;
import ch.epfl.xblast.SubCell;
import ch.epfl.xblast.server.Player.DirectedPosition;

public final class GameState {

    private static final int DISTANCE_BOMB_BLOQUANTE = 6;

    
    private final int ticks;
    private final Board board;
    private final List<Player> players;
    private final List<Bomb> bombs;
    private final List<Sq<Sq<Cell>>> explosions;
    private final List<Sq<Cell>> blasts;

    private static final List<List<PlayerID>> PERMUTATION_LIST = Lists.permutations(Arrays.asList(PlayerID.values()));

    private static final Random RANDOM = new Random(2016);

    /**
     * Constructeur du GameState
     * @param ticks
     * @param board
     * @param players
     * @param bombs
     * @param explosions
     * @param blasts
     */
    public GameState(
            int ticks, 
            Board board, 
            List<Player> players, 
            List<Bomb> bombs, 
            List<Sq<Sq<Cell>>> explosions, 
            List<Sq<Cell>> blasts){
        ArgumentChecker.requireNonNegative(ticks);
        this.ticks = ticks;
        this.board = Objects.requireNonNull(board);
        if (players.size()!=4) {
            throw new IllegalArgumentException();
        }
        this.players = new ArrayList<>(Objects.requireNonNull(players));
        this.bombs = new ArrayList<>(Objects.requireNonNull(bombs));
        this.explosions = new ArrayList<>(Objects.requireNonNull(explosions));
        this.blasts = new ArrayList<>(Objects.requireNonNull(blasts));

    }

    /**
     * Constructeur secondaire du GameState appelant le premier
     * @param board
     * @param players
     */
    public GameState(Board board, List<Player> players){
        this(0, board, players, new ArrayList<Bomb>(), new ArrayList<Sq<Sq<Cell>>>(), new ArrayList<Sq<Cell>>());
    }

    /**
     * fonction retournant le nombre de ticks
     * @return ticks
     */
    public int ticks(){
        return ticks;
    }

    /**
     * fonction retournant true si le jeu est terminé, false sinon
     * @return true ou false
     */
    public boolean isGameOver(){
        if (ticks()>Ticks.TOTAL_TICKS || this.alivePlayers().size()<=1) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * fonction retournant le temps restant du jeu sous forme de double
     * @return double remainingTime
     */
    public double remainingTime(){
        return ((double)Ticks.TOTAL_TICKS - ticks())/((double)Ticks.TICKS_PER_SECOND);
    }

    /** 
     * fonction retournant le gagnant du jeu s'il y en a un, null sinon
     * @return Player ou null
     */
    public Optional<PlayerID> winner(){
        if (this.alivePlayers().size()==1) {
            return Optional.of(this.alivePlayers().get(0).id());
        }
        else {
            return Optional.empty();
        }
    }

    /**
     * fonction retournant le board du jeu
     * @return board
     */
    public Board board(){
        return board;
    }

    /**
     * fonction retournant la liste des players du jeu
     * @return List de players
     */
    public List<Player> players(){
        return players;
    }

    /**
     * fonction retournant une liste des players vivants, càd avec plus que 0 vies
     * @return List de players
     */
    public List<Player> alivePlayers(){
        List<Player> temp = new ArrayList<>();
        for (int i = 0; i < players().size(); i++) {
            if (players().get(i).lives()>0) {
                temp.add(players().get(i));
            }
        }
        return temp;
    }


    /**
     *fonction retournant une Map contenant les Cells du gamestate  
     * @return Map<Cell, Bomb> 
     */
    public Map<Cell, Bomb> bombedCells(){
        Map<Cell, Bomb> temp = new HashMap<>();
        for (Bomb bomb : bombs) {
            temp.put(bomb.position(), bomb);
        }
        return temp;
    }


    /**
     * fonction retournant un Set contenant les blastedCells du gamestate
     * @return Set<Cell>
     */ 
    public Set<Cell> blastedCells(){
        Set<Cell> temp = new HashSet<>();
        for (int i = 0; i < blasts.size(); i++) {
            temp.add(blasts.get(i).head());
        }
        return temp;
    }

    /**
     * Fonction next, appelant toutes les méthodes statiques privées et permettant la création du futur du jeu
     * @param speedChangeEvents Map<PlayerID, Optional<Direction>> map des changements de direction des joueurs
     * @param bombDropEvents Set<PlayerID> set des id des joueurs voulant déposer une bombe
     * @return GameState futur
     */
    public GameState next(
            Map<PlayerID, Optional<Direction>> speedChangeEvents, 
            Set<PlayerID> bombDropEvents){

        List<PlayerID> listConflit = PERMUTATION_LIST.get(ticks()%PERMUTATION_LIST.size());

        List<Player> players0 = new ArrayList<>();
        for (PlayerID playerID : listConflit) {
            for (Player player : players) {
                if (playerID.equals(player.id())) {
                    players0.add(player);
                }  
            }

        }

        List<Sq<Cell>> blasts1 = nextBlasts(this.blasts, board(), this.explosions);

        Set<Cell> consumedBonuses = new HashSet<>();
        Map<PlayerID, Bonus> playerBonuses = new HashMap<>();
        for (int i = 0; i < players0.size(); i++) {
            if (!consumedBonuses.contains(players0.get(i).position().containingCell())) {
                if (board().blockAt(players0.get(i).position().containingCell()).hashCode()==Block.BONUS_BOMB.hashCode()) {
                    consumedBonuses.add(players0.get(i).position().containingCell());
                    playerBonuses.put(players0.get(i).id(), Bonus.INC_BOMB);
                }
                if (board().blockAt(players0.get(i).position().containingCell()).hashCode()==Block.BONUS_RANGE.hashCode()) {
                    consumedBonuses.add(players0.get(i).position().containingCell());
                    playerBonuses.put(players0.get(i).id(), Bonus.INC_RANGE);
                }
            }

        }
        Set<Cell> blastedCells1 = new HashSet<>();
        for (Sq<Cell> sq : blasts1) {
            blastedCells1.add(sq.head());
        }

        Board board1 = nextBoard(board(), consumedBonuses, blastedCells1);

        for (int i = 0; i < bombs.size(); i++) {
            if (blastedCells1.contains(bombs.get(i).position())){
                explosions.add(bombs.get(i).explosionArmTowards(Direction.E));
                explosions.add(bombs.get(i).explosionArmTowards(Direction.N));
                explosions.add(bombs.get(i).explosionArmTowards(Direction.S));
                explosions.add(bombs.get(i).explosionArmTowards(Direction.W));
                bombs.remove(bombs.get(i));
            }
        } 

        List<Sq<Sq<Cell>>> explosions1 = nextExplosions(explosions);    

        Set<PlayerID> setBombDropEvents = new HashSet<>();
        for (Player player : players0) {
            if (bombDropEvents.contains(player.id())) {
                int compteur = 0;
                for (Bomb bomb : bombs) {
                    if (bomb.ownerId().equals(player.id())) {
                        compteur++;
                    }
                }
                if (compteur<player.maxBombs() && player.isAlive()) {
                    setBombDropEvents.add(player.id());
                }
            }
        }


        List<Bomb> tempBomb = newlyDroppedBombs(players0, setBombDropEvents, this.bombs);
        List<Bomb> bombs1 = new ArrayList<>();
        for (Bomb bomb : tempBomb) {
            if (!bomb.fuseLengths().tail().isEmpty()) {
                bombs1.add(new Bomb(bomb.ownerId(), bomb.position(), bomb.fuseLengths().tail(), bomb.range()));
            }
            else {
                explosions1.addAll(bomb.explosion());
            }
        }


        Set<Cell> Cells1 = new HashSet<>();
        for (Bomb bomb : bombs1) {
            Cells1.add(bomb.position());
        }
        List<Player> players1 = nextPlayers(players(), playerBonuses, Cells1, board1, blastedCells1, speedChangeEvents);
        return new GameState(ticks+1, board1, players1, bombs1, explosions1, blasts1);
    }

    /**
     * Fonction retournant les futur blasts selon les paramètres
     * @param blasts0 List<Sq<Cell>>
     * @param board0 Board
     * @param explosions0 List<Sq<Sq<Cell>>> 
     * @return Futur Blasts
     */
    private static List<Sq<Cell>> nextBlasts(
            List<Sq<Cell>> blasts0, 
            Board board0, 
            List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Cell>> temp = new ArrayList<>();

        for (int i = 0; i < blasts0.size(); i++) {
            if (board0.blockAt(blasts0.get(i).head()).isFree()) {
                if (!blasts0.get(i).tail().isEmpty()) {
                    temp.add(blasts0.get(i).tail());
                }
            }
        }

        for (int i = 0; i < explosions0.size(); i++) {
            if(!explosions0.get(i).isEmpty()){
                temp.add(explosions0.get(i).head());
            }
        }

        return temp;
    }

    /**
     * Fonction retournant le board futur
     * @param board0 Board
     * @param consumedBonuses Set<Cell>
     * @param blastedCells1 Set<Cell>
     * @return
     */
    private static Board nextBoard(Board board0, Set<Cell> consumedBonuses, Set<Cell> blastedCells1){

        List<Sq<Block>> tempboard = new ArrayList<>();
        for (int i = 0; i < Cell.ROW_MAJOR_ORDER.size(); i++) {
            tempboard.add(board0.blocksAt(Cell.ROW_MAJOR_ORDER.get(i)).tail());
        }

        Sq<Block> freeBlock = Sq.constant(Block.FREE);

        for (Cell cell : consumedBonuses) {
            tempboard.set(cell.rowMajorIndex(), freeBlock);
        }

        for (Cell cell : blastedCells1) {
            if (tempboard.get(cell.rowMajorIndex()).head().hashCode() == Block.DESTRUCTIBLE_WALL.hashCode()) {
                Sq<Block> tempBlock = Sq.repeat(Ticks.WALL_CRUMBLING_TICKS, Block.CRUMBLING_WALL);
                int bonus = RANDOM.nextInt(3);
                if (bonus == 0) {
                    tempBlock = tempBlock.concat(Sq.constant(Block.BONUS_BOMB));
                }
                else if (bonus == 1) {
                    tempBlock = tempBlock.concat(Sq.constant(Block.BONUS_RANGE));
                }
                else if (bonus == 2) {
                    tempBlock = tempBlock.concat(Sq.constant(Block.FREE));
                }
                tempboard.set(cell.rowMajorIndex(), tempBlock);
            }
            if (tempboard.get(cell.rowMajorIndex()).head().hashCode() == Block.BONUS_BOMB.hashCode()) {
                Sq<Block> testDisappearingBonus = tempboard.get(cell.rowMajorIndex());
                for (int i = 0; i < Ticks.BONUS_DISAPPEARING_TICKS; i++){
                    testDisappearingBonus = testDisappearingBonus.tail();
                }
                if (!testDisappearingBonus.head().isFree()){
                    Sq<Block> tempBlock = Sq.repeat(Ticks.BONUS_DISAPPEARING_TICKS, Block.BONUS_BOMB);
                    tempBlock = tempBlock.concat(freeBlock);
                    tempboard.set(cell.rowMajorIndex(), tempBlock);
                }
            }
            if (tempboard.get(cell.rowMajorIndex()).head().hashCode() == Block.BONUS_RANGE.hashCode()) {
                Sq<Block> testDisappearingBonus = tempboard.get(cell.rowMajorIndex());
                for (int i = 0; i < Ticks.BONUS_DISAPPEARING_TICKS; i++){
                    testDisappearingBonus = testDisappearingBonus.tail();
                }
                if (!testDisappearingBonus.head().isFree()){
                    Sq<Block> tempBlock = Sq.repeat(Ticks.BONUS_DISAPPEARING_TICKS, Block.BONUS_RANGE);
                    tempBlock = tempBlock.concat(freeBlock);
                    tempboard.set(cell.rowMajorIndex(), tempBlock);
                }
            }
        }

        return new Board(tempboard);
    }

    /**
     * Fonction retournant les futurs du player mouvements et vies
     * @param players0 List<Player>
     * @param playerBonuses Map<PlayerID, Bonus>
     * @param Cells1 Set<Cell>
     * @param board1 Board
     * @param blastedCells1 Set<Cell>
     * @param speedChangeEvents Map<PlayerID, Optional<Direction>>
     * @return Futurs joueurs
     */
    private static List<Player> nextPlayers(
            List<Player> players0, 
            Map<PlayerID, Bonus> playerBonuses, 
            Set<Cell> Cells1, 
            Board board1, 
            Set<Cell> blastedCells1, 
            Map<PlayerID, Optional<Direction>> speedChangeEvents){

        List<Player> tempPlayers = new ArrayList<>();

        for (Player player : players0) {
            Optional<Direction> a = speedChangeEvents.get(player.id());
            Sq<DirectedPosition> temp = player.directedPositions();
            Sq<DirectedPosition> stop = Player.DirectedPosition.stopped(temp.head()).limit(1);

            if (player.lifeState().canMove()) {
                if (a != null && a.isPresent()) {
                    Direction dir = a.get();
                    if (player.position().isCentral() && !board1.blockAt(player.position().containingCell().neighbor(dir)).canHostPlayer()) {
                        temp = stop.concat(temp);
                    }
                    else if (!Cells1.isEmpty()) {
                        if (Cells1.contains(player.position().containingCell())) {
                            if (player.position().distanceToCentral()<DISTANCE_BOMB_BLOQUANTE) {
                                Sq<DirectedPosition> sqUntilNextCentral = Player.DirectedPosition.moving(player.directedPositions().head()).takeWhile(u -> !u.position().isCentral());
                                DirectedPosition nextCentral = player.directedPositions().findFirst(u -> u.position().isCentral());
                                temp = sqUntilNextCentral;
                                Sq<DirectedPosition> sqFromNextCentral = Player.DirectedPosition.moving(new DirectedPosition(nextCentral.position(), dir));
                                temp = temp.concat(sqFromNextCentral);
                            }
                            else if (player.position().distanceToCentral()>=DISTANCE_BOMB_BLOQUANTE) {
                                if (player.position().neighbor(dir).distanceToCentral()>player.position().distanceToCentral()
                                    && dir.isParallelTo(player.direction())) {
                                    temp = Player.DirectedPosition.moving(new DirectedPosition(player.position(), dir));
                                }
                                else if (player.position().neighbor(dir).distanceToCentral()<DISTANCE_BOMB_BLOQUANTE 
                                        && dir.isParallelTo(player.direction())) {
                                    temp = stop.concat(temp);
                                }
                                else {
                                    temp = stop.concat(temp);
                                }
                                
                            }
                        }
                        else {
                            Sq<DirectedPosition> sqUntilNextCentral = Player.DirectedPosition.moving(player.directedPositions().head()).takeWhile(u -> !u.position().isCentral());
                            DirectedPosition nextCentral = player.directedPositions().findFirst(u -> u.position().isCentral());
                            temp = sqUntilNextCentral;
                            Sq<DirectedPosition> sqFromNextCentral = Player.DirectedPosition.moving(new DirectedPosition(nextCentral.position(), dir));
                            temp = temp.concat(sqFromNextCentral);
                        }
                    }
                    
                    else  if (dir.isParallelTo(player.direction())) {
                        temp = Player.DirectedPosition.moving(new DirectedPosition(player.position(), dir));
                    }
                    else {
                        Sq<DirectedPosition> sqUntilNextCentral = Player.DirectedPosition.moving(player.directedPositions().head()).takeWhile(u -> !u.position().isCentral());
                        DirectedPosition nextCentral = player.directedPositions().findFirst(u -> u.position().isCentral());
                        temp = sqUntilNextCentral;
                        Sq<DirectedPosition> sqFromNextCentral = Player.DirectedPosition.moving(new DirectedPosition(nextCentral.position(), dir));
                        temp = temp.concat(sqFromNextCentral);
                    }
                }
                else if (a!=null && !a.isPresent()) {
                    Sq<DirectedPosition> sqUntilNextCentral = Player.DirectedPosition.moving(player.directedPositions().head()).takeWhile(u -> !u.position().isCentral());
                    DirectedPosition nextCentral = player.directedPositions().findFirst(u -> u.position().isCentral());
                    temp = sqUntilNextCentral.concat(Player.DirectedPosition.stopped(nextCentral));
                }
                else {
                    if (player.position().isCentral() && !board1.blockAt(player.position().containingCell().neighbor(player.direction())).canHostPlayer()) {
                        temp = stop.concat(temp);
                    }
                    else if (!Cells1.isEmpty()) {
                        for (Cell cell : Cells1) {
                            if (player.position().distanceToCentral()>DISTANCE_BOMB_BLOQUANTE && player.position().containingCell().equals(cell)) {
                                if (player.position().neighbor(player.direction()).distanceToCentral()<player.position().distanceToCentral()
                                        && player.position().neighbor(player.direction()).distanceToCentral()<=6) {
                                    temp = stop.concat(temp);
                                }
                            }
                            else if (!board1.blockAt(player.position().containingCell().neighbor(player.direction())).canHostPlayer()
                                    && player.position().containingCell().equals(cell)
                                    && player.position().distanceToCentral()<=DISTANCE_BOMB_BLOQUANTE) {
                                temp = stop.concat(temp);
                            }
                        }
                    }
                }
            }
            else {
                temp = stop.concat(temp);
            }

            tempPlayers.add(new Player(player.id(), player.lifeStates(), temp.tail(), player.maxBombs(), player.bombRange()));

        }

        List<Player> tempPlayers2 = new ArrayList<>();
        int compteur = 0;
        for (Player player : tempPlayers) {
            for (Cell cell : blastedCells1) {
                if (cell.equals(player.position().containingCell()) && player.lifeState().state().hashCode()!=Player.LifeState.State.INVULNERABLE.hashCode()) {
                    tempPlayers2.add(new Player(player.id(), player.statesForNextLife(), player.directedPositions(), player.maxBombs(), player.bombRange()));
                    compteur++;
                }
            }
            if (compteur == 0) {
                tempPlayers2.add(new Player(player.id(), player.lifeStates(), player.directedPositions(), player.maxBombs(), player.bombRange()));
            }
            compteur = 0;
        }

        List<Player> players2 = new ArrayList<>();
        for (Player player : tempPlayers2) {
            players2.add(new Player(player.id(), player.lifeStates().tail(), player.directedPositions(), player.maxBombs(), player.bombRange()));
        }

        List<Player> players1 = new ArrayList<>();
        for (Player player : players2) {
            if (playerBonuses.containsKey(player.id())) {
                players1.add(playerBonuses.get(player.id()).applyTo(player));
            }
            else {
                players1.add(player);
            }
        }

        return players1;
    }

    /**
     * Fonction retournant les futurs explosions
     * @param explosions0 List<Sq<Sq<Cell>>>
     * @return
     */
    private static List<Sq<Sq<Cell>>> nextExplosions(List<Sq<Sq<Cell>>> explosions0){
        List<Sq<Sq<Cell>>> temp = new ArrayList<>();

        for (int i = 0; i < explosions0.size(); i++) {
            if (!explosions0.get(i).isEmpty()){
                temp.add(explosions0.get(i).tail());
            }
        }
        return temp;
    }

    /**
     * Fonction retournant les dépots de bombes
     * @param players0 List<Player>
     * @param bombDropEvents Set<PlayerID>
     * @param bombs0 List<Bomb>
     * @return Futurs bombes déposées
     */
    private static List<Bomb> newlyDroppedBombs(List<Player> players0, Set<PlayerID> bombDropEvents, List<Bomb> bombs0){      

        List<Bomb> tempBomb = new ArrayList<>(bombs0);

        for (Player player : players0) {
            if (bombDropEvents.contains(player.id()))
                if(tempBomb.isEmpty()) {
                    tempBomb.add(player.newBomb());
                }
                else {
                    boolean test = true;
                    for (int i = 0; i < tempBomb.size(); i++) {
                        if (player.position().containingCell().equals(tempBomb.get(i).position())) {
                            test = false;
                        }
                    }
                    if (test) {
                        tempBomb.add(player.newBomb());
                    }
                }
        }


        return tempBomb;

    }



}
