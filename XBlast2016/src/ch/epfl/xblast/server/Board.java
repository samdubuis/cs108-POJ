/**
 * Classe concernant le board du jeu et ses fonctionss
 * 
 * @author Samuel Dubuis (259157)
 * @author Yann Gabbud  (260036)
 *
 */


package ch.epfl.xblast.server;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.cs108.Sq;
import ch.epfl.xblast.Cell;
import ch.epfl.xblast.Lists;

public final class Board {

    private final List<Sq<Block>> blockSequence;

    /**
     * Constructeur de la liste de séquences de blocks
     * @param blocks, une liste de séquences de blocks
     */
    public Board(List<Sq<Block>> blocks) {
        if (blocks.size() != 195) {
            throw new IllegalArgumentException();
        }            
        blockSequence = new ArrayList<>(blocks);
    }

    /**
     * Fonction levant les exceptions si les matrix en paramètres ne sont pas de la bonne taille
     * @param matrix à tester
     * @param rows valeur des lignes à respecter
     * @param columns valeur des colonnes à respecter
     */
    private static void checkBlockMatrix(List<List<Block>> matrix, int rows, int columns) {
        for (int i = 0; i < matrix.size(); i++) {
            if (matrix.size() != rows || matrix.get(i).size() != columns) {
                throw new IllegalArgumentException();
            }
        }
    }

    /**
     * Fonction créant un board constant en recevant en paramètre une liste de rows de blocks
     * @param rows, liste de liste de blocks
     * @return board constant
     */
    public static Board ofRows(List<List<Block>> rows) {
        checkBlockMatrix(rows, Cell.ROWS, Cell.COLUMNS);

        List<Sq<Block>> listConstant = new ArrayList<>();
        for (int i = 0; i < rows.size(); i++) {
            for (int j = 0; j < rows.get(i).size(); j++) {
                listConstant.add(Sq.constant(rows.get(i).get(j)));
            }
        }
        return new Board(listConstant);
    }

    /**
     * Fonction créant un board constant en recevant en paramètre les blocks intérieurs du board
     * @param innerBlocks, liste de blocks internes
     * @return board constant
     */
    public static Board ofInnerBlocksWalled(List<List<Block>> innerBlocks) {
        checkBlockMatrix(innerBlocks, Cell.ROWS-2, Cell.COLUMNS-2);

        List<Sq<Block>> listConstant = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            listConstant.add(Sq.constant(Block.INDESTRUCTIBLE_WALL));
        }
        for (int i = 0; i < innerBlocks.size(); i++) {
            listConstant.add(Sq.constant(Block.INDESTRUCTIBLE_WALL));
            for (int j = 0; j < innerBlocks.get(i).size(); j++) {
                listConstant.add(Sq.constant(innerBlocks.get(i).get(j)));
            }
            listConstant.add(Sq.constant(Block.INDESTRUCTIBLE_WALL));
        }
        for (int i = 0; i < 15; i++) {
            listConstant.add(Sq.constant(Block.INDESTRUCTIBLE_WALL));
        }
        return new Board(listConstant);
    }

    /**
     * Fonction créant un board constant en recevant en paramètre les blocks du coin nord ouest du board
     * @param quadrantNWBlocks, liste de blocks du coin nord ouest du board
     * @return board constant
     */
    public static Board ofQuadrantNWBlocksWalled(List<List<Block>> quadrantNWBlocks) {
        checkBlockMatrix(quadrantNWBlocks, (Cell.ROWS-1)/2, (Cell.COLUMNS-1)/2);

        List<List<Block>> tempList = new ArrayList<>(quadrantNWBlocks);

        List<List<Block>> tempList2 = Lists.mirrored(tempList);

        List<List<Block>> tempList3 = new ArrayList<>();
        for (int i = 0; i < tempList2.size(); i++) {
            tempList3.add(Lists.mirrored(tempList2.get(i)));
        }

        Board board = ofInnerBlocksWalled(tempList3);
        return board;

    }

    /**
     * Fonction retournant la séquence de blocks de la cellule donnée en parametre
     * @param c cellule 
     * @return séquence de blocks
     */
    public Sq<Block> blocksAt(Cell c){
        return blockSequence.get(c.rowMajorIndex());
    }

    /**
     * Fonction retournant le block à la position de la cellule donnée en paramètre
     * @param c cellule
     * @return block
     */
    public Block blockAt (Cell c){
        return blocksAt(c).head();
    }
}
