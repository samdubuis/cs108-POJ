/**
 * classe permettant de créer une niveau avec un gamestate et boardpainter 
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.PlayerID;

public final class Level {

    private static final int DEFAULT_NUMBER_LIFE = 3;
    private static final int DEFAULT_NUMBER_BOMB = 2;
    private static final int DEFAULT_BOMB_RANGE = 3;
    
    
    private GameState gs;
    private BoardPainter boardpainter;
    
    /**
     * table de correspondances par defaut du boardpainter
     */
    private static final Map<Block, BlockImage> DEFAULT_MAP_BOARDPAINTER;
    static{
        DEFAULT_MAP_BOARDPAINTER = new HashMap<>();
        DEFAULT_MAP_BOARDPAINTER.put(Block.INDESTRUCTIBLE_WALL, BlockImage.DARK_BLOCK);
        DEFAULT_MAP_BOARDPAINTER.put(Block.DESTRUCTIBLE_WALL, BlockImage.EXTRA);
        DEFAULT_MAP_BOARDPAINTER.put(Block.CRUMBLING_WALL, BlockImage.EXTRA_O);
        DEFAULT_MAP_BOARDPAINTER.put(Block.FREE, BlockImage.IRON_FLOOR);
        DEFAULT_MAP_BOARDPAINTER.put(Block.BONUS_BOMB, BlockImage.BONUS_BOMB);
        DEFAULT_MAP_BOARDPAINTER.put(Block.BONUS_RANGE, BlockImage.BONUS_RANGE);
    }
    /**
     * boardpainter par defaut
     */
    private static final BoardPainter DEFAULT_BOARDPAINTER = new BoardPainter(DEFAULT_MAP_BOARDPAINTER, BlockImage.IRON_FLOOR_S);
    
    /**
     * liste de joueur dans leur position et etat initials
     */
    private static final List<Player> players;
    static{
        players = new ArrayList<>();
        players.add(new Player(PlayerID.PLAYER_1, DEFAULT_NUMBER_LIFE, new Cell(1, 1), DEFAULT_NUMBER_BOMB, DEFAULT_BOMB_RANGE));
        players.add(new Player(PlayerID.PLAYER_2, DEFAULT_NUMBER_LIFE, new Cell(13, 1), DEFAULT_NUMBER_BOMB, DEFAULT_BOMB_RANGE));
        players.add(new Player(PlayerID.PLAYER_3, DEFAULT_NUMBER_LIFE, new Cell(13, 11), DEFAULT_NUMBER_BOMB, DEFAULT_BOMB_RANGE));
        players.add(new Player(PlayerID.PLAYER_4, DEFAULT_NUMBER_LIFE, new Cell(1, 11), DEFAULT_NUMBER_BOMB, DEFAULT_BOMB_RANGE));
    }

    
    private static Block __ = Block.FREE;
    private static Block XX = Block.INDESTRUCTIBLE_WALL;
    private static Block xx = Block.DESTRUCTIBLE_WALL;
    /**
     * board par defaut
     */
    private static final Board board;
    static{
        board = Board.ofQuadrantNWBlocksWalled(
            Arrays.asList(
                    Arrays.asList(__, __, __, __, __, xx, __),
                    Arrays.asList(__, XX, xx, XX, xx, XX, xx),
                    Arrays.asList(__, xx, __, __, __, xx, __),
                    Arrays.asList(xx, XX, __, XX, XX, XX, XX),
                    Arrays.asList(__, xx, __, xx, __, __, __),
                    Arrays.asList(xx, XX, xx, XX, xx, XX, __)));
    }
    /**
     * gamestate par defaut
     */
    private static final GameState DEFAULT_GAMESTATE = new GameState(board, players);
    
    /**
     * level par defaut
     */
    public static final Level DEFAULT_LEVEL = new Level(DEFAULT_BOARDPAINTER, DEFAULT_GAMESTATE);
    
    /**
     * constructeur de la classe level
     * @param boardpainter
     * @param gs
     */
    public Level(BoardPainter boardpainter, GameState gs){
        this.boardpainter=boardpainter;
        this.gs=gs;
    }
    
    /**
     * methode retournant le gamestate du level
     * @return
     */
    public GameState getGameState() {
        return gs;
    }
    
    /** methode retournant le boardpainter du level
     * 
     * @return
     */
    public BoardPainter getBoardpainter() {
        return boardpainter;
    }
    
    
    
    
    
    
    
}
