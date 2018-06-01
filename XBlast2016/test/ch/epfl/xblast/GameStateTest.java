package ch.epfl.xblast;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Player;
import ch.epfl.xblast.server.debug.GameStatePrinter;
import ch.epfl.xblast.server.debug.RandomEventGenerator;

public class GameStateTest {

    public static void main(String[] args) {

        Player player1 = new Player(PlayerID.PLAYER_1, 2, new Cell(1, 1), 2, 3);
        Player player2 = new Player(PlayerID.PLAYER_2, 2, new Cell(13, 1), 2, 3);
        Player player3 = new Player(PlayerID.PLAYER_3, 2, new Cell(1, 11), 2, 3);
        Player player4 = new Player(PlayerID.PLAYER_4, 2, new Cell(13, 11), 2, 3);

        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        players.add(player3);
        players.add(player4);

        
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        Board board = Board.ofQuadrantNWBlocksWalled(
                Arrays.asList(
                        Arrays.asList(__, __, __, __, __, xx, __),
                        Arrays.asList(__, XX, xx, XX, xx, XX, xx),
                        Arrays.asList(__, xx, __, __, __, xx, __),
                        Arrays.asList(xx, XX, __, XX, XX, XX, XX),
                        Arrays.asList(__, xx, __, xx, __, __, __),
                        Arrays.asList(xx, XX, xx, XX, xx, XX, __)));
        
        
        GameState gs = new GameState(board, players);

        int i = 0;

        do {
            RandomEventGenerator bla = new RandomEventGenerator(2016, 30, 100);
            gs = gs.next(bla.randomSpeedChangeEvents(), bla.randomBombDropEvents());
            System.out.println(gs.ticks());
            GameStatePrinter.printGameState(gs);
            long a = 50;
            try {
                Thread.sleep(a);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            i++;
        } while (i<20);
        
    }

}
