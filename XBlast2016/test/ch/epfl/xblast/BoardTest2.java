package ch.epfl.xblast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.epfl.xblast.server.Block;
import ch.epfl.xblast.server.Board;

public class BoardTest2 {

    public static void main(String[] args) {
        Block __ = Block.FREE;
        Block XX = Block.INDESTRUCTIBLE_WALL;
        Block xx = Block.DESTRUCTIBLE_WALL;
        Block BR = Block.BONUS_RANGE;
        Board board = Board.ofQuadrantNWBlocksWalled(
                Arrays.asList(
                        Arrays.asList(BR, __, __, __, __, xx, __),
                        Arrays.asList(__, XX, xx, XX, xx, XX, xx),
                        Arrays.asList(__, xx, __, __, __, xx, __),
                        Arrays.asList(xx, XX, __, XX, XX, XX, XX),
                        Arrays.asList(__, xx, __, xx, __, __, __),
                        Arrays.asList(xx, XX, xx, XX, xx, XX, __)));
        
        List<Block> tempBlocks = new ArrayList<>();
        for (int i = 0; i < Cell.ROW_MAJOR_ORDER.size(); i++) {
            tempBlocks.add(board.blockAt(Cell.ROW_MAJOR_ORDER.get(i)));
        }
        
        
        List<SubCell> blockingSubCell = new ArrayList<>();
        for (int i = 0; i < tempBlocks.size(); i++) {
            if (!tempBlocks.get(i).canHostPlayer()) {
                for (Direction direction : Direction.values()) {
                    blockingSubCell.add(SubCell.centralSubCellOf(Cell.ROW_MAJOR_ORDER.get(i).neighbor(direction)));
                }
            }
        }
        System.out.println(blockingSubCell);
        
    }
}
