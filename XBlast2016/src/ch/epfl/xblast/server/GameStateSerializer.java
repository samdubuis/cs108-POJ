/**
 * classe permettant de sérialiser le jeu afin de l'envoyer aux clients
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;
import ch.epfl.xblast.RunLengthEncoder;

public final class GameStateSerializer {
    
    /**
     * constructeur privé de la classe car non instanciable
     */
    private GameStateSerializer(){ }

    /**
     * methode servant à sérialiser le jeu
     * @param boardpainter
     * @param gs
     * @return
     */
    public static List<Byte> serialize(BoardPainter boardpainter, GameState gs){
        List<Byte> result = new ArrayList<>();

        List<Byte> board = new ArrayList<>();
        for (int i = 0; i < Cell.SPIRAL_ORDER.size(); i++) {
            board.add(boardpainter.byteOfCell(gs.board(), Cell.SPIRAL_ORDER.get(i)));
        }
        List<Byte> boardEncoded = RunLengthEncoder.encode(board);
        result.add((byte)boardEncoded.size());
        result.addAll(boardEncoded);

        List<Byte> explosion = new ArrayList<>();
        for (int i = 0; i < Cell.ROW_MAJOR_ORDER.size(); i++) {
            if (gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i))) {
                if (!gs.board().blockAt(Cell.ROW_MAJOR_ORDER.get(i)).isFree()) {
                    explosion.add(ExplosionPainter.BYTE_FOR_EMPTY);
                }
                else{
                    if(gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.N)) &&
                            gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.E)) &&
                            gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.S)) &&
                            gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.W))){
                        explosion.add(ExplosionPainter.byteOfBlast(true, true, true, true));
                    }
                    else {
                        boolean n = gs.board().blockAt(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.N)).isFree() && gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.N));
                        boolean e = gs.board().blockAt(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.E)).isFree() && gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.E));
                        boolean s = gs.board().blockAt(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.S)).isFree() && gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.S));
                        boolean w = gs.board().blockAt(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.W)).isFree() && gs.blastedCells().contains(Cell.ROW_MAJOR_ORDER.get(i).neighbor(Direction.W));
                        explosion.add(ExplosionPainter.byteOfBlast(n, e, s, w));
                    }                        
                }
            }
            else if (gs.bombedCells().containsKey(Cell.ROW_MAJOR_ORDER.get(i))) {
                Bomb b = gs.bombedCells().get(Cell.ROW_MAJOR_ORDER.get(i));
                explosion.add(ExplosionPainter.byteOfBomb(b));
            }
            else{
                explosion.add(ExplosionPainter.BYTE_FOR_EMPTY);
            }
        }
        List<Byte> explosionEncoded = RunLengthEncoder.encode(explosion);
        result.add((byte)explosionEncoded.size());
        result.addAll(explosionEncoded);


        for (Player p : gs.players()) {
            result.add((byte) p.lives());
            result.add((byte) p.position().x());
            result.add((byte) p.position().y());
            result.add(PlayerPainter.byteOfPlayer(gs.ticks(), p));
        }


        result.add((byte)(Math.ceil(((Ticks.TOTAL_TICKS/Ticks.TICKS_PER_SECOND)-(gs.ticks()/Ticks.TICKS_PER_SECOND))/2)));

        return result;
    }



}
