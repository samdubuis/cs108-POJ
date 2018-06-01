package ch.epfl.xblast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.xblast.server.BoardPainter;
import ch.epfl.xblast.server.GameState;
import ch.epfl.xblast.server.Level;

public class RunLengthEncoderTest {

    public static void main(String[] args) {
        
        BoardPainter bp = Level.DEFAULT_LEVEL.getBoardpainter();
        GameState gs = Level.DEFAULT_LEVEL.getGameState();
        
        List<Byte> listEncode = new ArrayList<>();
        for (int i = 0; i < Cell.SPIRAL_ORDER.size(); i++) {
            listEncode.add(bp.byteOfCell(gs.board(), Cell.SPIRAL_ORDER.get(i)));
        }
        System.out.println(listEncode);
        
//        System.out.println(RunLengthEncoder.encode(listEncode));
        
        System.out.println(RunLengthEncoder.decode(RunLengthEncoder.encode(listEncode)));
        
        

    }

}
