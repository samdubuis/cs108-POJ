/**
 * classe permettant de faire le lien entre un block et son image
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */

package ch.epfl.xblast.server;

import java.util.Map;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Direction;

public final class BoardPainter{

    private final Map<Block, BlockImage> palette;
    private final BlockImage shadow;
    
    /**
     * constructeur de la classe
     * @param map
     * @param shadow
     */
    public BoardPainter(Map<Block, BlockImage> map, BlockImage shadow){
        palette = map;
        this.shadow = shadow;
    }

    /**
     * methode permettant de retourner l'image ombrée ou non en fonction du block voisin de gauche
     * @param board
     * @param cell
     * @return
     */
    public byte byteOfCell(Board board, Cell cell){
        if (board.blockAt(cell).isFree() && board.blockAt(cell.neighbor(Direction.W)).castsShadow()) {
            return (byte) shadow.ordinal();
        }
        else {
            return (byte) palette.get(board.blockAt(cell)).ordinal();
        }
    }




}
