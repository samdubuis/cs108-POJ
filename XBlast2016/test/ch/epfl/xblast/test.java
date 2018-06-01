package ch.epfl.xblast;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.epfl.xblast.client.GameState;
import ch.epfl.xblast.client.GameStateDeserializer;
import ch.epfl.xblast.client.KeyboardEventHandler;
import ch.epfl.xblast.client.XBlastComponent;
import ch.epfl.xblast.server.BoardPainter;
import ch.epfl.xblast.server.GameStateSerializer;
import ch.epfl.xblast.server.Level;
import ch.epfl.xblast.server.debug.GameStatePrinter;
import ch.epfl.xblast.server.debug.RandomEventGenerator;

public class test {

    static XBlastComponent xbc = null;
    static ch.epfl.xblast.server.GameState gs = Level.DEFAULT_LEVEL.getGameState();
    static BoardPainter bp = Level.DEFAULT_LEVEL.getBoardpainter();

    static PlayerID id = PlayerID.PLAYER_1;

    static GameState gs2 = GameStateDeserializer.deserializeGameState(GameStateSerializer.serialize(bp, gs));
 



    private static void createUI() {
        Map<Integer, PlayerAction> kb = new HashMap<>();

        kb.put(KeyEvent.VK_UP, PlayerAction.MOVE_N);
        kb.put(KeyEvent.VK_RIGHT, PlayerAction.MOVE_E);
        kb.put(KeyEvent.VK_DOWN, PlayerAction.MOVE_S);
        kb.put(KeyEvent.VK_LEFT, PlayerAction.MOVE_W);
        kb.put(KeyEvent.VK_SPACE, PlayerAction.DROP_BOMB);
        kb.put(KeyEvent.VK_SHIFT, PlayerAction.STOP);


        JFrame j = new JFrame();
        j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        xbc = new XBlastComponent();
//        j.setSize(xbc.getPreferredSize()); //si ce n'est pas décommenter, c'est parce que mon ordinateur n'affiche pas assez grand
        j.setSize(1000, 800);

        Consumer<PlayerAction> c = (pa)-> { System.out.println(pa.ordinal());};
        xbc.addKeyListener(new KeyboardEventHandler(kb, c));
        
        j.getContentPane().add(xbc);
        j.setVisible(true);
        xbc.requestFocusInWindow();


    }


    public static void main(String[] args) throws InvocationTargetException, InterruptedException {      

        RandomEventGenerator bla = new RandomEventGenerator(14, 8, 50);
        
        SwingUtilities.invokeAndWait(()->createUI());

        do {
            gs = gs.next(bla.randomSpeedChangeEvents(), bla.randomBombDropEvents());
            gs2 = GameStateDeserializer.deserializeGameState(GameStateSerializer.serialize(bp, gs));
//            SwingUtilities.invokeLater(()-> xbc.setGameState(gs2, id));
            xbc.setGameState(gs2, id);

            long a = 40;
            try {
                Thread.sleep(a);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (!gs.isGameOver());
        
        System.out.println("The winner is : "+gs.winner().get());



    }



}